package com.example.demo.db.mapper;

import com.example.demo.db.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:24
 */
public interface StudentMapper extends BaseMapper<Student, Integer> {
}
