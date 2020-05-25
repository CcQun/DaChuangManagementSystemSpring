package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.mapper.BlinkMapper;
import com.example.demo.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyBlinkService extends BaseService<ApplyBlink,ApplyBlinkPK, ApplyBlinkMapper>{

    private final ApplyBlinkMapper applyBlinkMapper;

    public ApplyBlinkService(ApplyBlinkMapper applyBlinkMapper) {

        this.applyBlinkMapper=applyBlinkMapper;
    }
//申请加入blink
    public boolean insert(ApplyBlink applyBlink){
        try{
            mapper.save(applyBlink);
            return true;
        }catch (Exception e){
            System.out.println("主键冲突");
            return false;
        }
    }

    public boolean findAllByBlinkStudent(ApplyBlink applyBlink,int approval){
        applyBlink.setBlink_Approval(approval);
        try{
            mapper.save(applyBlink);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
