package com.aizuda.easy.security.util;

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

}
