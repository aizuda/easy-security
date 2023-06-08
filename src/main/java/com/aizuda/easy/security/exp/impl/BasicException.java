package com.aizuda.easy.security.exp.impl;

import com.aizuda.easy.security.exp.IErrorCode;


public class BasicException extends Exception {

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private BasicException(){
    }

    public BasicException(Integer code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BasicException(IErrorCode iErrorCode){
        super(iErrorCode.getMsg());
        this.code = iErrorCode.getCode();
        this.msg = iErrorCode.getMsg();
    }

}
