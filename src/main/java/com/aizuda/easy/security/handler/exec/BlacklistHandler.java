package com.aizuda.easy.security.handler.exec;

import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.aizuda.easy.security.util.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlacklistHandler implements FunctionHandler {
    private static final Logger log = LoggerFactory.getLogger(BlacklistHandler.class);
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response, SecurityProperties properties) throws BasicException {
        String ip = IPUtil.getIp(request);
        log.debug("Accessing the user's IP: {}",ip);
        if(properties.getBlackList().contains(ip)){
            throw new BasicException(BasicCode.BASIC_CODE_99984);
        }
    }

    @Override
    public Integer getIndex() {
        return 0;
    }

}
