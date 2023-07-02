package com.aizuda.easy.security;

import com.aizuda.easy.security.handler.FunctionHandler;

import java.util.Collection;

public interface HandlerFactory {

    void register(Integer index, FunctionHandler functionHandler);

    void remove(Integer index);

    FunctionHandler getFunctionHandler(Integer index);

    Collection<FunctionHandler> getFunctionHandlers();
}
