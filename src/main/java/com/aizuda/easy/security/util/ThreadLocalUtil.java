package com.aizuda.easy.security.util;

import com.aizuda.easy.security.consts.FilterConst;
import com.aizuda.easy.security.exp.IErrorCode;
import com.aizuda.easy.security.exp.impl.BasicException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ThreadLocalUtil {

    public static ThreadLocal<ThreadLocalEntity> threadLocal = new ThreadLocal<ThreadLocalEntity>();


    public static void forward(HttpServletRequest request, HttpServletResponse response, String errUrl, IErrorCode iErrorCode) throws ServletException, IOException {
        String url = errUrl + String.format(FilterConst.PARAMES, iErrorCode.getCode(),iErrorCode.getMsg());
        request.getRequestDispatcher(url).forward(request,response);
    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String errUrl, BasicException basicException) throws ServletException, IOException {
        String url = errUrl + String.format(FilterConst.PARAMES, basicException.getCode(),basicException.getMsg());
        request.getRequestDispatcher(url).forward(request,response);
    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String errUrl, Integer code,String msg) throws ServletException, IOException {
        String url = errUrl + String.format(FilterConst.PARAMES, code, msg);
        request.getRequestDispatcher(url).forward(request,response);
    }

    public static class ThreadLocalEntity{

        private Object user;

        private Boolean decrypt = false;

        private Boolean special = false;

        private Boolean project = false;

        public Boolean getSpecial() {
            return special;
        }

        public void setSpecial(Boolean special) {
            this.special = special;
        }

        public Boolean getProject() {
            return project;
        }

        public void setProject(Boolean project) {
            this.project = project;
        }

        public Boolean getDecrypt() {
            return decrypt;
        }

        public void setDecrypt(Boolean decrypt) {
            this.decrypt = decrypt;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }
    }

}
