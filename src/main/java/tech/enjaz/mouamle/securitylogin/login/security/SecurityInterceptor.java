package tech.enjaz.mouamle.securitylogin.login.security;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;

public interface SecurityInterceptor extends HandlerInterceptor, HandlerMethodArgumentResolver { }
