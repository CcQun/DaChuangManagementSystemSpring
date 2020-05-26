package com.example.demo.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author CcQun
 * @Date 2020/5/26 17:37
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "Teacher")
public class Teacher {
    @Id
    private Integer teacher_number;
    private String teacher_name;
    private String teacher_gender;
    private String teacher_college;
    private String teacher_introduction;
    private String teacher_password;
}
