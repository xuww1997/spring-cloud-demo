package com.xuww.demo.advice;

import com.alibaba.fastjson.JSON;
import com.xuww.demo.annotation.Resp;
import com.xuww.demo.domain.vo.ResultVo;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class LicenseResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        Resp annotation = methodParameter.getMethod().getAnnotation(Resp.class);
        return annotation != null;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ResultVo resultVo = ResultVo.ok(o);
        if (o instanceof String){
            return JSON.toJSONString(resultVo);
        }
        return resultVo;
    }
}
