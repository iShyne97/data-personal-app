package com.personalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseWrapper <T> {
    private boolean success;
    private String message;
    private T data;
    private List<String> errors;
    private int errorCode;
}
