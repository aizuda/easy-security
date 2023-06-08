package com.aizuda.easy.security.util;

import cn.hutool.core.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {

    private static final String IP = "127.0.0.1";

    private static final String UNKNOWN = "unknown";

    /**
     * 如果使用nginx需要配置X-Real-IP，配置方式如下：
     * location / { proxy_set_header X-Real-IP $remote_addr; }
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)|| IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)|| IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)|| IP.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (StrUtil.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)|| IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StrUtil.isBlank(ip) || IP.equals(ip)|| ip.indexOf(":") > -1) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = null;
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
