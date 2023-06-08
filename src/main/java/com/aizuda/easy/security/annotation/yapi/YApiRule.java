package com.aizuda.easy.security.annotation.yapi;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface YApiRule {

    boolean required() default false;

    boolean hide() default false;
}
