package com.example.demo.controller;

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
@RestController("/blink")
public class BlinkController {

    private final ApplyBlinkService applyBlinkService;
    public BlinkController(ApplyBlinkService applyBlinkService){
        this.applyBlinkService=applyBlinkService;
    }

    @RequestMapping("/applyBlink")
    public void joinBlink(HttpServletRequest req, HttpServletResponse resp) {
        int blinknum=Integer.parseInt(req.getParameter("blink_number"));
        int studentnum=Integer.parseInt(req.getParameter("student_number"));

        ApplyBlinkPK applyblinkPK=new ApplyBlinkPK();
        applyblinkPK.setBlinknum(blinknum);
        applyblinkPK.setStudentnum(studentnum);

        ApplyBlink applyblink=new ApplyBlink();
        applyblink.setApplyBlinkPK(applyblinkPK);
        applyblink.setBlink_Approval(0);

        applyBlinkService.insert(applyblink);
    }



}
