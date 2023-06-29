package com.aizuda.easy.security.code;



public enum FilterOrderCode {

    /**
     * 过滤去顺序
     */
    FILTER_ORDER_CODE_0(0, "前置过滤器"),
    FILTER_ORDER_CODE_500(500, "业务过滤器"),
    FILTER_ORDER_CODE_1000(1000, "后置过滤器"),
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
