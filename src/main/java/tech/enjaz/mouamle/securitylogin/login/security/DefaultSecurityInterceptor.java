package tech.enjaz.mouamle.securitylogin.login.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.ModelAndViewContainer;
import tech.enjaz.mouamle.securitylogin.login.security.annotation.SecuredCall;
import tech.enjaz.mouamle.securitylogin.login.security.annotation.SessionContext;
import tech.enjaz.mouamle.securitylogin.login.session.DefaultSessionProvider;
import tech.enjaz.mouamle.securitylogin.login.session.Session;
import tech.enjaz.mouamle.securitylogin.login.session.SessionProvider;
import tech.enjaz.mouamle.securitylogin.login.session.exception.SessionNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;


@Component
public class DefaultSecurityInterceptor implements SecurityInterceptor {

    private final SessionProvider sessionManager;

    @Autowired
    public DefaultSecurityInterceptor(DefaultSessionProvider defaultSessionProvider) {
        this.sessionManager = defaultSessionProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            Method method = handlerMethod.getMethod();
            if (method.isAnnotationPresent(SecuredCall.class)) {
                String sessionId = request.getHeader("token");
                if (!sessionManager.isSessionPresent(sessionId)) {
                    throw new SessionNotFoundException(String.format("Couldn't find session with id %s", sessionId), sessionId);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SessionContext.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Method method = parameter.getMethod();
        if (method != null) {
            if (method.isAnnotationPresent(SecuredCall.class)) {
                String sessionId = webRequest.getHeader("token");

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
