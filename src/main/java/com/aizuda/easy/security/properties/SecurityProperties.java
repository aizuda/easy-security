package com.aizuda.easy.security.properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


@ConfigurationProperties(value = "easy.security")
public class SecurityProperties {

    private Boolean authEnable = false;

    private Boolean authorizeEnable = false;

    private Boolean requestDataEnable = false;

    /**
     * request.header(key)
     */
    private String tokenKey = "token";
    /**
     * 项目路径
     */
    private List<String> projectUrl = new ArrayList<>();
    /**
     * 特殊路径
     */
    private List<String> specialUrl = new ArrayList<>();
    /**
     * 解密路径
     */
    private List<String> decryptUrl = new ArrayList<>();
    /**
     * 黑名单
     */
    private List<String> blackList = new ArrayList<>();
    /**
     * 长度16位的字母数字组合
     */
    private String secretKey;

    public boolean getAuthEnable() {
        return authEnable;
    }

    public void setAuthEnable(boolean authEnable) {
        this.authEnable = authEnable;
    }

    public boolean getAuthorizeEnable() {
        return authorizeEnable;
    }

    public void setAuthorizeEnable(Boolean authorizeEnable) {
        this.authorizeEnable = authorizeEnable;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public List<String> getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(List<String> projectUrl) {
        this.projectUrl = projectUrl;
    }

    public List<String> getSpecialUrl() {
        return specialUrl;
    }

    public void setSpecialUrl(List<String> specialUrl) {
        this.specialUrl = specialUrl;
    }

    public List<String> getDecryptUrl() {
        return decryptUrl;
    }

    public void setDecryptUrl(List<String> decryptUrl) {
        this.decryptUrl = decryptUrl;
    }

    public Boolean getRequestDataEnable() {
        return requestDataEnable;
    }

    public void setRequestDataEnable(Boolean requestDataEnable) {
        this.requestDataEnable = requestDataEnable;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }



}
