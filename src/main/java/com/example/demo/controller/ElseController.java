package com.example.demo.controller;

import com.example.demo.core.request.LoginRequest;
import com.example.demo.core.response.DataResponse;
import com.example.demo.db.model.Student;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.StudentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @Author CcQun、ZyMeng、ZYing、LhRan、LcYao
 * @Date 2020/5/16 13:28
 */
@RestController
public class ElseController {
    private final StudentService studentService;

    public ElseController(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping("/login")
    public DataResponse login(@RequestBody LoginRequest request){
        List<Student> list = studentService.findAllByStudentNumber(request.getStudent_number());
        String MD5Password = request.getStudent_password();
        DataResponse response = new DataResponse();
        if(MD5Password.equals(list.get(0).getStudentPassword())){
            response.setCode(1);
            return response;
        }else{
            response.setCode(0);
            return response;
        }
    }
}
