package com.newland.srb.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class R {
    private Integer code;
    private String message;
    private Object data;

    private R() {
    }

    public static R ok() {
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        return r;
    }

    public static R ok(Object result) {
        R r = new R();
        r.setCode(ResponseEnum.SUCCESS.getCode());
        r.setMessage(ResponseEnum.SUCCESS.getMessage());
        r.setData(result);
        return r;
    }

    public static R error() {
        R r = new R();
        r.setCode(ResponseEnum.ERROR.getCode());
        r.setMessage(ResponseEnum.ERROR.getMessage());
        return r;
    }

    public static R result(ResponseEnum responseEnum) {
        R r = new R();
        r.setCode(responseEnum.getCode());
        r.setMessage(responseEnum.getMessage());
        return r;
    }
    /**
     * 设置特定的响应消息
     * @param message
     * @return
     */
    public R message(String message){
        this.setMessage(message);
        return this;
    }


    /**
     * 设置特定的响应码
     * @param code
     * @return
     */
    public R code(Integer code){
        this.setCode(code);
        return this;
    }
    public R data(Object data){
        this.setData(data);
        return this;
    }
    public R data(String key,Object data){
        Map<String,Object> map=new HashMap<>();
        map.put(key,data);
        this.setData(map);
        return this;
    }
}
