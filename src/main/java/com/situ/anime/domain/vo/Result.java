package com.situ.anime.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liangyunfei
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result {
    private int code;
    private String msg;
    private Object data;

    public static Result success(int code, String msg, Object data){
        return new Result(code, msg, data);
    }

    public static Result success(String msg, Object data){
        return success(200, msg, data);
    }

    public static Result success(Object data){
        return success("这是一条成功信息", data);
    }

    public static Result error(int code, String msg, Object data){
        return new Result(code, msg, data);
    }

    public static Result error(String msg, Object data){
        return error(500, msg, data);
    }

    public static Result error(String msg){
        return error(msg, null);
    }
}
