package com.example.demo.core.request;

import lombok.Data;

/**
 * @Author CcQun
 * @Date 2020/5/26 18:21
 */
@Data
public class PublishProjectRequest {
    private Integer create_teacher_number;
    private String project_name;
    private String project_description;
    private String project_college;
    private String project_field;
}
