package com.example.demo.core.request;

import lombok.Data;

@Data
public class ApplyProjectRequest {
    private Integer project_number;
    private Integer student_number;
    private Integer project_approval;
}
