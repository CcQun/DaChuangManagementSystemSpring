package com.example.demo.controller;

import com.example.demo.core.request.ApplyProjectRequest;
import com.example.demo.core.request.DeleteBlinkRequest;
import com.example.demo.core.request.DeleteProjectRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.db.model.ApplyProject;
import com.example.demo.db.model.ApplyProjectPK;
import com.example.demo.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
