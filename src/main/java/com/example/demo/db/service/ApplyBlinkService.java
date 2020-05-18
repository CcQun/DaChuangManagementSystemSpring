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

    public void insert(ApplyBlink applyBlink){
        System.out.println(applyBlink.toString());
        applyblinkMapper.save(applyBlink);
    }
}
