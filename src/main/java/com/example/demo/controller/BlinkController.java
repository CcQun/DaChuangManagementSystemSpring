package com.example.demo.controller;

import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.MyBlinkRequest;
import com.example.demo.core.request.PublishBlinkRequest;
import com.example.demo.core.request.SearchBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Student;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.SpringServletContainerInitializer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    public BlinkController(ApplyBlinkService applyBlinkService, BlinkService blinkService) {
        this.applyBlinkService = applyBlinkService;
        this.blinkService = blinkService;
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
    public ListResponse<Blink> myBlink(@RequestBody MyBlinkRequest request){
        Blink blink = Blink.builder().student_number(request.getStudent_number()).build();
        List<Blink> list = blinkService.findAll(blink);
        ListResponse<Blink> response = new ListResponse<>();
        response.setData(list);
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

    //查询blink
    @RequestMapping("/queryblink")
    public List<Blink> queryblink(){
        List<Blink> blinks = blinkService.findAll();
        return blinks;
    }
    //关键字搜索blink

    @RequestMapping("/searchblink")
    public List<Blink> searchblink(@RequestBody SearchBlinkRequest request) {
        String str = request.getKeywords();
        List<Blink> blinks = blinkService.findAll();
        List<Blink> searchedblink = new ArrayList<Blink>();
        for (int i = 0; i<blinks.size(); i++){
            if(blinks.get(i).getBlink_content().indexOf(str)!= -1){
                searchedblink.add(blinks.get(i));
            }
            else if(blinks.get(i).getBlink_title().indexOf(str)!= -1){
                searchedblink.add(blinks.get(i));
            }
        }
        return searchedblink;
    }
}
