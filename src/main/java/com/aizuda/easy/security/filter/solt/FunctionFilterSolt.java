package com.aizuda.easy.security.filter.solt;

import com.aizuda.easy.security.DefaultHandlerFactory;
import com.aizuda.easy.security.HandlerFactory;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.filter.FilterSolt;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.server.wrapper.RequestDataWrapper;
import com.aizuda.easy.security.server.wrapper.ResponseDataWrapper;
import com.aizuda.easy.security.util.LocalUtil;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class FunctionFilterSolt extends DefaultHandlerFactory implements FilterSolt, HandlerFactory {

    private static final Logger log = LoggerFactory.getLogger(FunctionFilterSolt.class);

    @Override
    public void beforeSolt(RequestDataWrapper request, ResponseDataWrapper response) throws BasicException, IOException {
        request.setCharacterEncoding(Charsets.UTF_8.toString());
        response.setCharacterEncoding(Charsets.UTF_8.toString());
        LocalUtil.create();
        for (FunctionHandler functionHandler : getFunctionHandlers()) {
            log.debug("exec handler : {}",functionHandler.getClass().getName());
            functionHandler.exec(request, response);
        }
    }

    @Override
    public void afterSolt(RequestDataWrapper request, ResponseDataWrapper response) throws BasicException, IOException {
        ((ResponseDataWrapper)response).changeContent();
    }
}
