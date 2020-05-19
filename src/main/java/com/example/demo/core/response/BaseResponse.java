package com.example.demo.core.response;

import lombok.Data;

/**
 * @Author CcQun
 * @Date 2020/5/17 10:39
 */
@Data
public class BaseResponse {

    private int code;
    private String msg;
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
