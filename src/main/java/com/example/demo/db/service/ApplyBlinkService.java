package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.mapper.BlinkMapper;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyBlinkPK;
import com.example.demo.db.model.Blink;
import org.springframework.stereotype.Service;

@Service
public class ApplyBlinkService extends BaseService<ApplyBlink,ApplyBlinkPK, ApplyBlinkMapper>{

    public boolean insert(ApplyBlink applyBlink){
        try{
            mapper.save(applyBlink);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
