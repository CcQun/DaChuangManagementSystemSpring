package com.example.demo.core.request;

import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @Author CcQun
 * @Date 2020/5/19 8:15
 */
@Data
public class PublishBlinkRequest {
    private Integer student_number;
    private String blink_title;
    private String blink_content;
    private String blink_college;
    private String blink_field;
}
