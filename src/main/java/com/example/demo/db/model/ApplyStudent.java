package com.example.demo.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ApplyStudent{
    @Id
    private Integer blinknum;
    private Integer blink_approval;
    private Integer student_number;
    private String student_name;
    private String student_gender;
    private String student_college;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date date;
    private String major;
    private String student_introduction;
}
