package com.springapp.mvc.result;

import com.google.gson.Gson;

import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by Max on 2017/3/20.
 */
public class ResultSupport {
    public static Result<String> ok(){return new Result<String>(ResultCode.OK);}

    public static <T> Result<T> ok(T data){
        Result<T> result=new Result<T>(ResultCode.OK);
        result.setData(data);
        return result;
    }

    public static Result<String> ok(ServletResponse resp) throws IOException {
        return outputJson(ok(),resp);
    }

    public static Result<String> serverError(){return new Result<String>(ResultCode.INTERNAL_SERVER_ERROR);}

    public static Result<String> serverError(ServletResponse resp) throws IOException {
        return outputJson(serverError(), resp);
    }

    public static Result<String> forbidden() {
        return new Result<String>(ResultCode.FORBIDDEN);
    }

    public static Result<String> forbidden(ServletResponse resp) throws IOException {
        return outputJson(forbidden(), resp);
    }

    public static final <T> Result<T> outputJson(Result<T> obj,ServletResponse resp) throws IOException {
        resp.getWriter().write(new Gson().toJson(obj));
        return obj;
    }

    public static final Result<String> outputJson(ResultCode resultCode,ServletResponse resp) throws IOException {
        return ResultSupport.outputJson(new Result<String>(resultCode),resp);
    }

}
