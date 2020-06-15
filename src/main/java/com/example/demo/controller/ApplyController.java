package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.ApplyDirectRequest;
import com.example.demo.core.request.ApplyDirectRequest2;
import com.example.demo.core.request.StudentRequest;
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
import java.util.*;

@RestController()
@RequestMapping("/apply")
public class ApplyController {
    @Autowired
    private final ApplyBlinkService applyBlinkService;
    @Autowired
    private final StudentService studentService;
    @Autowired
    private final BlinkService blinkService;

    @Autowired
    private final ProjectService projectService;

    @Autowired
    private final ApplyDirectService applyDirectService;

    public ApplyController(ApplyBlinkService applyBlinkService, StudentService studentService, BlinkService blinkService, ProjectService projectService, ApplyDirectService applyDirectService) {
        this.applyBlinkService = applyBlinkService;
        this.studentService = studentService;
        this.blinkService = blinkService;
        this.projectService = projectService;
        this.applyDirectService = applyDirectService;
    }

    //查询blink申请情况
    @RequestMapping("/selectApply")
    public ListResponse<ApplyBlink> selectApply(@RequestBody ApplyBlinkRequest request) {
        Integer blink_number = request.getBlink_number();
        ListResponse<ApplyBlink> response = new ListResponse<>();

        Specification<ApplyBlink> specification = new Specification<ApplyBlink>() {
            @Override
            public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Predicate shPredicate = criteriaBuilder.equal(root.get("applyBlinkPK").get("blinknum"), blink_number);
                predicateList.add(shPredicate);
                Predicate[] predicates = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyBlink> list = applyBlinkService.findAll(specification);
        List list2 = new ArrayList();
        if (list.size() == 0) {
            response.setMsg("No apply");
        } else {
            response.setCode(1);
            response.setMsg("Have apply");
            for (int i = 0; i < list.size(); i++) {
                ApplyStudent applyStudent = new ApplyStudent();
                List<Student> list1 = studentService.findAllByStudentNumber(list.get(i).getApplyBlinkPK().getStudentnum());
                applyStudent.setBlinknum(list.get(i).getApplyBlinkPK().getBlinknum());
                applyStudent.setBlink_approval(list.get(i).getBlink_Approval());
                applyStudent.setStudent_number(list.get(i).getApplyBlinkPK().getStudentnum());
                applyStudent.setStudent_name(list1.get(0).getStudent_name());
                applyStudent.setStudent_gender(list1.get(0).getStudent_gender());
                applyStudent.setStudent_college(list1.get(0).getStudent_college());
                applyStudent.setMajor(list1.get(0).getMajor());
                applyStudent.setStudent_introduction(list1.get(0).getStudent_introduction());
                applyStudent.setDate(list1.get(0).getDate());
                list2.add(applyStudent);
            }
            response.setData(list2);
        }
        return response;
    }

    //根据ID查看学生信息
    @RequestMapping("/selectStudent")
    public BaseResponse selectStudent(@RequestBody StudentRequest request) {
        Integer student_number = request.getStudent_number();
        System.out.println("aaa" + student_number);
        ListResponse<Student> response = new ListResponse<>();
        List<Student> list = studentService.findAllByStudentNumber(student_number);
        System.out.println(list);
        response.setData(list);
        return response;
    }

    //审核blink申请加入状态
    @RequestMapping("/checkApply")
    public BaseResponse checkApply(@RequestBody ApplyBlinkRequest request) {
        Integer blink_number = request.getBlink_number();
        Integer student_number = request.getStudent_number();
        Integer blink_approval = request.getBlink_approval();

        ApplyBlinkPK applyBlinkPK = new ApplyBlinkPK();
        applyBlinkPK.setBlinknum(blink_number);
        applyBlinkPK.setStudentnum(student_number);

        Specification<ApplyBlink> specification = new Specification<ApplyBlink>() {

            @Override
            public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Predicate shPredicate = criteriaBuilder.equal(root.get("applyBlinkPK"), applyBlinkPK);
                predicateList.add(shPredicate);
                Predicate[] predicates = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
        BaseResponse response = new ListResponse<>();

        List<ApplyBlink> list = applyBlinkService.findAll(specification);
        if (list.size() == 0) {
            response.setCode(0);
            response.setMsg("无此学生或无此队伍");
            return response;
        }

        ApplyBlink applyBlink1= list.get(0);
        int blinknum=applyBlink1.getApplyBlinkPK().getBlinknum();
        int oldapproval=applyBlink1.getBlink_Approval();
        List<Blink> list1=blinkService.findAllByBlinkNumber(blinknum);
        Blink blink=list1.get(0);
        int max=this.getMaxProjectNumber();
        boolean back1=blinkService.changeState(blink,blink_approval,oldapproval,max,student_number);
        //boolean back1=blinkService.findAllByBlinkNumber(blinknum,blink_approval);
        if(!back1){
            response.setCode(0);
            response.setMsg("已满员");
            return response;
        }
        else{
            boolean back=applyBlinkService.findAllByBlinkStudent(applyBlink1,blink_approval);
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
    //查看自己申请blink的通过情况
    @ResponseBody
    @RequestMapping("/getApprove")
    public JSONObject getApprove(@RequestBody ApplyBlinkRequest request) {
        Integer student_number=request.getStudent_number();
        ListResponse<ApplyBlink> response=new ListResponse<>();

        Specification<ApplyBlink> specification=new Specification<ApplyBlink>() {
            @Override
            public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyBlinkPK").get("studentnum"),student_number);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyBlink> list = applyBlinkService.findAll(specification);

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
                int blink_num=list.get(i).getApplyBlinkPK().getBlinknum();
                List<Blink> blink=blinkService.findAllByBlinkNumber(blink_num);
                jsonlist.get(num).put("blink_number",list.get(i).getApplyBlinkPK().getBlinknum());
                jsonlist.get(num).put("blink_approval",list.get(i).getBlink_Approval());
                jsonlist.get(num).put("blink_content",blink.get(0).getBlink_content());
                jsonlist.get(num).put("blink_state",blink.get(0).getBlink_state());
                jsonlist.get(num).put("blink_title",blink.get(0).getBlink_title());
                jsonlist.get(num).put("blink_college",blink.get(0).getBlink_college());
                jsonlist.get(num).put("blink_field",blink.get(0).getBlink_field());
                jsonlist.get(num).put("blink_create_time",blink.get(0).getCreate_time());
                num++;
            }
            object.put("code",1);
            object.put("msg","yes");
            object.put("data",jsonlist);
            return object;
        }
    }

    //申请指导老师
    @RequestMapping("/applydirect")
    public BaseResponse applydirect(@RequestBody ApplyDirectRequest request){
        ApplyDirectPK applyDirectPK = ApplyDirectPK.builder()
                .projectnum(request.getProject_number())
                .teachernum(request.getTeacher_number())
                .build();
        ApplyDirect applyDirect = ApplyDirect.builder()
                .applyDirectPK(applyDirectPK)
                .Direct_Approval(0)
                .build();
        applyDirectService.getMapper().save(applyDirect);
        BaseResponse response = new BaseResponse();
        response.setCode(1);
        return response;
    }

    //获得最大project number
    public Integer getMaxProjectNumber() {
        List<Project> projects = projectService.findAll();
        Integer maxProjectNumber = 0;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProject_number() > maxProjectNumber) {
                maxProjectNumber = projects.get(i).getProject_number();
            }
        }
        return maxProjectNumber;
    }

    //老师审批指导老师请求
    @RequestMapping("/checkDirectApply")
    public BaseResponse checkDirectApply(@RequestBody ApplyDirectRequest2 request) {
        Integer project_number = request.getProject_number();
        Integer teacher_number = request.getTeacher_number();
        Integer direct_approval = request.getDirect_approval();

        ApplyDirectPK applyDirectPK = new ApplyDirectPK();
        applyDirectPK.setProjectnum(project_number);
        applyDirectPK.setTeachernum(teacher_number);

        Specification<ApplyDirect> specification = new Specification<ApplyDirect>() {

            @Override
            public Predicate toPredicate(Root<ApplyDirect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                Predicate shPredicate = criteriaBuilder.equal(root.get("applyDirectPK"), applyDirectPK);
                predicateList.add(shPredicate);
                Predicate[] predicates = new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
        BaseResponse response = new ListResponse<>();

        List<ApplyDirect> list = applyDirectService.findAll(specification);
        if (list.size() == 0) {
            response.setCode(0);
            response.setMsg("项目没有向该老师发起申请");
            return response;
        }
        ApplyDirect applyDirect1 = list.get(0);

        List<Project> list1 = projectService.findAllByProjectNumber(project_number);
        Project project = list1.get(0);

        if (project.getDirect_Teacher_Number() != null) {
            response.setCode(0);
            response.setMsg("此队伍已有其他指导老师");
            return response;
        } else {
            boolean back = applyDirectService.changeState(applyDirect1, project, direct_approval, teacher_number);

            if (back) {
                response.setCode(1);
                response.setMsg("审批成功");
            } else {
                response.setCode(0);
                response.setMsg("审批失败");
            }
            return response;
        }
    }

    //教师查看申请
    @RequestMapping("/ViewInstructorApplication")
    public ListResponse<ApplyDirect> ViewInstructorApplication(@RequestBody ApplyDirectRequest request) {
        Integer teacher_number=request.getTeacher_number();
        ListResponse<ApplyDirect> response=new ListResponse<>();

        Specification<ApplyDirect> specification=new Specification<ApplyDirect>() {
            @Override
            public Predicate toPredicate(Root<ApplyDirect> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyDirectPKv").get("teachernum"),teacher_number);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyDirect> list = applyDirectService.findAll(specification);
        List list2 = new ArrayList();
        if(list.size()==0){
            response.setMsg("No apply");
        }
        else {
            response.setCode(1);
            response.setMsg("Have apply");
            for(int i=0;i<list.size();i++){
                ProjectStudent projectStudent=new ProjectStudent();
                List<Project> list1 = projectService.findAllByProjectNumber(list.get(i).getApplyDirectPK().getProjectnum());
                List<Student> list3 = studentService.findAllByStudentNumber(list1.get(0).getCreate_Student_Number());
                projectStudent.setProject_College(list1.get(0).getProject_College());
                projectStudent.setProject_Description(list1.get(0).getProject_Description());
                projectStudent.setProject_Field(list1.get(0).getProject_Field());
                projectStudent.setProject_Name(list1.get(0).getProject_Name());
                projectStudent.setProject_number(list.get(i).getApplyDirectPK().getProjectnum());
                projectStudent.setStudent_number(list1.get(0).getCreate_Student_Number());
                projectStudent.setStudent_name(list3.get(0).getStudent_name());
                list2.add(projectStudent);
            }
            response.setData(list2);
        }
        return response;
    }
}
