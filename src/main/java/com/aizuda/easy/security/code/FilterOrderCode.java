package com.aizuda.easy.security.code;



public enum FilterOrderCode {

    /**
     * 过滤去顺序
     */
    FILTER_ORDER_CODE_90(90, "黑名单拦截"),
    FILTER_ORDER_CODE_100(100, "特殊路径拦截"),
    FILTER_ORDER_CODE_200(200, "项目路径拦截"),
    FILTER_ORDER_CODE_210(210, "解密路径拦截"),
    FILTER_ORDER_CODE_300(300, "凭证路径拦截"),
    FILTER_ORDER_CODE_310(310, "凭证续期拦截"),
    FILTER_ORDER_CODE_400(400, "权限路径拦截"),
    FILTER_ORDER_CODE_500(500, "数据封装拦截"),
    ;



    private Integer code;

    private String name;

    FilterOrderCode(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    @Override
    public String toString() {
        return "{\"code\": "+getCode()+", \"msg\": \""+getName()+"\", \"data\": null }";
    }
}
