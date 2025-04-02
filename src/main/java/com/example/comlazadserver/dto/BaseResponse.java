package com.example.comlazadserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private String code;
    private String status;
    private T data;

    public BaseResponse(int code, String success, T data) {
        this.code = String.valueOf(code);
        this.status = success;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(200, "Success", data);
    }
    public static <T> BaseResponse<T> fail(T data) {
        return new BaseResponse<>(400, "Error", data);
    }
}