package com.example.demo.db.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProjectStudent {
    private Integer project_number;
    private String Project_Name;
    private String Project_Description;
    private String Project_College;
    private String Project_Field;
    private Integer student_number;
    private String student_name;
}
