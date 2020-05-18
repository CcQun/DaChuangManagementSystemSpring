package com.example.demo.db.model;

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
    private Integer studentNumber;

    @Column(name="Student_Name")
    private String studentName;

    @Column(name="Student_Gender")
    private String studentGender;

    @Column(name="Student_College ")
    private String studentCollege;

    @Column(name="Enrollment_Year")
    private Date date;

    @Column(name="Major")
    private String major;

    @Column(name="Student_Introduction")
    private String studentIntroduction;

    @Column(name="Student_Password")
    private String studentPassword;
}
