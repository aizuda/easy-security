package com.aizuda.easy.security.server.wrapper;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.aizuda.easy.security.code.BasicCode;
import com.aizuda.easy.security.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aizuda.easy.security.exp.impl.BasicException;
import com.aizuda.easy.security.server.encryption.CiphertextServer;
import com.aizuda.easy.security.server.encryption.impl.AesEncryptServer;
import com.aizuda.easy.security.domain.Rep;
import com.aizuda.easy.security.util.LocalUtil;
import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ResponseDataWrapper extends HttpServletResponseWrapper {

    private static final Logger log = LoggerFactory.getLogger(ResponseDataWrapper.class);

    private ByteArrayOutputStream buffer = null;
    private ServletOutputStream out = null;
    private PrintWriter writer = null;
    private HttpServletResponse response;
    private CiphertextServer ciphertextServer = new AesEncryptServer();
    private ObjectMapper mapper = new ObjectMapper();
    private SecurityProperties securityProperties;
    public ResponseDataWrapper(HttpServletResponse response,SecurityProperties securityProperties) throws IOException {
        super(response);
        this.response = response;
        this.securityProperties = securityProperties;
        //真正存储数据的流
        buffer = new ByteArrayOutputStream();
        out = new WapperedOutputStream(buffer);
        writer = new PrintWriter(new OutputStreamWriter(buffer, Charsets.UTF_8));
    }

    @Override
    public ServletOutputStream getOutputStream()throws IOException {
        return out;
    }

    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {
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

    public void changeContent() throws IOException{
        flushBuffer();
        PrintWriter out = null;
        String data = null;
        try {
            data = buffer.toString(Charsets.UTF_8);
            out = response.getWriter();
            String iv = ciphertextServer.getIV();
            response.setHeader(CiphertextServer.IV,iv);
            response.setCharacterEncoding(Charsets.UTF_8.toString());
            if(StrUtil.isEmpty(data)){
                return;
            }
            Rep<Object> rep = mapper.readValue(data.trim(), Rep.class);
            LocalUtil.LocalEntity localEntity = LocalUtil.getLocalEntity();
            if(!ObjectUtil.isEmpty(rep.getData()) && localEntity.getDecrypt()){
                String obj = ciphertextServer.encryption(
                        mapper.writeValueAsString(rep.getData()),
                        securityProperties.getSecretKey(),
                        iv
                ).trim();
                if(StrUtil.isEmpty(obj)){
                    throw new BasicException(BasicCode.BASIC_CODE_99988);
                }
                rep.setData(obj);
            }
            data = mapper.writeValueAsString(rep);
        }catch (Exception e){
            e.getMessage();
            log.error(e.getMessage());
        }finally {
            if(out != null){
                out.write(data);
                out.flush();
                out.close();
            }
        }
    }

    private Object encrypt(Rep<Object> rep, String key, String iv){
        Object obj = rep.getData();
        if(obj.getClass().equals(String.class)){
            obj = toString(obj,key,iv);
        }
        if(obj.getClass().equals(LinkedHashMap.class)){
            obj = toLinkedHashMap(obj,key,iv);
        }
        if(obj.getClass().equals(ArrayList.class)){
            obj = toArray(obj,key,iv);
        }
        return obj;
    }

    private Object toString(Object obj,String key,String iv){
        if(ObjectUtil.isEmpty(obj)){
            return obj;
        }
        return ciphertextServer.encryption(obj.toString(),key,iv);
    }

    private Object toArray(Object obj,String key,String iv){
        return obj;
    }

    private Object toLinkedHashMap(Object obj,String key,String iv){
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap) obj;
        linkedHashMap.keySet().stream().forEach(i -> {
            Object ov = linkedHashMap.get(i);
            if(!ObjectUtil.isEmpty(ov)) {
                String value = String.valueOf(ov).trim();
                linkedHashMap.put(i, ciphertextServer.encryption(value,key,iv).trim());
            }
        });
        return linkedHashMap;
    }


    private class WapperedOutputStream extends ServletOutputStream{

        private ByteArrayOutputStream bos=null;

        public WapperedOutputStream(ByteArrayOutputStream stream) throws IOException{
            bos=stream;
        }
        @Override
        public void write(int b) throws IOException{
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
