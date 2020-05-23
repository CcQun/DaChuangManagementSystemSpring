package com.example.demo.controller;

import com.example.demo.core.request.ApplyBlinkRequest;
import com.example.demo.core.request.StudentRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.core.response.ListResponse;
import com.example.demo.db.mapper.ApplyBlinkMapper;
import com.example.demo.db.model.*;
import com.example.demo.db.service.ApplyBlinkService;
import com.example.demo.db.service.BaseService;
import com.example.demo.db.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


    public ApplyController(ApplyBlinkService applyBlinkService, StudentService studentService) {
        this.applyBlinkService = applyBlinkService;
        this.studentService = studentService;
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
