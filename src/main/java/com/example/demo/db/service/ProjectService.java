package com.example.demo.db.service;


import com.example.demo.db.mapper.ApplyProjectMapper;
import com.example.demo.db.mapper.ProjectMapper;
import com.example.demo.db.mapper.TeamMapper;
import com.example.demo.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ProjectService extends BaseService<Project,Integer, ProjectMapper> {

    private final ProjectMapper projectMapper;
    private final ApplyProjectMapper applyProjectMapper;
    private final TeamMapper teamMapper;
    @Autowired
    private final ApplyProjectService applyProjectService;
    @Autowired
    private final TeamService teamService;
    public ProjectService(ProjectMapper projectMapper, ApplyProjectMapper applyProjectMapper, TeamMapper teamMapper, ApplyProjectService applyProjectService, TeamService teamService) {
        this.projectMapper = projectMapper;
        this.applyProjectMapper=applyProjectMapper;
        this.teamMapper = teamMapper;
        this.applyProjectService = applyProjectService;
        this.teamService = teamService;
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
                    if(state+1==3){
                        //加到Team中
                        Specification<ApplyProject> specification=new Specification<ApplyProject>() {
                            @Override
                            public Predicate toPredicate(Root<ApplyProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                                List<Predicate> predicateList=new ArrayList<>();
                                Predicate shPredicate=criteriaBuilder.equal(root.get("applyProjectPK").get("projectnum"),project.getProject_number());
                                predicateList.add(shPredicate);
                                Predicate[] predicates=new Predicate[predicateList.size()];
                                return criteriaBuilder.and(predicateList.toArray(predicates));
                            }
                        };

                        List<ApplyProject> list1 = applyProjectService.findAll(specification);

                        for(int i=0;i<list1.size();i++){
                            TeamPK teamPK=TeamPK.builder().
                                    projectnum(project.getProject_number())
                                    .studentnum(list1.get(i).getApplyProjectPK().getStudentnum())
                                    .build();
                            Team team=Team.builder().teamPK(teamPK).join_time(new Date()).build();
                            teamService.getMapper().save(team);

                        }
                    }

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
