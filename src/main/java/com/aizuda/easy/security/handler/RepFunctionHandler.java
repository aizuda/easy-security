package com.aizuda.easy.security.handler;

import com.aizuda.easy.security.exp.impl.BasicException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface RepFunctionHandler extends FunctionHandler{

    String exec(HttpServletResponse response, String json) throws BasicException, IOException;

}
