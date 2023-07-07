package com.aizuda.easy.security;

import cn.hutool.core.util.ObjectUtil;
import com.aizuda.easy.security.handler.FunctionHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultHandlerFactory extends AbstractHandlerFactory implements HandlerFactory {

    private Map<Integer, FunctionHandler> functionHandlerMap = new ConcurrentHashMap<>();

    public void register(Integer index, FunctionHandler functionHandler) {
        if(ObjectUtil.isNull(index) || ObjectUtil.isNull(functionHandler)){
            return;
        }
        functionHandlerMap.put(index, functionHandler);
    }

    public void remove(Integer index) {
        functionHandlerMap.remove(index);
    }


    @Override
    public Map<Integer, FunctionHandler> getFactoryMap() {
        return functionHandlerMap;
    }
}
