package com.example.demo.db.service;

import com.example.demo.db.mapper.ApplyDirectMapper;
import com.example.demo.db.mapper.ProjectMapper;
import com.example.demo.db.model.ApplyDirect;
import com.example.demo.db.model.ApplyDirectPK;
import com.example.demo.db.model.Project;
import org.springframework.stereotype.Service;

/**
 * @Author CcQun
 * @Date 2020/6/14 10:41
 */
@Service
public class ApplyDirectService extends BaseService<ApplyDirect, ApplyDirectPK, ApplyDirectMapper> {

    private final ProjectMapper projectMapper;
    private final ApplyDirectMapper applyDirectMapper;

    public ApplyDirectService(ProjectMapper projectMapper,ApplyDirectMapper applyDirectMapper) {
        this.projectMapper = projectMapper;
        this.applyDirectMapper=applyDirectMapper;
    }

    public boolean changeState(ApplyDirect applyDirect, Project project,int direct_approval, int teacher_number) {
        if(direct_approval==1){
            applyDirect.setDirect_Approval(direct_approval);
            project.setDirect_Teacher_Number(teacher_number);
            try {
                projectMapper.save(project);
                mapper.save(applyDirect);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else {
            applyDirect.setDirect_Approval(direct_approval);
            try {
                mapper.save(applyDirect);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

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
