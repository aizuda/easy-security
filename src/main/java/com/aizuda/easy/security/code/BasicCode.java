package com.aizuda.easy.security.code;

import cn.hutool.http.HttpStatus;
import com.aizuda.easy.security.exp.IErrorCode;


public enum BasicCode implements IErrorCode {

    /**
     * 校验码
     */
    BASIC_CODE_401(HttpStatus.HTTP_UNAUTHORIZED, "未登录"),
    BASIC_CODE_402(HttpStatus.HTTP_PAYMENT_REQUIRED, "数据未找到"),
    BASIC_CODE_403(HttpStatus.HTTP_FORBIDDEN,"无操作权限"),
    BASIC_CODE_404(HttpStatus.HTTP_NOT_FOUND,"不存在的接口"),
    BASIC_CODE_409(HttpStatus.HTTP_CONFLICT,"数据已存在"),
    BASIC_CODE_500(HttpStatus.HTTP_INTERNAL_ERROR,"系统内部错误"),

    BASIC_CODE_99984(-99984,"您已被列为黑名单"),
    BASIC_CODE_99985(-99985,"登录会话过期"),
    BASIC_CODE_99986(-99986,"密钥验证错误"),
    BASIC_CODE_99987(-99987,"数据解密失败"),
    BASIC_CODE_99988(-99988,"数据加密失败"),
    BASIC_CODE_99989(-99989,"公钥(IV)不能为空"),
    BASIC_CODE_99990(-99990,"私钥(KEY)不能为空"),
    BASIC_CODE_99991(-99991,"特殊路径解析异常"),
    BASIC_CODE_99992(-99992,"项目路径解析异常"),
    BASIC_CODE_99993(-99993,"data数据解析异常"),
    BASIC_CODE_99994(-99994,"数据解密异常"),
    BASIC_CODE_99995(-99995,"验证码不正确或已失效，请刷新验证码"),
    BASIC_CODE_99996(-99996,"参数校验失败"),
    BASIC_CODE_99997(-99997,"不支持的请求方式"),
    BASIC_CODE_99998(-99998,"参数列表不匹配，或传参错误"),
    BASIC_CODE_99999(-99999,"参数不能为空"),
    ;



    private Integer code;

    private String msg;

    BasicCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }


    @Override
    public String toString() {
        return "{\"code\": "+getCode()+", \"msg\": \""+getMsg()+"\", \"data\": null }";
    }
}
