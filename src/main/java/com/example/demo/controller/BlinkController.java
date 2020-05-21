package com.example.demo.controller;

import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.MyBlinkRequest;
import com.example.demo.core.request.PublishBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.core.response.custommodel.BlinkWithSName;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Student;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import com.example.demo.db.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                    .creat_time(blink1.getCreat_time())
                    .student_name(student_name)
                    .build();
            res.add(bwsn);
        }
        ListResponse<BlinkWithSName> response = new ListResponse<>();
        response.setData(res);
        response.setCode(1);
        return response;
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
}
