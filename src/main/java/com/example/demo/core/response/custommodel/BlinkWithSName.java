package com.example.demo.core.response.custommodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author CcQun
 * @Date 2020/5/21 9:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlinkWithSName {
    private Integer blink_number;
    private Integer student_number;
    private String student_name;
    private String blink_title;
    private String blink_content;
    private Date creat_time;
    private String blink_college;
    private String blink_field;
    private Integer blink_state;
}
