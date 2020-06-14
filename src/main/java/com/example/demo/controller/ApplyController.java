package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.StudentRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.model.*;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BlinkService;
import com.example.demo.db.service.ProjectService;
import com.example.demo.db.service.StudentService;
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

    public ApplyController(ApplyBlinkService applyBlinkService, StudentService studentService, BlinkService blinkService, ProjectService projectService) {
        this.applyBlinkService = applyBlinkService;
        this.studentService = studentService;
        this.blinkService = blinkService;
        this.projectService = projectService;
    }

    //查询blink申请情况
    @RequestMapping("/selectApply")
    public ListResponse<ApplyBlink> selectApply(@RequestBody ApplyBlinkRequest request) {
        Integer blink_number=request.getBlink_number();
        ListResponse<ApplyBlink> response=new ListResponse<>();

        Specification<ApplyBlink> specification=new Specification<ApplyBlink>() {
            @Override
            public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyBlinkPK").get("blinknum"),blink_number);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };

        List<ApplyBlink> list = applyBlinkService.findAll(specification);
        List list2 = new ArrayList();
        if(list.size()==0){
            response.setMsg("No apply");
        }
        else {
            response.setCode(1);
            response.setMsg("Have apply");
            for(int i=0;i<list.size();i++){
                ApplyStudent applyStudent=new ApplyStudent();
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
        Integer student_number=request.getStudent_number();
        System.out.println("aaa"+student_number);
        ListResponse<Student> response=new ListResponse<>();
        List<Student> list = studentService.findAllByStudentNumber(student_number);
        System.out.println(list);
        response.setData(list);
        return response;
    }

    //审核blink申请加入状态
    @RequestMapping("/checkApply")
    public BaseResponse checkApply(@RequestBody ApplyBlinkRequest request) {
        Integer blink_number=request.getBlink_number();
        Integer student_number=request.getStudent_number();
        Integer blink_approval=request.getBlink_approval();

        ApplyBlinkPK applyBlinkPK=new ApplyBlinkPK();
        applyBlinkPK.setBlinknum(blink_number);
        applyBlinkPK.setStudentnum(student_number);

        Specification<ApplyBlink> specification=new Specification<ApplyBlink>() {

            @Override
            public Predicate toPredicate(Root<ApplyBlink> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList=new ArrayList<>();
                Predicate shPredicate=criteriaBuilder.equal(root.get("applyBlinkPK"),applyBlinkPK);
                predicateList.add(shPredicate);
                Predicate[] predicates=new Predicate[predicateList.size()];
                return criteriaBuilder.and(predicateList.toArray(predicates));
            }
        };
        BaseResponse response=new ListResponse<>();

        List<ApplyBlink> list = applyBlinkService.findAll(specification);
        if(list.size()==0){
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
        boolean back1=blinkService.changeState(blink,blink_approval,oldapproval,max);
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
}
