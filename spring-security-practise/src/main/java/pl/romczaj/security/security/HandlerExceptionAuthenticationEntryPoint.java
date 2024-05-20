package pl.romczaj.security.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
@Slf4j
public class HandlerExceptionAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public static final String JAVAX_SERVLET_ERROR_EXCEPTION = "javax.servlet.error.exception";

    private final HandlerExceptionResolver resolver;

    public HandlerExceptionAuthenticationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, AuthenticationException ex) {
        if (request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION) != null) {
            Throwable throwable = (Throwable) request.getAttribute(JAVAX_SERVLET_ERROR_EXCEPTION);
            resolver.resolveException(request, httpServletResponse, null, (Exception) throwable);
        }
        resolver.resolveException(request, httpServletResponse, null, ex);
    }
}

