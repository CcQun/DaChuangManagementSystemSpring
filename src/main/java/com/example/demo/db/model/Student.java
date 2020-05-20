package com.example.demo.db.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:11
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "STUDENT")
public class Student {
    @Id
    @Column(name="Student_Number")
    private Integer student_number;

    @Column(name="Student_Name")
    private String student_name;

    @Column(name="Student_Gender")
    private String student_gender;

    @Column(name="Student_College")
    private String student_college;

    @Column(name="Enrollment_Year")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    private Date date;

    @Column(name="Major")
    private String major;

    @Column(name="Student_Introduction")
    private String student_introduction;

    @Column(name="Student_Password")
    private String student_password;
}
