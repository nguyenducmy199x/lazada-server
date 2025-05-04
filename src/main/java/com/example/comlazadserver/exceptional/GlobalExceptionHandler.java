package com.example.comlazadserver.exceptional;

import com.example.comlazadserver.dto.BaseResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<String> handleException(final Exception ex){
        return BaseResponse.fail(ex.getMessage());
    }
}
