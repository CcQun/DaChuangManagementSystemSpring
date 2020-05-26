package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.ApplyProjectRequest;
import com.example.demo.core.request.DeleteBlinkRequest;
import com.example.demo.core.request.DeleteProjectRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.ApplyBlink;
import com.example.demo.db.model.ApplyProject;
import com.example.demo.db.model.ApplyProjectPK;
import com.example.demo.db.model.Project;
import com.example.demo.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author CcQun、ZyMeng、ZYing、LhRan、LcYao
 * @Date 2020/5/16 7:40
 */
@RestController()
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    private final ApplyProjectService applyProjectService;
    @Autowired
    private final ProjectService projectService;
    public ProjectController(ApplyProjectService applyProjectService,ProjectService projectService) {
        this.applyProjectService = applyProjectService;
        this.projectService=projectService;
    }

    //申请加入某个blink
    @RequestMapping("/applyProject")
    public BaseResponse joinBlink(@RequestBody ApplyProjectRequest request) {
        BaseResponse response = new BaseResponse();
        ApplyProjectPK applyProjectPK = ApplyProjectPK.builder()
                .projectnum(request.getProject_number())
                .studentnum(request.getStudent_number())
                .build();

        ApplyProject applyProject = ApplyProject.builder()
                .applyProjectPK(applyProjectPK)
                .Project_Approval(0)
                .build();

        if (applyProjectService.insert(applyProject)) {
            response.setCode(1);
            response.setMsg("Success!");
        } else {
            response.setCode(0);
            response.setMsg("False");
        }
        return response;
    }

    //删除项目
    @RequestMapping("/deleteproject")
    public BaseResponse deleteblink(@RequestBody DeleteProjectRequest request){
        BaseResponse response = new BaseResponse();
        Integer project_num=request.getProject_number();
        if (projectService.delete(project_num)) {
            response.setCode(1);
            response.setMsg("Success!");
        } else {
            response.setCode(0);
            response.setMsg("False");
        }
        return response;
    }

    @ResponseBody
    @RequestMapping("/getProjectApprove")
    public JSONObject getApprove(@RequestBody ApplyProjectRequest request) {
        Integer student_number=request.getStudent_number();

        Specification<ApplyProject> specification=new Specification<ApplyProject>() {
            @Override
            public Predicate toPredicate(Root<ApplyProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyProjectPK").get("studentnum"),student_number);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyProject> list = applyProjectService.findAll(specification);

        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        JSONObject object=new JSONObject();

        if(list.size()==0){
            object.put("code",0);
            object.put("msg","no apply");
            return object;
        }
        else {
            int num=0;
            for (int i = 0; i<list.size(); i++){
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("project_number",list.get(i).getApplyProjectPK().getProjectnum());
                jsonlist.get(num).put("project_approval",list.get(i).getProject_Approval());
                num++;
            }
            object.put("code",1);
            object.put("msg","yes");
            object.put("data",jsonlist);
            return object;
        }
    }
}
