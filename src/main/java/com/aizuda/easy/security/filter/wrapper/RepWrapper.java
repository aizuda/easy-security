package com.aizuda.easy.security.filter.wrapper;

import cn.hutool.core.util.ObjectUtil;
import com.aizuda.easy.security.AbstractHandlerFactory;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.handler.RepFunctionHandler;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class RepWrapper extends HttpServletResponseWrapper {

    private static final Logger log = LoggerFactory.getLogger(RepWrapper.class);
    private ByteArrayOutputStream buffer;
    private ServletOutputStream out;
    private PrintWriter writer;
    private HttpServletResponse response;
    private String body;
    private AbstractHandlerFactory abstractHandlerFactory;
    public RepWrapper(HttpServletResponse response){
        super(response);
        this.response = response;
        //真正存储数据的流
        buffer = new ByteArrayOutputStream();
        out = new WapperedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, Charsets.UTF_8));
    }

    @Override
    public ServletOutputStream getOutputStream(){
        return out;
    }

    @Override
    public PrintWriter getWriter(){
        return writer;
    }

    @Override
    public void flushBuffer()throws IOException{
        if(out!=null){
            out.flush();
        }
        if(writer!=null){
            writer.flush();
        }
    }

    @Override
    public void reset(){
        buffer.reset();
    }

    public void changeContent() throws IOException, BasicException {
        PrintWriter printWriter = null;
        try {
            flushBuffer();
            body = buffer.toString();
            printWriter = response.getWriter();
            for (FunctionHandler functionHandler : abstractHandlerFactory.getFunctionHandlers()) {
                log.debug("exec handler : {}", functionHandler.getClass().getName());
                if(functionHandler instanceof RepFunctionHandler) {
                    body = ((RepFunctionHandler)functionHandler).exec(response,body);
                }
            }
        }finally {
            if(!ObjectUtil.isNull(printWriter)){
                printWriter.write(body);
                printWriter.flush();
                printWriter.close();
            }
        }
    }

    private class WapperedOutputStream extends ServletOutputStream{
        private ByteArrayOutputStream bos;
        public WapperedOutputStream(ByteArrayOutputStream stream){
            bos=stream;
        }
        @Override
        public void write(int b){
            bos.write(b);
        }
        @Override
        public boolean isReady() {
            return false;
        }
        @Override
        public void setWriteListener(WriteListener writeListener) {
        }
    }


}
