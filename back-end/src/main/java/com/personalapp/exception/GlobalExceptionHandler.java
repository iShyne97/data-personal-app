package com.personalapp.exception;

import com.personalapp.dto.ResponseWrapper;
import com.personalapp.util.ResponseUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseWrapper<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
        return ResponseUtil.error(Collections.singletonList(ex.getMessage()), "An unexpected error occurred", 500, request.getRequestURI());
    }

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseWrapper<Object> handleDataNotFoundException(DataNotFoundException ex, HttpServletRequest request){
        return ResponseUtil.error(Collections.singletonList(ex.getMessage()), "Data tidak ditemukan!", HttpStatus.NOT_FOUND.value(), request.getRequestURI());
    }

    @ExceptionHandler(NoDataFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseWrapper<Object> handleNoDataFoundException(NoDataFoundException ex, HttpServletRequest request){
        return ResponseUtil.error(Collections.singletonList(ex.getMessage()), "Tidak ada data yang ditemukan!", HttpStatus.CONFLICT.value(), request.getRequestURI());
    }

    @ExceptionHandler(NikAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseWrapper<Object> handleDataAlreadyExistException(NikAlreadyExistException ex, HttpServletRequest request){
        return ResponseUtil.error(Collections.singletonList(ex.getMessage()), "Data sudah terdaftar!", HttpStatus.CONFLICT.value(), request.getRequestURI());
    }
}
