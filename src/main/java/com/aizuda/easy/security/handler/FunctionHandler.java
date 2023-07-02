package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.exp.impl.BasicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface FunctionHandler {

    String exec(HttpServletRequest request,String json) throws BasicException, IOException;

    void exec(HttpServletResponse response) throws BasicException, IOException;


    void doExec(HttpServletRequest request,FunctionHandler functionHandler) throws BasicException, IOException;

    void doExec(HttpServletResponse response,FunctionHandler functionHandler) throws BasicException, IOException;

    default Integer getIndex(){
        return 0;
    }

}
