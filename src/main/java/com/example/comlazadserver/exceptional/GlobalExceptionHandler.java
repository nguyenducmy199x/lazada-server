package com.example.comlazadserver.exceptional;

import com.example.comlazadserver.dto.BaseResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler  {

    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<String> handleIllegalArgumentException(final IllegalArgumentException ex){
        return BaseResponse.fail(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> handleException(final Exception ex){
        return BaseResponse.fail(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> handleMethodArgumentNotValidException(final MethodArgumentNotValidException  ex){
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        return BaseResponse.fail(fieldErrors.stream().findFirst().get().getDefaultMessage());
    }

    @ExceptionHandler(BussinessException.class)
    public BaseResponse<String> handleBussinessException(final BussinessException  ex){
        return BaseResponse.fail(ex.getMessage());
    }
}
