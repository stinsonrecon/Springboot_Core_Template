package vn.com.core.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import vn.com.core.common.config.CoreConstants;
import vn.com.core.common.config.EnableWrapResponse;
import vn.com.core.common.config.IgnoreWrapResponse;
import vn.com.core.common.dto.MessagesResponse;
import vn.com.core.common.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestControllerAdvice(basePackages = {"vn.com.hust"})
public class ResponseBodyHandler extends HandlerInterceptorAdapter implements ResponseBodyAdvice<Object> {
    @Autowired
    Environment env;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (response.getStatus() != 200) {
            long startTime = (long) request.getAttribute("startTime");
            long elapsedTimeMs = System.currentTimeMillis() - startTime;
            request.setAttribute("elapsedTimeMs", elapsedTimeMs);
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest servletServerRequest = (ServletServerHttpRequest) serverHttpRequest;
        Long startTime = (Long) servletServerRequest.getServletRequest().getAttribute("startTime");
        Long elapsedTimeMs = -1L;
        if (startTime != null) {
            elapsedTimeMs = System.currentTimeMillis() - startTime;
        }
        servletServerRequest.getServletRequest().setAttribute("elapsedTimeMs", elapsedTimeMs);
//        log.info("END " + servletServerRequest.getServletRequest().getRequestURI()
//                + " in " + elapsedTimeMs + " ms");

        String wrapResponseEnabled = env.getProperty("wrap.response.enabled");

        //Method co khai bao IgnoreWrapFisResponse
        if (!(o instanceof MessagesResponse) && (methodParameter.getMethodAnnotation(IgnoreWrapResponse.class) != null ||
                //Project config wrap.response.enabled=false And Class khong khai bao EnableWrapFisResponse
                ("false".equalsIgnoreCase(wrapResponseEnabled)
                        && methodParameter.getDeclaringClass().getAnnotation(EnableWrapResponse.class) == null))) {
            return o;
        }
        MessagesResponse msg = new MessagesResponse().build(o);
        msg.setElapsedTimeMs(elapsedTimeMs);
        msg.setPath(servletServerRequest.getServletRequest().getRequestURI());
        String requestPair = servletServerRequest.getServletRequest().getHeader(CoreConstants.HEADER_KEY.REQUEST_PAIR);
        if (StringUtil.stringIsNullOrEmty(requestPair)) {
            requestPair = StringUtil.nvl(servletServerRequest.getServletRequest().getAttribute(CoreConstants.REQUEST_ATTR.REQUEST_ID), "");
        }
        msg.setRequestId(requestPair);
        return msg;
    }
}
