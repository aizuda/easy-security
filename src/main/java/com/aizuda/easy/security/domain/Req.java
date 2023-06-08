package com.aizuda.easy.security.domain;

import com.aizuda.easy.security.annotation.yapi.YApiRule;

public class Req<T,U> {

    @YApiRule(required = true)
    private T data;

    @YApiRule(hide = true)
    private U user;

    @YApiRule(hide = true)
    private String token;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public U getUser() {
        return user;
    }

    public void setUser(U user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
