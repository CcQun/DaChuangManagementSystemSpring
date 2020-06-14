package com.example.demo.core.request;

import lombok.Data;

/**
 * @Author CcQun
 * @Date 2020/6/14 10:18
 */
@Data
public class ApplyDirectRequest {
    private Integer project_number;
    private Integer teacher_number;
    private Integer direct_approval;
}
