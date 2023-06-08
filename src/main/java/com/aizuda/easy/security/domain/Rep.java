package com.aizuda.easy.security.domain;

import cn.hutool.http.HttpStatus;
import com.aizuda.easy.security.exp.IErrorCode;


public class Rep<T>{

    private Integer code;
    private String msg;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    protected Rep(Integer code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Rep<T> ok(){
        return new Rep<T>(HttpStatus.HTTP_OK,null,null);
    }

    public static <T> Rep<T> ok(T data){
        return new Rep<T>(HttpStatus.HTTP_OK,null,data);
    }

    public static <T> Rep<T> ok(IErrorCode iErrorCode){
        return new Rep<T>(iErrorCode.getCode(),iErrorCode.getMsg(),null);
    }

    public static <T> Rep<T> ok(IErrorCode iErrorCode, T obj){
        return new Rep<T>(iErrorCode.getCode(),iErrorCode.getMsg(),obj);
    }

    public static <T> Rep<T> error(IErrorCode iErrorCode){
        return new Rep<T>(iErrorCode.getCode(),iErrorCode.getMsg(),null);
    }

    public static <T> Rep<T> error(Rep<T> rep){
        return new Rep<T>(rep.getCode(), rep.getMsg(),null);
    }

    public static <T> Rep<T> error(Integer code, String msg){
        return new Rep<T>(code,msg,null);
    }

    public Rep() {
    }

}