package com.aizuda.easy.security;

import com.aizuda.easy.security.handler.FunctionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class DefaultHandlerFactory extends HttpServletRequestWrapper implements HandlerFactory {

    private Map<Integer, FunctionHandler> functionHandlerMap = new ConcurrentHashMap<>();

    public DefaultHandlerFactory(HttpServletRequest request) {
        super(request);
    }

    @Override
    public void register(Integer index, FunctionHandler functionHandler) {
        functionHandlerMap.put(index, functionHandler);
    }

    @Override
    public void remove(Integer index) {
        functionHandlerMap.remove(index);
    }

    @Override
    public FunctionHandler getFunctionHandler(Integer index) {
        return functionHandlerMap.get(index);
    }

    @Override
    public Collection<FunctionHandler> getFunctionHandlers() {
        return functionHandlerMap.keySet().stream()
                .sorted()
                .map(i -> functionHandlerMap.get(i))
                .collect(Collectors.toList());
    }

    public boolean containsFunctionHandler(Integer index, FunctionHandler functionHandler) {
        return functionHandlerMap.containsKey(index);
    }

}
