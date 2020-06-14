package com.example.demo.db.service;

import com.example.demo.db.mapper.StudentMapper;
import com.example.demo.db.model.Student;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:26
 */
@Service
public class StudentService extends BaseService<Student, Integer, StudentMapper> {
    public List<Student> findAllByStudentNumber(int studentNumber) {
        Student student = Student.builder().student_number(studentNumber).build();
        Example<Student> example = Example.of(student);
        List<Student> list = mapper.findAll(example);
        return list;
    }

    public String findNameByStudentNumber(int student_number){
        Student student=Student.builder().student_number(student_number).build();
        Example<Student> example=Example.of(student);
        List<Student> list=mapper.findAll(example);
        String name=list.get(0).getStudent_name();
        return name;
    }
}
