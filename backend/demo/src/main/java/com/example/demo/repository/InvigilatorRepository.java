package com.example.demo.repository;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Invigilator;

@Repository
public interface InvigilatorRepository extends JpaRepository<Invigilator, Integer> {
   
}
