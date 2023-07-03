package com.aizuda.easy.security.properties;


import com.aizuda.easy.security.DefaultHandlerFactory;
import com.aizuda.easy.security.HandlerFactory;
import com.aizuda.easy.security.code.FilterOrderCode;
import com.aizuda.easy.security.filter.FunctionFilter;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.server.EasySecurityServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Configuration
@ConditionalOnClass(value = {SecurityProperties.class,EasySecurityServer.class})
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration extends DefaultHandlerFactory implements HandlerFactory, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

    private final String  urlPatterns = "/*";

    private ApplicationContext context;

    @Resource
    EasySecurityServer easySecurityServer;

    @Resource
    SecurityProperties securityProperties;


    @PostConstruct
    public void init(){
        Map<String, FunctionHandler> beansOfType = context.getBeansOfType(FunctionHandler.class);
        beansOfType.values().forEach(item -> {
            if(item instanceof AbstractFunctionHandler){
                AbstractFunctionHandler abstractFunctionHandler = (AbstractFunctionHandler) item;
                abstractFunctionHandler.setProperties(securityProperties);
                abstractFunctionHandler.setEasySecurityServer(easySecurityServer);
            }
            register(item.getIndex(), item);
        });
    }

    @Bean
    public FilterRegistrationBean<FunctionFilter> functionFilter() {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_500.getName());
        FilterRegistrationBean<FunctionFilter> registration = new FilterRegistrationBean<>();
        FunctionFilter functionFilter = new FunctionFilter(securityProperties);
        registration.setFilter(functionFilter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName("functionFilter");
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_500.getCode());
        return registration;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
