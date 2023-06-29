package com.aizuda.easy.security.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultHandlerFactory {

    private Map<Integer, FunctionHandler> functionHandlerMap = new ConcurrentHashMap<>();

    public void register(Integer index, FunctionHandler functionHandler) {
        functionHandlerMap.put(index, functionHandler);
    }

    public boolean containsFunctionHandler(Integer index, FunctionHandler functionHandler) {
        return functionHandlerMap.containsKey(index);
    }

    public FunctionHandler getFunctionHandler(Integer index) {
        return functionHandlerMap.get(index);
    }

    public Collection<FunctionHandler> getFunctionHandlers() {
        return functionHandlerMap.keySet().stream()
                .sorted()
                .map(i -> functionHandlerMap.get(i))
                .collect(Collectors.toList());
    }

}
