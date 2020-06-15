package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.mapper.BlinkMapper;
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

/**
 * @Author CcQun
 * @Date 2020/5/18 20:32
 */
@Service
public class BlinkService extends BaseService<Blink,Integer, BlinkMapper>{
    private final BlinkMapper BlinkMapper;
    private final ApplyBlinkMapper applyBlinkMapper;
    @Autowired
    private final ProjectService projectService;
    @Autowired
    private final TeamService teamService;
    @Autowired
    private final ApplyBlinkService applyBlinkService;

    public BlinkService(BlinkMapper blinkMapper, ApplyBlinkMapper applyBlinkMapper, ProjectService projectService, TeamService teamService, ApplyBlinkService applyBlinkService) {
        this.BlinkMapper = blinkMapper;
        this.applyBlinkMapper=applyBlinkMapper;
        this.projectService = projectService;
        this.teamService = teamService;
        this.applyBlinkService = applyBlinkService;
    }
    public List<Blink> findAll(){
        return BlinkMapper.findAll();
    }

    public boolean delete(Integer blink_num){

        try{
            applyBlinkMapper.deleteByblinknum(blink_num);
            BlinkMapper.deleteById(blink_num);
            return true;
        }catch (Exception e){
            System.out.println("不存在该Blink");
            return false;
        }
    }

    public boolean changeState(Blink blink,int blinkapproval,int oldapproval,int max,int student_num){
        int state=blink.getBlink_state();
        if(blinkapproval==2){
            if(oldapproval==1){
                blink.setBlink_state(state-1);
                try{
                    mapper.save(blink);
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
            if(oldapproval==1){
                return true;
            }
            else{
                if(state==2){
                    return false;
                }
                else {
                    blink.setBlink_state(state+1);
                    try{
                        mapper.save(blink);
                        if(state+1==2){
                            blink.getStudent_number();
                            blink.getBlink_content();
                            blink.getBlink_number();
                            blink.getBlink_state();
                            Project project = Project.builder()
                                    .Create_Student_Number(blink.getStudent_number())
                                    .project_number(max + 1)
                                    .Project_Name(blink.getBlink_title())
                                    .Project_Description(blink.getBlink_content())
                                    .Project_College(blink.getBlink_college())
                                    .Project_Field(blink.getBlink_field())
                                    .create_time(blink.getCreate_time())
                                    .Project_State(new Integer(4))
                                    .build();
                            projectService.getMapper().save(project);


                           /* ApplyBlinkPK a=ApplyBlinkPK.builder().blinknum(blink.getBlink_number()).build();
                            Example<ApplyBlinkPK> example=Example.of(a);
                            List<ApplyBlinkPK> list1=applyBlinkPKMapper.findAll(example);
                            System.out.println("aaaa"+list1.size());
                            for(int i=0;i<list1.size();i++){
                                TeamPK teamPK=TeamPK.builder().projectnum(project.getProject_number()).studentnum(list1.get(i).getStudentnum()).build();
                                Team team=Team.builder().teamPK(teamPK).build();
                                teamService.getMapper().save(team);
                            }*/
                            //加到Team中
                            Specification<ApplyBlink> specification=new Specification<ApplyBlink>() {
                                @Override
                                public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                                    List<Predicate> predicateList=new ArrayList<>();
                                    Predicate shPredicate=criteriaBuilder.equal(root.get("applyBlinkPK").get("blinknum"),blink.getBlink_number());
                                    predicateList.add(shPredicate);
                                    Predicate[] predicates=new Predicate[predicateList.size()];
                                    return criteriaBuilder.and(predicateList.toArray(predicates));
                                }
                            };

                            List<ApplyBlink> list1 = applyBlinkService.findAll(specification);

                            for(int i=0;i<list1.size();i++){
                                if(list1.get(i).getBlink_Approval()==1){
                                    TeamPK teamPK=TeamPK.builder().
                                            projectnum(project.getProject_number())
                                            .studentnum(list1.get(i).getApplyBlinkPK().getStudentnum())
                                            .build();
                                    Team team=Team.builder().teamPK(teamPK).join_time(new Date()).build();
                                    teamService.getMapper().save(team);
                                }
                            }

                            TeamPK teamPK1=TeamPK.builder().
                                    projectnum(project.getProject_number())
                                    .studentnum(student_num)
                                    .build();
                            Team team=Team.builder().teamPK(teamPK1).join_time(new Date()).build();
                            teamService.getMapper().save(team);

                            TeamPK teamPK2=TeamPK.builder().
                                    projectnum(project.getProject_number())
                                    .studentnum(blink.getStudent_number()
                                    )
                                    .build();
                            Team team1=Team.builder().teamPK(teamPK2).join_time(new Date()).build();
                            teamService.getMapper().save(team1);

                        }
                        return true;
                    }catch (Exception e){
                        e.printStackTrace();
                        return false;
                    }
                }
            }

        }

    }
    public List<Blink> findAllByBlinkNumber(int blinkNumber){
        Blink blink=Blink.builder().blink_number(blinkNumber).build();
        Example<Blink> example = Example.of(blink);
        List<Blink> list = mapper.findAll(example);
        return list;
    }
}