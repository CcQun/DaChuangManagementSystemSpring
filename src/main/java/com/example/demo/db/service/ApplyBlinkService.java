package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyBlinkService extends BaseService<ApplyBlink,ApplyBlinkPK, ApplyBlinkMapper>{

    @Autowired
    private ApplyBlinkMapper applyBlinkMapper;

    public boolean insert(ApplyBlink applyBlink){
        try{
            mapper.save(applyBlink);
            return true;
        }catch (Exception e){
            e.printStackTrace();
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
