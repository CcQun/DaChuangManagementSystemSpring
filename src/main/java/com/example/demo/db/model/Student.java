package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer studentNumber;
    private String studentName;
    private String studentGender;
    private String studentCollege;
    private Date date;
    private String major;
    private String studentIntroduction;
    private String studentPassword;
}
