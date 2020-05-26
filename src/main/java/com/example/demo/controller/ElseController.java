package com.example.demo.controller;

import com.example.demo.core.Utils;
import com.example.demo.core.request.LoginRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.Teacher;
import com.example.demo.db.service.StudentService;
import com.example.demo.db.service.TeacherService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author CcQun、ZyMeng、ZYing、LhRan、LcYao
 * @Date 2020/5/16 13:28
 */
@RestController()
@RequestMapping("/usr")
public class ElseController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    public ElseController(StudentService studentService, TeacherService teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @RequestMapping("/login")
    public BaseResponse login(@RequestBody LoginRequest request){
        if(request.getTs() == 0) {
            List<Student> list = studentService.findAllByStudentNumber(request.getNumber());
            String MD5Password = Utils.getMD5(request.getPassword());
            BaseResponse response = new BaseResponse();
            if (list.size() > 0) {
                if (MD5Password.equals(list.get(0).getStudent_password())) {
                    response.setCode(1);
                    response.setMsg(list.get(0).getStudent_name());
                    return response;
                } else {
                    response.setCode(0);
                    return response;
                }
            } else {
                response.setCode(0);
                return response;
            }
        }else{
            List<Teacher> list = teacherService.findAllByTeacherNumber(request.getNumber());
            String MD5Password = Utils.getMD5(request.getPassword());
            BaseResponse response = new BaseResponse();
            if (list.size() > 0) {
                if (MD5Password.equals(list.get(0).getTeacher_password())) {
                    response.setCode(1);
                    response.setMsg(list.get(0).getTeacher_name());
                    return response;
                } else {
                    response.setCode(0);
                    return response;
                }
            } else {
                response.setCode(0);
                return response;
            }
        }
    }
}
