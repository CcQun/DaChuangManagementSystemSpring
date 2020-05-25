package com.example.demo.db.service;


import com.example.demo.db.mapper.ApplyProjectMapper;
import com.example.demo.db.mapper.ProjectMapper;
import com.example.demo.db.model.ApplyProject;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectService extends BaseService<Project,Integer, ProjectMapper> {

    private final ProjectMapper projectMapper;
    private final ApplyProjectMapper applyProjectMapper;

    public ProjectService(ProjectMapper projectMapper,ApplyProjectMapper applyProjectMapper) {
        this.projectMapper = projectMapper;
        this.applyProjectMapper=applyProjectMapper;
    }

    public boolean delete(Integer project_num){

        try{
            applyProjectMapper.deleteByProjectnum(project_num);
            projectMapper.deleteById(project_num);
            return true;
        }catch (Exception e){
            System.out.println("不存在该项目");
            return false;
        }
    }
}
