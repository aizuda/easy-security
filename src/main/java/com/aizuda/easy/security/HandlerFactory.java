package com.aizuda.easy.security;

import com.aizuda.easy.security.handler.FunctionHandler;

import java.util.Collection;

public interface HandlerFactory {

    FunctionHandler getFunctionHandler(Integer index);

    Collection<FunctionHandler> getFunctionHandlers();

    boolean contain(Integer index, FunctionHandler functionHandler);
}
