package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.ApplyProjectRequest;
import com.example.demo.core.request.DeleteProjectRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.*;
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

    @Autowired
    private final StudentService studentService;

    public ProjectController(ApplyProjectService applyProjectService, ProjectService projectService, StudentService studentService) {
        this.applyProjectService = applyProjectService;
        this.projectService=projectService;
        this.studentService = studentService;
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

    //查看自己申请项目的通过情况
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
                int projectNum=list.get(i).getApplyProjectPK().getProjectnum();
                List<Project> list2=projectService.findAllByProjectNumber(projectNum);
                //int teacher_Num=list2.get(0).getCreate_Teacher_Number();
                //String teacherName=list2.get(0).getCreate_Teacher_Number();
                jsonlist.get(num).put("project_name",list2.get(0).getProject_Name());
                jsonlist.get(num).put("create_teacher",list2.get(0).getCreate_Teacher_Number());
                jsonlist.get(num).put("create_time",list2.get(0).getCreat_time());
                jsonlist.get(num).put("project_college",list2.get(0).getProject_College());
                jsonlist.get(num).put("project_field",list2.get(0).getProject_Field());
                jsonlist.get(num).put("project_description",list2.get(0).getProject_Description());
                jsonlist.get(num).put("project_state",list2.get(0).getProject_State());
                num++;
            }
            object.put("code",1);
            object.put("msg","yes");
            object.put("data",jsonlist);
            return object;
        }
    }

    //查询自己发布的project申请情况
    @RequestMapping("/selectProjectApply")
    public ListResponse<ApplyProject> selectApply(@RequestBody ApplyProjectRequest request) {
        Integer project_number=request.getProject_number();
        ListResponse<ApplyProject> response=new ListResponse<>();

        Specification<ApplyProject> specification=new Specification<ApplyProject>() {
            @Override
            public Predicate toPredicate(Root<ApplyProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyProjectPK").get("projectnum"),project_number);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyProject> list = applyProjectService.findAll(specification);
        List list2 = new ArrayList();
        if(list.size()==0){
            response.setMsg("No apply");
        }
        else {
            response.setCode(1);
            response.setMsg("Have apply");
            for(int i=0;i<list.size();i++){
                ApplyProjectStudent applyProjectStudent=new ApplyProjectStudent();
                List<Student> list1 = studentService.findAllByStudentNumber(list.get(i).getApplyProjectPK().getStudentnum());
                applyProjectStudent.setProjectnum(list.get(i).getApplyProjectPK().getProjectnum());
                applyProjectStudent.setProject_approval(list.get(i).getProject_Approval());
                applyProjectStudent.setStudent_number(list.get(i).getApplyProjectPK().getStudentnum());
                applyProjectStudent.setStudent_name(list1.get(0).getStudent_name());
                applyProjectStudent.setStudent_gender(list1.get(0).getStudent_gender());
                applyProjectStudent.setStudent_college(list1.get(0).getStudent_college());
                applyProjectStudent.setMajor(list1.get(0).getMajor());
                applyProjectStudent.setStudent_introduction(list1.get(0).getStudent_introduction());
                applyProjectStudent.setDate(list1.get(0).getDate());
                list2.add(applyProjectStudent);
            }
            response.setData(list2);
        }
        return response;
    }

    //审核项目申请加入状态
    @RequestMapping("/checkProjectApply")
    public BaseResponse checkApply(@RequestBody ApplyProjectRequest request) {
        Integer project_number=request.getProject_number();
        Integer student_number=request.getStudent_number();
        Integer project_approval=request.getProject_approval();

        ApplyProjectPK applyProjectPK=new ApplyProjectPK();
        applyProjectPK.setProjectnum(project_number);
        applyProjectPK.setStudentnum(student_number);

        Specification<ApplyProject> specification=new Specification<ApplyProject>() {

            @Override
            public Predicate toPredicate(Root<ApplyProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyProjectPK"),applyProjectPK);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
        BaseResponse response=new ListResponse<>();

        List<ApplyProject> list = applyProjectService.findAll(specification);
        if(list.size()==0){
            response.setCode(0);
            response.setMsg("无此学生或无此队伍");
            return response;
        }

        ApplyProject applyProject1= list.get(0);
        boolean back=applyProjectService.findAllByProjectStudent(applyProject1,project_approval);
        if(back){
            response.setCode(1);
            response.setMsg("已改");
        }
        else {
            response.setCode(0);
            response.setMsg("失败");
        }
        return response;
    }

}
