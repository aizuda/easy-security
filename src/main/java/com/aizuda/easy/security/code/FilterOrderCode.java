package com.aizuda.easy.security.code;



public enum FilterOrderCode {

    /**
     * 过滤去顺序
     */
    FILTER_ORDER_CODE_0(0, "核心过滤器加载"),
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
