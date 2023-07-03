package com.aizuda.easy.security.filter.wrapper;

import com.aizuda.easy.security.HandlerFactory;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.handler.ReqFunctionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;


public class ReqWrapper extends HttpServletRequestWrapper {

    private static final Logger log = LoggerFactory.getLogger(ReqWrapper.class);
    private String body;

    public ReqWrapper(HttpServletRequest request,HandlerFactory factory) throws IOException, BasicException {
        super(request);
        body = getBodyContent(request);
        for (FunctionHandler functionHandler : factory.getFunctionHandlers()) {
            if(functionHandler instanceof ReqFunctionHandler) {
                log.debug("exec handler : {}",functionHandler.getClass().getName());
                body = ((ReqFunctionHandler)functionHandler).exec(request,body);
            }
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }
            @Override
            public boolean isFinished() {
                return false;
            }
            @Override
            public boolean isReady() {
                return false;
            }
            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    private String getBodyContent(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = request.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }


}
