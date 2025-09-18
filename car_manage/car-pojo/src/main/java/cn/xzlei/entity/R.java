package cn.xzlei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R {
    private Integer code;
    private String message;
    private Object data;
    private Integer status;

    public static R success(){
        return new R(200, "success", null, 1);
    }
    public static R success(Object data){
        return new R(200, "success", data, 1);
    }
    public static R success(String message){
        return new R(200, message, null, 1);
    }
    public static R success(Integer code, String message){
        return new R(code, message, null, 1);
    }
    public static R success(Object data,String message){
        return new R(200, message, data, 1);
    }
    public static R success(Integer code, String message, Object data){
        return new R(code, message, data, 1);
    }

    public static R error(){
        return new R(500, "error", null, 0);
    }
    public static R error(String message){
        return new R(500, message, null, 0);
    }
    public static R error(Integer code, String message){
        return new R(code, message, null, 0);
    }
    public static R error(Integer code, String message, Object data){
        return new R(code, message, data, 0);
    }
}
