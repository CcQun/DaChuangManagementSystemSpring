package com.example.demo.controller;

import com.example.demo.core.response.BaseResponse;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.service.ApplyBlinkService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author CcQun、ZyMeng、ZYing、LhRan、LcYao
 * @Date 2020/5/16 7:39
 */
@RestController()
@RequestMapping("/blink")
public class BlinkController {

    private final ApplyBlinkService applyBlinkService;
    public BlinkController(ApplyBlinkService applyBlinkService){
        this.applyBlinkService=applyBlinkService;
    }

    @RequestMapping("/applyBlink")
    public BaseResponse joinBlink(HttpServletRequest req, HttpServletResponse resp) {
        BaseResponse response = new BaseResponse();
        ApplyBlinkPK applyblinkPK=new ApplyBlinkPK();

        Integer blinknum=Integer.parseInt(req.getParameter("blink_number"));
        Integer studentnum=Integer.parseInt(req.getParameter("student_number"));

        applyblinkPK.setBlinknum(blinknum);
        applyblinkPK.setStudentnum(studentnum);

        ApplyBlink applyblink=new ApplyBlink();
        applyblink.setApplyBlinkPK(applyblinkPK);
        applyblink.setBlink_Approval(0);

        if(applyBlinkService.insert(applyblink)){
            response.setCode(1);
            response.setMsg("Success!");
        }else{
            response.setCode(0);
            response.setMsg("False");
        }
        return response;
    }
}
