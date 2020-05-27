package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.core.request.ApplyProjectRequest;
import com.example.demo.core.request.DeleteBlinkRequest;
import com.example.demo.core.request.DeleteProjectRequest;
import com.example.demo.core.request.SearchBlinkRequest;
import com.example.demo.core.response.BaseResponse;
import com.example.demo.db.model.*;
import com.example.demo.db.service.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    private final StudentService studentService;
    @Autowired
    private final TeacherService teacherService;
    @Autowired
    private final ProjectService projectService;
    public ProjectController(ApplyProjectService applyProjectService,ProjectService projectService,TeacherService teacherService,StudentService studentService) {
        this.applyProjectService = applyProjectService;
        this.projectService=projectService;
        this.studentService=studentService;
        this.teacherService=teacherService;

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

    //关键字搜索project
    @ResponseBody
    @RequestMapping("/searchproject")
    public JSONObject searchproject(@RequestBody SearchBlinkRequest request) throws JSONException {

        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        JSONObject object=new JSONObject();

        String str = request.getKeywords();
        List<Project> projects = projectService.findAll();
        if (str.length()==0||str==null||str.replaceAll("\\s*", "").length()==0){
            object.put("code", 1);
            object.put("msg", "yes");
            object.put("data", jsonlist);
            return object;
        }
        int num = 0;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).getProject_Description().indexOf(str) != -1) {
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("project_number",projects.get(i).getProject_number());
                jsonlist.get(num).put("create_teacher_number",projects.get(i).getCreate_Teacher_Number());
                jsonlist.get(num).put("student_number",projects.get(i).getCreate_Student_Number());
                jsonlist.get(num).put("direct_teacher_number",projects.get(i).getDirect_Teacher_Number());
                Integer studentnum=projects.get(i).getCreate_Student_Number();
                Integer create_teacher_number=projects.get(i).getCreate_Teacher_Number();
                Integer direct_teacher_number=projects.get(i).getDirect_Teacher_Number();
                if(studentnum==null){
                    jsonlist.get(num).put("student_name",null);
                }
                else{
                    jsonlist.get(num).put("student_name",studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                }
                if(create_teacher_number==null){
                    jsonlist.get(num).put("create_teacher_name",null);
                }
                else{
                    jsonlist.get(num).put("create_teacher_name",teacherService.findAllByTeacherNumber(create_teacher_number).get(0).getTeacher_name());
                }
                if(direct_teacher_number==null){
                    jsonlist.get(num).put("direct_teacher_name",null);
                }
                else{
                    jsonlist.get(num).put("direct_teacher_name",teacherService.findAllByTeacherNumber(direct_teacher_number).get(0).getTeacher_name());
                }
                jsonlist.get(num).put("project_Name",projects.get(i).getProject_Name());
                jsonlist.get(num).put("project_Description",projects.get(i).getProject_Description());
                jsonlist.get(num).put("project_College",projects.get(i).getProject_College());
                jsonlist.get(num).put("project_Field",projects.get(i).getProject_Field());
                jsonlist.get(num).put("project_State",projects.get(i).getProject_State());
                jsonlist.get(num).put("creat_time",projects.get(i).getCreat_time());
                num++;
            }
            else if(projects.get(i).getProject_Name().indexOf(str)!= -1){
                jsonlist.add(new JSONObject());
                jsonlist.get(num).put("project_number",projects.get(i).getProject_number());
                jsonlist.get(num).put("create_teacher_number",projects.get(i).getCreate_Teacher_Number());
                jsonlist.get(num).put("student_number",projects.get(i).getCreate_Student_Number());
                jsonlist.get(num).put("direct_teacher_number",projects.get(i).getDirect_Teacher_Number());
                Integer studentnum=projects.get(i).getCreate_Student_Number();
                Integer create_teacher_number=projects.get(i).getCreate_Teacher_Number();
                Integer direct_teacher_number=projects.get(i).getDirect_Teacher_Number();
                if(studentnum==null){
                    jsonlist.get(num).put("student_name",null);
                }
                else{
                    jsonlist.get(num).put("student_name",studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
                }
                if(create_teacher_number==null){
                    jsonlist.get(num).put("create_teacher_name",null);
                }
                else{
                    jsonlist.get(num).put("create_teacher_name",teacherService.findAllByTeacherNumber(create_teacher_number).get(0).getTeacher_name());
                }
                if(direct_teacher_number==null){
                    jsonlist.get(num).put("direct_teacher_name",null);
                }
                else{
                    jsonlist.get(num).put("direct_teacher_name",teacherService.findAllByTeacherNumber(direct_teacher_number).get(0).getTeacher_name());
                }
                jsonlist.get(num).put("project_Name",projects.get(i).getProject_Name());
                jsonlist.get(num).put("project_Description",projects.get(i).getProject_Description());
                jsonlist.get(num).put("project_College",projects.get(i).getProject_College());
                jsonlist.get(num).put("project_Field",projects.get(i).getProject_Field());
                jsonlist.get(num).put("project_State",projects.get(i).getProject_State());
                jsonlist.get(num).put("creat_time",projects.get(i).getCreat_time());
                num++;
            }
        }
        object.put("code",1);
        object.put("msg","yes");
        object.put("data",jsonlist);
        return object;
    }

    //查询project
    @ResponseBody
    @RequestMapping("/queryproject")
    public JSONObject searchproject() throws JSONException {

        List<JSONObject> jsonlist=new ArrayList<JSONObject>();
        JSONObject object=new JSONObject();
        List<Project> projects = projectService.findAll();
        int num=0;
        for (int i = 0; i<projects.size(); i++){
            jsonlist.add(new JSONObject());
            jsonlist.get(num).put("project_number",projects.get(i).getProject_number());
            jsonlist.get(num).put("create_teacher_number",projects.get(i).getCreate_Teacher_Number());
            jsonlist.get(num).put("student_number",projects.get(i).getCreate_Student_Number());
            jsonlist.get(num).put("direct_teacher_number",projects.get(i).getDirect_Teacher_Number());
            Integer studentnum=projects.get(i).getCreate_Student_Number();
            Integer create_teacher_number=projects.get(i).getCreate_Teacher_Number();
            Integer direct_teacher_number=projects.get(i).getDirect_Teacher_Number();
            if(studentnum==null){
                jsonlist.get(num).put("student_name",null);
            }
            else{
                jsonlist.get(num).put("student_name",studentService.findAllByStudentNumber(studentnum).get(0).getStudent_name());
            }
            if(create_teacher_number==null){
                jsonlist.get(num).put("create_teacher_name",null);
            }
            else{
                jsonlist.get(num).put("create_teacher_name",teacherService.findAllByTeacherNumber(create_teacher_number).get(0).getTeacher_name());
            }
            if(direct_teacher_number==null){
                jsonlist.get(num).put("direct_teacher_name",null);
            }
            else{
                jsonlist.get(num).put("direct_teacher_name",teacherService.findAllByTeacherNumber(direct_teacher_number).get(0).getTeacher_name());
            }
            jsonlist.get(num).put("project_Name",projects.get(i).getProject_Name());
            jsonlist.get(num).put("project_Description",projects.get(i).getProject_Description());
            jsonlist.get(num).put("project_College",projects.get(i).getProject_College());
            jsonlist.get(num).put("project_Field",projects.get(i).getProject_Field());
            jsonlist.get(num).put("project_State",projects.get(i).getProject_State());
            jsonlist.get(num).put("creat_time",projects.get(i).getCreat_time());
            num++;
        }
        object.put("code",1);
        object.put("msg","yes");
        object.put("data",jsonlist);
        return object;
    }
}
