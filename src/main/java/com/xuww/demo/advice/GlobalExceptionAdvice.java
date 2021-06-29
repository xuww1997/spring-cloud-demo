package com.xuww.demo.advice;

import com.xuww.demo.domain.vo.ResultVo;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

//@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public ResultVo defaultErrorHandler(HttpServletResponse response, Exception e) {
        System.out.println(e.getMessage());
        return ResultVo.error(e.getMessage());
    }
}
