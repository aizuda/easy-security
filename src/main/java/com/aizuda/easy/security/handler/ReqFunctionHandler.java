package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.exp.impl.BasicException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ReqFunctionHandler extends FunctionHandler {

    String exec(HttpServletRequest request, String json) throws BasicException, IOException;


}
