package com.xuww.demo.annotation.aspect;

import com.xuww.demo.domain.vo.ResultVo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

//@Aspect
//@Component
public class RespAspect {
    @Pointcut("@annotation(com.xuww.demo.annotation.Resp)")
    public void resp() {
    }

//    @AfterReturning(returning = "response", pointcut = "resp()")
//    public void doAfterReturn(HttpServletResponse response) {
//        int status = response.getStatus();
//
//    }
//
//    @Around("resp()")
//    public Map unifyResponse(ProceedingJoinPoint pjp) throws Throwable {
//        Object controllerResult = pjp.proceed();
//        Map map = new HashMap();
//        map.put("123","!23");
//        return map;
//    }

    @Around("resp()")
    public Object unifyResponse(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        ResultVo resultVo = (ResultVo)args[0];
        args[0] = resultVo.getData();
        Object result = pjp.proceed(args);
        return result;
//        Map map = new HashMap();
//        map.put("123", "!23");
//        return map;
    }


}
