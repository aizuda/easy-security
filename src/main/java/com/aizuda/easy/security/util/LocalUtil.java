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
        LocalEntity localEntity = getLocalEntity();
        if(ObjectUtil.isNull(localEntity) || ObjectUtil.isNull(localEntity.getUser())){
            return null;
        }
        return (T) localEntity.getUser();
    }

}
