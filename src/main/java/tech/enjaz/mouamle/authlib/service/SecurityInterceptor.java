package tech.enjaz.mouamle.authlib.service;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;

public interface SecurityInterceptor extends HandlerInterceptor, HandlerMethodArgumentResolver { }
