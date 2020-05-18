package com.example.demo.core.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author CcQun
 * @Date 2020/5/18 19:56
 */

@Data
public class LoginRequest {
    private Integer student_number;
    private String student_password;
}
