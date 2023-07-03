package com.aizuda.easy.security;

import com.aizuda.easy.security.handler.FunctionHandler;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractHandlerFactory implements HandlerFactory{

    @Override
    public FunctionHandler getFunctionHandler(Integer index) {
        return getFactoryMap().get(index);
    }

    @Override
    public Collection<FunctionHandler> getFunctionHandlers() {
        Map<Integer, FunctionHandler> factoryMap = getFactoryMap();
        return factoryMap
                .keySet().stream()
                .sorted()
                .map(factoryMap::get)
                .collect(Collectors.toList());
    }

    @Override
    public boolean contain(Integer index, FunctionHandler functionHandler) {
        return getFactoryMap().containsKey(index);
    }

    public abstract Map<Integer, FunctionHandler> getFactoryMap();

}
