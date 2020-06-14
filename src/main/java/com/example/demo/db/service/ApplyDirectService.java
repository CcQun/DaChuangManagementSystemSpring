package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyDirectMapper;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyDirect;
import com.example.demo.db.model.ApplyDirectPK;
import com.example.demo.db.model.Student;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author CcQun
 * @Date 2020/6/14 10:41
 */
@Service
public class ApplyDirectService extends BaseService<ApplyDirect, ApplyDirectPK, ApplyDirectMapper> {
    public boolean insert(ApplyDirect applyDirect) {
        try {
            mapper.save(applyDirect);
            return true;
        } catch (Exception e) {
            System.out.println("主键冲突");
            return false;
        }
    }

    public boolean findAllByDirectProject(ApplyDirect applyDirect, int approval) {

        applyDirect.setDirect_Approval(approval);

        try {

            mapper.save(applyDirect);

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }
}
