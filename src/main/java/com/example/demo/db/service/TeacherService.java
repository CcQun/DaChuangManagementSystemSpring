package com.example.demo.db.service;

import com.example.demo.db.mapper.TeacherMapper;
import com.example.demo.db.model.Teacher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/5/26 17:36
 */
@Service
public class TeacherService extends BaseService<Teacher, Integer, TeacherMapper> {
    public List<Teacher> findAllByTeacherNumber(int teacherNumber){
        Teacher teacher = Teacher.builder().teacher_number(teacherNumber).build();
        Example<Teacher> example = Example.of(teacher);
        List<Teacher> list = mapper.findAll(example);
        return list;
    }
}
