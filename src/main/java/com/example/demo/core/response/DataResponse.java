package com.example.demo.core.response;

import lombok.Data;

/**
 * @Author CcQun
 * @Date 2020/5/16 13:21
 */
@Data
public class DataResponse<T> {
    private int code;
    private String msg;
    private T data;
}
