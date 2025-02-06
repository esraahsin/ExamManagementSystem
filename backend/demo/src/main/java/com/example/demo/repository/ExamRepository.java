package com.example.demo.repository;

import com.example.demo.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    
    // Find exams by department ID
    List<Exam> findByDepartment_DepartmentId(int departmentId);
@Query("SELECT e FROM Exam e WHERE e.speciality = :speciality")
List<Exam> findBySpeciality(@Param("speciality") String speciality);

}
