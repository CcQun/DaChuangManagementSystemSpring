package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.model.*;
import org.springframework.stereotype.Service;

@Service
public class ApplyBlinkService extends BaseService<ApplyBlink, ApplyBlinkPK, ApplyBlinkMapper> {

    public boolean insert(ApplyBlink applyBlink) {
        try {
            mapper.save(applyBlink);
            return true;
        } catch (Exception e) {
            System.out.println("主键冲突");
            return false;
        }
    }

    public boolean findAllByBlinkStudent(ApplyBlink applyBlink, int approval) {

        applyBlink.setBlink_Approval(approval);

        try {

            mapper.save(applyBlink);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }
}
