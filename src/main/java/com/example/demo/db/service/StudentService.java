package com.example.demo.db.service;

import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:26
 */
@Service
public class StudentService {
    private final StudentMapper studentMapper;

    public StudentService(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public List<Student> findAllByStudentNumber(int studentNumber){
        Student student = Student.builder().studentNumber(studentNumber).build();
        Example<Student> example = Example.of(student);
        List<Student> list = studentMapper.findAll(example);
        return list;
    }
}
