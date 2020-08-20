package tech.enjaz.mouamle.authlib.service;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import tech.enjaz.mouamle.authlib.annotation.SecuredCall;
import tech.enjaz.mouamle.authlib.annotation.SessionContext;
import tech.enjaz.mouamle.authlib.session.Session;
import tech.enjaz.mouamle.authlib.session.exception.InvalidRoleException;
import tech.enjaz.mouamle.authlib.session.exception.SessionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

public class DefaultSecurityInterceptor implements SecurityInterceptor {

    private final String headerName;
    private final SessionProvider<?> sessionManager;

    public DefaultSecurityInterceptor(String headerName, SessionProvider<?> sessionProvider) {
        this.headerName = headerName;
        this.sessionManager = sessionProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(SecuredCall.class)) {
                String sessionId = request.getHeader(headerName);

                if (sessionId == null) {
                    throw new SessionNotFoundException("Invalid session id", null);
                }

                SecuredCall annotation = method.getAnnotation(SecuredCall.class);

                if (annotation.token().equals(SecuredCall.DEFAULT_STATIC)) {
                    if (!sessionManager.isSessionPresent(sessionId)) {
                        throw new SessionNotFoundException(String.format("Couldn't find session with id %s", sessionId), sessionId);
                    }
                } else {
                    if (!annotation.token().equals(sessionId)) {
                        throw new SessionNotFoundException(String.format("Couldn't find session with id %s", sessionId), sessionId);
                    }
                }

                String role = annotation.role();
                if (!role.equals(SecuredCall.DEFAULT_ROLE) && !sessionManager.checkRole(sessionId, role)) {
                    throw new InvalidRoleException("Invalid role");
                }

                return true;
            }
        }

        return true;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {

        Method method = parameter.getMethod();
        if (method != null) {
            if (method.isAnnotationPresent(SecuredCall.class)) {
                String sessionId = webRequest.getHeader(headerName);

                Optional<Session> oSession = sessionManager.getSession(sessionId);
                if (!oSession.isPresent()) {
                    throw new SessionNotFoundException(String.format("Couldn't find session with id %s", sessionId), sessionId);
                }

                return oSession.get();
            }
        }

        return null;
    }
}
