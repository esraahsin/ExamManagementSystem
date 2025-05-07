package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.sql.Date;
import java.sql.Time;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.example.demo.model.User;
import com.example.demo.repository.InvigilatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class InvigilatorService {

    private static final Logger log = LoggerFactory.getLogger(InvigilatorService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private InvigilatorRepository invigilatorRepository;

    public List<User> getAvailableInvigilators(int examId, String examDate, String startTime, String endTime) {
        try {
            log.info("Début de la récupération des surveillants disponibles pour examId: {}, examDate: {}, startTime: {}, endTime: {}", 
                     examId, examDate, startTime, endTime);

            // Valider les paramètres d'entrée
            if (examId <= 0 || examDate == null || startTime == null || endTime == null) {
                throw new IllegalArgumentException("Paramètres d'entrée invalides.");
            }

            // Valider le format de la date et de l'heure
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            try {
                dateFormatter.parse(examDate);
                LocalTime.parse(startTime, timeFormatter);
                LocalTime.parse(endTime, timeFormatter);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("Format de date ou d'heure invalide.");
            }

            // Convertir les paramètres en types SQL
            Date sqlExamDate = Date.valueOf(examDate);
            LocalTime startLocalTime = LocalTime.parse(startTime, timeFormatter);
            LocalTime endLocalTime = LocalTime.parse(endTime, timeFormatter);
            Time sqlStartTime = Time.valueOf(startLocalTime);
            Time sqlEndTime = Time.valueOf(endLocalTime);

            // Récupérer la matière de l'examen
            log.info("Récupération de la matière de l'examen avec examId: {}", examId);
            List<String> subjects = jdbcTemplate.query(
                "SELECT subject FROM exams WHERE exam_id = ?", 
                (rs, rowNum) -> rs.getString("subject"), 
                examId);

            if (subjects.isEmpty()) {
                log.warn("Aucun examen trouvé avec l'ID : {}", examId);
                throw new IllegalStateException("Aucun examen trouvé avec l'ID : " + examId);
            }

            String subject = subjects.get(0);
            log.info("Matière de l'examen récupérée : {}", subject);

            // Récupérer tous les enseignants
            List<User> allTeachers = jdbcTemplate.query(
                "SELECT u.user_id, u.name FROM users u WHERE u.role = 'TEACHER'", 
                new BeanPropertyRowMapper<>(User.class)
            );

            // Filtrer les surveillants disponibles
            List<User> availableInvigilators = allTeachers.stream()
                .filter(teacher -> {
                    boolean isOccupied = invigilatorRepository.isTeacherOccupied(
                        teacher.getUserId(), sqlExamDate, sqlStartTime, sqlEndTime);
                    log.info("L'enseignant {} est occupé: {}", teacher.getUserId(), isOccupied);
                    return !isOccupied;
                })
                .filter(teacher -> {
                    boolean isAssigned = isTeacherAssignedToSubject(teacher.getUserId(), subject);
                    log.info("L'enseignant {} est affecté à la matière {}: {}", teacher.getUserId(), subject, isAssigned);
                    return !isAssigned;
                })
                .collect(Collectors.toList());

            log.info("Surveillants disponibles récupérés avec succès : {}", availableInvigilators.size());
            return availableInvigilators;

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des surveillants disponibles : ", e);
            throw new RuntimeException("Erreur lors de la récupération des surveillants disponibles : " + e.getMessage());
        }
    }

    private boolean isTeacherAssignedToSubject(int userId, String subject) {
        String sql = "SELECT COUNT(*) > 0 FROM teaching_assignments WHERE user_id = ? AND subject = ?";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, subject);
        return result != null && result;
    }
}
