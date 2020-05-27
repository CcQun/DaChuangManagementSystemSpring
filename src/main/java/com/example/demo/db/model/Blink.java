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
@Table(name="BLINK")
public class Blink {
    @Id
    @Column(name="Blink_Number")
    private Integer blink_number;

    @Column(name="Student_Number")
    private Integer student_number;

    @Column(name="Blink_Title")
    private String blink_title;

    @Column(name="Blink_Content")
    private String blink_content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name="Create_Time")
    private Date create_time;

    @Column(name="Blink_College")
    private String blink_college;

    @Column(name="Blink_Field")
    private String blink_field;

    @Column(name="Blink_State")
    private Integer blink_state;
}
