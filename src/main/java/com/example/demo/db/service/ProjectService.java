package com.example.demo.db.service;


import com.example.demo.db.mapper.ApplyProjectMapper;
import com.example.demo.db.mapper.ProjectMapper;
import com.example.demo.db.mapper.TeamMapper;
import com.example.demo.db.model.ApplyProject;
import com.example.demo.db.model.Blink;
import com.example.demo.db.model.Project;
import com.example.demo.db.model.Student;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectService extends BaseService<Project,Integer, ProjectMapper> {

    private final ProjectMapper projectMapper;
    private final ApplyProjectMapper applyProjectMapper;
    private final TeamMapper teamMapper;

    public ProjectService(ProjectMapper projectMapper,ApplyProjectMapper applyProjectMapper,TeamMapper teamMapper) {
        this.projectMapper = projectMapper;
        this.applyProjectMapper=applyProjectMapper;
        this.teamMapper = teamMapper;
    }

    public boolean delete(Integer project_num){

        try{
            applyProjectMapper.deleteByProjectnum(project_num);
            teamMapper.deleteByProjectnum(project_num);
            projectMapper.deleteById(project_num);
            return true;
        }catch (Exception e){
            System.out.println("不存在该项目");
            return false;
        }
    }

    public List<Project> findAllByProjectNumber(int projectNumber){
        Project project=Project.builder().project_number(projectNumber).build();
        Example<Project> example = Example.of(project);
        List<Project> list = mapper.findAll(example);
        return list;
    }

    public boolean changeState(Project project,int projectapproval,int oldapproval) {
        int state = project.getProject_State();
        if (projectapproval == 2) {
            if (oldapproval == 1) {
                project.setProject_State(state - 1);
                try {
                    mapper.save(project);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                return true;
            }
        } else {
            if (oldapproval == 1) {
                return true;
            } else {
                if (state == 3) {
                    return false;
                } else {
                    project.setProject_State(state + 1);
                    try {
                        mapper.save(project);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }

        }
    }

    public List<Project> findAllByBlinkNumber(int projectNumber){
        Project project=Project.builder().project_number(projectNumber).build();
        Example<Project> example = Example.of(project);
        List<Project> list = mapper.findAll(example);
        return list;
    }

}
