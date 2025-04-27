package com.example.demo.repository;

import com.example.demo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
<<<<<<< HEAD
   
    @Query("SELECT e FROM Exam e WHERE e.subject IN :subjects")
    List<Exam> findExamsBySubjects(@Param("subjects") List<String> subjects);

    List<Exam> findBySubjectIn(List<String> subjects);
    @Query("SELECT e FROM Exam e JOIN Invigilator i ON e.examId = i.exam.examId WHERE i.user.userId = :teacherId")
    List<Exam> findExamsByInvigilatorId(@Param("teacherId") int teacherId);
=======
    
    // Find exams by department ID
    List<Exam> findByDepartment_DepartmentId(int departmentId);
@Query("SELECT e FROM Exam e WHERE e.speciality = :speciality")
List<Exam> findBySpeciality(@Param("speciality") String speciality);

>>>>>>> 2f9424f3f915851b00eebf33b4144ce31d5b26ba
}




