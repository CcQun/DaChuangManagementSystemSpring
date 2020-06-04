package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.Utils;
import com.example.demo.core.request.LoginRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.DataResponse;
import com.example.demo.db.model.Student;
import com.example.demo.db.model.Teacher;
import com.example.demo.db.service.StudentService;
import com.example.demo.db.service.TeacherService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
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


    //上传图片
    @ResponseBody
    @RequestMapping(value = "/fileUpload")
    public JSONObject fileUpload(@RequestParam("file") MultipartFile file,@RequestParam("name") String name){
        if(file.isEmpty()){
            JSONObject json = new JSONObject();
            json.put("response", "false");
            return json;
        }
        //String fileName = file.getOriginalFilename();
        String fileName = name;
        System.out.println(fileName);
        int size = (int) file.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "D:\\Aa大学文件\\处理事项\\大三下\\课程-JavaEE\\DaChuangManagementSystemSpring\\src\\main\\resources\\static";
        File dest = new File(path + "/" + fileName);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            JSONObject json = new JSONObject();
            json.put("response", "true");
            json.put("fileindex", fileName);
            return json;
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("response", "false");
            return json;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JSONObject json = new JSONObject();
            json.put("response", "false");
            return json;
        }
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
