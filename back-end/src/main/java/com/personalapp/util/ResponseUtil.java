package com.personalapp.util;

import com.personalapp.dto.ResponseWrapper;

import java.util.List;

public class ResponseUtil {
    public static <T> ResponseWrapper<T> success(T data, String message, String path) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> ResponseWrapper<T> error(List<String> errors, String message, int errorCode, String path) {
        ResponseWrapper<T> response = new ResponseWrapper<>();
        response.setSuccess(false);
        response.setMessage(message);
        response.setData(null);
        response.setErrors(errors);
        response.setErrorCode(errorCode);
        return response;
    }
}
