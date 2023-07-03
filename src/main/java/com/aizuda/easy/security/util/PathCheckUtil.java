package com.aizuda.easy.security.util;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class PathCheckUtil {

    private static final String RULE_0 = "**";

    private static final String RULE_1 = "/";

    public static void pathMatch(List<String> configureUrl, String url, Consumer<Boolean> consumer) {
        boolean b = isMatch(configureUrl,url);
        if(b){
            consumer.accept(b);
        }

    }

    public static Boolean isMatch(List<String> configureUrl, String url){
        return configureUrl.stream()
                .anyMatch(i -> {
                    String ir = i.replace(RULE_0, "");
                    return i.equals(url) || (i.endsWith(RULE_0) && url.startsWith(ir)) || ir.equals(url + RULE_1);
                });
    }

    public static Object pathMatchR(List<String> configureUrl, String url, Function<Boolean,Object> function) {
        boolean b = isMatch(configureUrl,url);
        if(b){
            return function.apply(b);
        }
        return b;
    }


}
