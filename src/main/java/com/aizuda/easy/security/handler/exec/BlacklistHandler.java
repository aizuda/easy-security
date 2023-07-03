package com.aizuda.easy.security.handler.exec;

import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import com.aizuda.easy.security.util.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class BlacklistHandler extends AbstractFunctionHandler implements ReqFunctionHandler {
    private static final Logger log = LoggerFactory.getLogger(BlacklistHandler.class);
    @Override
    public String exec(HttpServletRequest request, String json) throws BasicException {
        String ip = IPUtil.getIp(request);
        log.debug("Accessing the user's IP: {}",ip);
        if(properties.getBlackList().contains(ip)){
            throw new BasicException(BasicCode.BASIC_CODE_99984);
        }
        return json;
    }

    @Override
    public Integer getIndex() {
        return Integer.MIN_VALUE;
    }

}
