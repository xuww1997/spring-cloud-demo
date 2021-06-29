package com.xuww.demo.domain.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo {
    private String code;

    private Object data;

    private String msg;

    public static ResultVo ok(Object data){
        return ResultVo.builder().code("200").msg("成功").data(data).build();
    }

    public static ResultVo error(Object data){
        return ResultVo.builder().code("400").msg("失败").data(data).build();
    }
}
