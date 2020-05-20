package com.example.demo.controller;

import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.PublishBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public BlinkController(ApplyBlinkService applyBlinkService, BlinkService blinkService) {
        this.applyBlinkService = applyBlinkService;
        this.blinkService = blinkService;
    }


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
