package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyProjectMapper;
import com.example.demo.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyProjectService extends BaseService<ApplyProject,ApplyProjectPK, ApplyProjectMapper>{

    private final ApplyProjectMapper applyProjectMapper;

    public ApplyProjectService(ApplyProjectMapper applyProjectMapper) {

        this.applyProjectMapper=applyProjectMapper;
    }

    //申请加入项目
    public boolean insert(ApplyProject applyProject){
        try{
            mapper.save(applyProject);
            return true;
        }catch (Exception e){
            System.out.println("主键冲突");
            return false;
        }
    }

    public boolean findAllByProjectStudent(ApplyProject applyProject,int approval){
        applyProject.setProject_Approval(approval);
        try{
            mapper.save(applyProject);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
