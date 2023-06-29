package com.aizuda.easy.security.properties;


import com.aizuda.easy.security.code.FilterOrderCode;
import com.aizuda.easy.security.filter.*;
import com.aizuda.easy.security.server.EasySecurityServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(value = {SecurityProperties.class,EasySecurityServer.class})
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityAutoConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SecurityAutoConfiguration.class);

    private final String  urlPatterns = "/*";

    @Bean
    public FilterRegistrationBean<BeforeFilter> beforeFilter(SecurityProperties securityProperties) {
        log.info("building {}", FilterOrderCode.FILTER_ORDER_CODE_0.getName());
        FilterRegistrationBean<BeforeFilter> registration = new FilterRegistrationBean<>();
        BeforeFilter beforeFilter = new BeforeFilter();
        beforeFilter.setSecurityProperties(securityProperties);
        registration.setFilter(beforeFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("beforeFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_0.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<BlacklistFilter> blacklistFilter(SecurityProperties securityProperties) {
        log.info("building {}", FilterOrderCode.FILTER_ORDER_CODE_90.getName());
        FilterRegistrationBean<BlacklistFilter> registration = new FilterRegistrationBean<>();
        BlacklistFilter blacklistFilter = new BlacklistFilter();
        blacklistFilter.setAuthenticationProperties(securityProperties);
        registration.setFilter(blacklistFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("blacklistFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_90.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<SpecialPathFilter> specialPathFilter(SecurityProperties securityProperties) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_100.getName());
        FilterRegistrationBean<SpecialPathFilter> registration = new FilterRegistrationBean<>();
        SpecialPathFilter specialPathFilter = new SpecialPathFilter();
        specialPathFilter.setAuthenticationProperties(securityProperties);
        registration.setFilter(specialPathFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("specialPathFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_100.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<ProjectPathFilter> projectPathFilter(SecurityProperties securityProperties) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_200.getName());
        FilterRegistrationBean<ProjectPathFilter> registration = new FilterRegistrationBean<>();
        ProjectPathFilter projectPathFilter = new ProjectPathFilter();
        projectPathFilter.setAuthenticationProperties(securityProperties);
        securityProperties.getProjectUrl().add(securityProperties.getErrorUrl());
        registration.setFilter(projectPathFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("projectPathFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_200.getCode());
        return registration;
    }

    @Bean
    public FilterRegistrationBean<DecryptPathFilter> decryptPathFilter(SecurityProperties securityProperties) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_210.getName());
        FilterRegistrationBean<DecryptPathFilter> registration = new FilterRegistrationBean<>();
        DecryptPathFilter decryptPathFilter = new DecryptPathFilter();
        decryptPathFilter.setAuthenticationProperties(securityProperties);
        registration.setFilter(decryptPathFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("decryptPathFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_210.getCode());
        return registration;
    }

    @ConditionalOnExpression("${easy.security.auth-enable:false}")
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter(SecurityProperties securityProperties, EasySecurityServer easySecurityServer) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_300.getName());
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationProperties(securityProperties);
        authenticationFilter.setAuthenticationServerSource(easySecurityServer);
        registration.setFilter(authenticationFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("authenticationFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_300.getCode());
        return registration;
    }

    @ConditionalOnExpression("${easy.security.auth-enable:false} && ${easy.security.authorize-enable:false}")
    @Bean
    public FilterRegistrationBean<AuthorizationFilter> authorizationFilter(SecurityProperties securityProperties, EasySecurityServer easySecurityServer) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_400.getName());
        FilterRegistrationBean<AuthorizationFilter> registration = new FilterRegistrationBean<>();
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        authorizationFilter.setAuthenticationProperties(securityProperties);
        authorizationFilter.setAuthorizationServerSource(easySecurityServer);
        registration.setFilter(authorizationFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("authorizationFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_400.getCode());
        return registration;
    }

    @ConditionalOnExpression("${easy.security.request-data-enable:false}")
    @Bean
    public FilterRegistrationBean<RequestDataFilter> requestDataFilter(SecurityProperties securityProperties) {
        log.info("building {}",FilterOrderCode.FILTER_ORDER_CODE_500.getName());
        FilterRegistrationBean<RequestDataFilter> registration = new FilterRegistrationBean<>();
        RequestDataFilter requestDataFilter = new RequestDataFilter();
        requestDataFilter.setAuthenticationProperties(securityProperties);
        registration.setFilter(requestDataFilter);
        //配置过滤路径
        registration.addUrlPatterns(urlPatterns);
        //设置filter名称
        registration.setName("requestDataFilter");
        //请求中过滤器执行的先后顺序，值越小越先执行
        registration.setOrder(FilterOrderCode.FILTER_ORDER_CODE_500.getCode());
        return registration;
    }

}
