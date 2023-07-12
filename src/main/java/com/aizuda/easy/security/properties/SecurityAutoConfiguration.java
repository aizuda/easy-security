package com.aizuda.easy.security.properties;


import com.aizuda.easy.security.DefaultHandlerFactory;
import com.aizuda.easy.security.HandlerFactory;
import com.aizuda.easy.security.code.FilterOrderCode;
import com.aizuda.easy.security.filter.FunctionFilter;
import com.aizuda.easy.security.handler.AbstractFunctionHandler;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.server.EasySecurityServer;
import com.aizuda.easy.security.server.EasySecurityServerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.Map;

@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
@ConditionalOnClass(value = {SecurityProperties.class,EasySecurityServer.class})
@ComponentScan(value = {"com.aizuda.easy.security"})
public class SecurityAutoConfiguration extends DefaultHandlerFactory implements HandlerFactory, ApplicationContextAware {

    private static final Logger log = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

    private final String  urlPatterns = "/*";

    private ApplicationContext context;

    final SecurityProperties securityProperties;

    final HandlerExceptionResolver handlerExceptionResolver;

    public SecurityAutoConfiguration(SecurityProperties securityProperties,HandlerExceptionResolver handlerExceptionResolver) {
        this.securityProperties = securityProperties;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    @ConditionalOnMissingBean(EasySecurityServer.class)
    @Bean
    public EasySecurityServer easySecurityServer(){
        return new EasySecurityServerImpl();
    }

    @Bean
    public FilterRegistrationBean<FunctionFilter> functionFilter(EasySecurityServer easySecurityServer) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_0.getName());
        init(easySecurityServer);
        FilterRegistrationBean<FunctionFilter> registration = new FilterRegistrationBean<>();
        FunctionFilter functionFilter = new FunctionFilter(securityProperties,this,handlerExceptionResolver);
        registration.setFilter(functionFilter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName("functionFilter");
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_0.getCode());
        return registration;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void init(EasySecurityServer easySecurityServer){
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

}
