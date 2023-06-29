package com.aizuda.easy.security.properties;


import com.aizuda.easy.security.code.FilterOrderCode;
import com.aizuda.easy.security.filter.*;
import com.aizuda.easy.security.handler.DefaultHandlerFactory;
import com.aizuda.easy.security.handler.FunctionHandler;
import com.aizuda.easy.security.server.EasySecurityServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.Set;

@Configuration
@ConditionalOnClass(value = {SecurityProperties.class,EasySecurityServer.class})
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

    private final String  urlPatterns = "/*";

    @Bean
    public DefaultHandlerFactory defaultHandlerFactory(EasySecurityServer easySecurityServer) throws Exception{
        DefaultHandlerFactory defaultHandlerFactory = new DefaultHandlerFactory();
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(FunctionHandler.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(FunctionHandler.class.getPackage().getName());
        for (BeanDefinition component : components) {
            Class cls = Class.forName(component.getBeanClassName());
            FunctionHandler o = (FunctionHandler) cls.getConstructor().newInstance();
            o.setEasySecurityServer(easySecurityServer);
            defaultHandlerFactory.register(o.getIndex(), o);
        }
        return defaultHandlerFactory;
    }


    @Bean
    public FilterRegistrationBean<BeforeFilter> beforeFilter(EasySecurityServer easySecurityServer,SecurityProperties securityProperties) {
        log.info("building {}", FilterOrderCode.FILTER_ORDER_CODE_0.getName());
        FilterRegistrationBean<BeforeFilter> registration = new FilterRegistrationBean<>();
        BeforeFilter beforeFilter = new BeforeFilter(securityProperties,easySecurityServer);
        registration.setFilter(beforeFilter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName("beforeFilter");
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_0.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<FunctionFilter> functionFilter(SecurityProperties securityProperties,DefaultHandlerFactory defaultHandlerFactory) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_500.getName());
        FilterRegistrationBean<FunctionFilter> registration = new FilterRegistrationBean<>();
        FunctionFilter functionFilter = new FunctionFilter(securityProperties,defaultHandlerFactory);
        registration.setFilter(functionFilter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName("functionFilter");
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_500.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AfterFilter> afterFilter(EasySecurityServer easySecurityServer,SecurityProperties securityProperties) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_1000.getName());
        FilterRegistrationBean<AfterFilter> registration = new FilterRegistrationBean<>();
        AfterFilter afterFilter = new AfterFilter(securityProperties,easySecurityServer);
        registration.setFilter(afterFilter);
        registration.addUrlPatterns(urlPatterns);
        registration.setName("afterFilter");
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_1000.getCode());
        return registration;
    }

}
