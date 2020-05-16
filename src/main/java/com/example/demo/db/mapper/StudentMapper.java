package com.example.demo.db.mapper;

import com.example.demo.db.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:24
 */
public interface StudentMapper extends JpaRepository<Student, Integer> {
}
