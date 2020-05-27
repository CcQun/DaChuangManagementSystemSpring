package com.example.demo.core.response.custommodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author CcQun
 * @Date 2020/5/27 7:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectWithTName {
    private Integer project_number;
    private Integer Create_Teacher_Number;
    private String Create_Teacher_Name;
    private Integer Direct_Teacher_Number;
    private String Direct_Teacher_Name;
    private Integer Create_Student_Number;
    private String Create_Student_Name;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date create_time;
    private String project_name;
    private String project_description;
    private String project_college;
    private String project_field;
    private Integer project_State;
}
