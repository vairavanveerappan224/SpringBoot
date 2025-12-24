package com.example.E_commerce.Resposes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private int code;
    private boolean status;
    private String message;
    private T data;
}
