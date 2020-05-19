package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import org.springframework.stereotype.Service;

@Service
public class ApplyBlinkService {

    private final ApplyBlinkMapper applyblinkMapper;
    public ApplyBlinkService(ApplyBlinkMapper applyblinkMapper) {
        this.applyblinkMapper = applyblinkMapper;
    }

    public boolean insert(ApplyBlink applyBlink){
        try{
            applyblinkMapper.save(applyBlink);
            System.out.println(applyBlink);
            System.out.println(applyblinkMapper.save(applyBlink));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
