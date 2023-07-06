package com.aizuda.easy.security.util;

import cn.hutool.core.util.ObjectUtil;
import com.aizuda.easy.security.domain.LocalEntity;

public class LocalUtil {

    private static ThreadLocal<LocalEntity> local = new ThreadLocal<LocalEntity>();

    public static LocalEntity getLocalEntity(){
        return local.get();
    }

    public static void destroy(){
        local.remove();
    }

    public static void create(){
        local.set(new LocalEntity());
    }

    public static <T> T getUser(){
        if(ObjectUtil.isNull(getLocalEntity())){
            return null;
        }
        return (T) getLocalEntity().getUser();
    }


    public static void main(String[] args) {
        Object user = LocalUtil.<LocalEntity>getUser();
        System.out.println(user);
    }

}
