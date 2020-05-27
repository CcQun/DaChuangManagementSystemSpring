package com.example.demo.controller;

import com.example.demo.core.request.*;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.core.response.custommodel.BlinkWithSName;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import com.example.demo.db.service.StudentService;
import org.json.JSONException;

//import com.example.demo.db.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.alibaba.fastjson.JSONObject;

import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.MyBlinkRequest;
import com.example.demo.core.request.PublishBlinkRequest;
import com.example.demo.core.request.SearchBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import com.example.demo.db.service.StudentService;
import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;


import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.MyBlinkRequest;
import com.example.demo.core.request.PublishBlinkRequest;
import com.example.demo.core.request.SearchBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.core.response.custommodel.BlinkWithSName;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Student;

/**
 * @Author CcQun、ZyMeng、ZYing、LhRan、LcYao
 * @Date 2020/5/16 7:39
 */
@RestController()
@RequestMapping("/blink")
public class BlinkController {

    @Autowired
    private final ApplyBlinkService applyBlinkService;

    @Autowired
    private final BlinkService blinkService;

    @Autowired
    private final StudentService studentService;

    public BlinkController(ApplyBlinkService applyBlinkService, BlinkService blinkService, StudentService studentService) {
        this.applyBlinkService = applyBlinkService;
        this.blinkService = blinkService;
        this.studentService = studentService;
    }


    //发布blink
    @RequestMapping("/publishBlink")
    public BaseResponse publishBlink(@RequestBody PublishBlinkRequest request) {

        Blink blink = Blink.builder()
                .blink_number(getMaxBlinkNumber() + 1)
                .student_number(request.getStudent_number())
                .blink_title(request.getBlink_title())
                .blink_content(request.getBlink_content())
                .creat_time(new Date())
                .blink_college(request.getBlink_college())
                .blink_field(request.getBlink_field())
                .blink_state(new Integer(0))
                .build();
        blinkService.getMapper().save(blink);
        BaseResponse response = new BaseResponse();
        response.setCode(1);
        return response;
    }

    //申请加入某个blink
    @RequestMapping("/applyBlink")
    public BaseResponse joinBlink(@RequestBody ApplyBlinkRequest request) {
        BaseResponse response = new BaseResponse();
        ApplyBlinkPK applyblinkPK = ApplyBlinkPK.builder()
                .blinknum(request.getBlink_number())
                .studentnum(request.getStudent_number())
                .build();

        ApplyBlink applyblink = ApplyBlink.builder()
                .applyBlinkPK(applyblinkPK)
                .Blink_Approval(0)
                .build();

        if (applyBlinkService.insert(applyblink)) {
            response.setCode(1);
            response.setMsg("Success!");
        } else {
            response.setCode(0);
            response.setMsg("False");
        }
        return response;
    }

    //查看自己发布的blink
    @RequestMapping("/myBlink")
    public ListResponse<BlinkWithSName> myBlink(@RequestBody MyBlinkRequest request){
        Blink blink = Blink.builder().student_number(request.getStudent_number()).build();
        List<Blink> list = blinkService.findAll(blink);
        List<BlinkWithSName> res = new ArrayList<>();
        for(int i = 0;i < list.size();i++){
            Blink blink1 = list.get(i);
            Student student = Student.builder().student_number(blink1.getStudent_number()).build();
            String student_name = studentService.findAll(student).get(0).getStudent_name();
            BlinkWithSName bwsn = BlinkWithSName.builder()
                    .blink_number(blink1.getBlink_number())
                    .student_number(blink1.getStudent_number())
                    .blink_college(blink1.getBlink_college())
                    .blink_content(blink1.getBlink_content())
                    .blink_field(blink1.getBlink_field())
                    .blink_state(blink1.getBlink_state())
                    .blink_title(blink1.getBlink_title())
                    .create_time(blink1.getCreat_time())
                    .student_name(student_name)
                    .build();
            res.add(bwsn);
        }
        ListResponse<BlinkWithSName> response = new ListResponse<>();
        response.setData(res);
        response.setCode(1);
        return response;
    }

    //查询blink
    @RequestMapping("/queryblink")
    public List<Blink> queryblink(){
        List<Blink> blinks = blinkService.findAll();
        return blinks;
    }

    //关键字搜索blink

    @ResponseBody
    @RequestMapping("/searchblink")
    public JSONObject searchblink(@RequestBody SearchBlinkRequest request) throws JSONException {

        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        JSONObject object=new JSONObject();

        String str = request.getKeywords();
        List<Blink> blinks = blinkService.findAll();
        if (str.length()==0||str==null||str.replaceAll("\\s*", "").length()==0){
            object.put("code", 1);
            object.put("msg", "yes");
            object.put("data", jsonlist);
            return object;
        }
        int num = 0;
        for (int i = 0; i < blinks.size(); i++) {
            if (blinks.get(i).getBlink_content().indexOf(str) != -1) {
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("blink_number",blinks.get(i).getBlink_number());
                jsonlist.get(num).put("student_number",blinks.get(i).getStudent_number());
                int studentnum=blinks.get(i).getStudent_number();
                System.out.println("1"+studentnum);
                System.out.println("2"+studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                jsonlist.get(num).put("student_name",studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                jsonlist.get(num).put("blink_title",blinks.get(i).getBlink_title());
                jsonlist.get(num).put("blink_content",blinks.get(i).getBlink_content());
                jsonlist.get(num).put("create_time",blinks.get(i).getCreat_time());
                jsonlist.get(num).put("blink_college",blinks.get(i).getBlink_college());
                jsonlist.get(num).put("blink_field",blinks.get(i).getBlink_field());
                jsonlist.get(num).put("blink_state",blinks.get(i).getBlink_state());
                num++;
            }
            else if(blinks.get(i).getBlink_title().indexOf(str)!= -1){
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("blink_number",blinks.get(i).getBlink_number());
                jsonlist.get(num).put("student_number",blinks.get(i).getStudent_number());
                int studentnum=blinks.get(i).getStudent_number();
                System.out.println("1"+studentnum);
                System.out.println("2"+studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                jsonlist.get(num).put("student_name",studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                jsonlist.get(num).put("blink_title",blinks.get(i).getBlink_title());
                jsonlist.get(num).put("blink_content",blinks.get(i).getBlink_content());
                jsonlist.get(num).put("create_time",blinks.get(i).getCreat_time());
                jsonlist.get(num).put("blink_college",blinks.get(i).getBlink_college());
                jsonlist.get(num).put("blink_field",blinks.get(i).getBlink_field());
                jsonlist.get(num).put("blink_state",blinks.get(i).getBlink_state());
                num++;
            }
        }
        object.put("code",1);
        object.put("msg","yes");
        object.put("data",jsonlist);
        return object;
    }

    //获得最大blink number
    public Integer getMaxBlinkNumber() {
        List<Blink> blinks = blinkService.findAll();
        Integer maxBlinkNumber = 0;
        for (int i = 0; i < blinks.size(); i++) {
            if (blinks.get(i).getBlink_number() > maxBlinkNumber) {
                maxBlinkNumber = blinks.get(i).getBlink_number();
            }
        }
        return maxBlinkNumber;
    }

    //删除blink
    @RequestMapping("/deleteblink")
    public BaseResponse deleteblink(@RequestBody DeleteBlinkRequest request){
        BaseResponse response = new BaseResponse();
        Integer blink_num=request.getBlink_number();
        if (blinkService.delete(blink_num)) {
            response.setCode(1);
            response.setMsg("Success!");
        } else {
            response.setCode(0);
            response.setMsg("False");
        }
        return response;
    }
}
