package com.example.demo.core.response.custommodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author CcQun
 * @Date 2020/5/27 7:58
 */
@Data
public class ProjectWithTName {
    private Integer project_number;
    private Integer Create_Teacher_Number;
    private Integer Direct_Teacher_Number;
    private Integer Create_Student_Number;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date create_time;
    private String project_name;
    private String project_description;
    private String project_college;
    private String project_field;
    private Integer project_State;
}
