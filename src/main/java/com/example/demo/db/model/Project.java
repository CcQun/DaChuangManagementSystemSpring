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
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name="PROJECT")
public class Project {
    @Id
    @Column(name="Project_Number")
    private Integer project_number;

    @Column(name="Create_Teacher_Number")
    private Integer Create_Teacher_Number;

    @Column(name="Direct_Teacher_Number")
    private Integer Direct_Teacher_Number;

    @Column(name="Create_Student_Number")
    private Integer Create_Student_Number;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name="Create_Time")
    private Date creat_time;

    @Column(name="Project_Name")
    private String Project_Name;

    @Column(name="Project_Description")
    private String Project_Description;

    @Column(name="Project_College")
    private String Project_College;

    @Column(name="Project_Field")
    private String Project_Field;

    @Column(name="Project_State")
    private Integer Project_State;

}
