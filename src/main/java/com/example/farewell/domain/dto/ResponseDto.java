package com.example.farewell.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor(staticName = "set")
@NoArgsConstructor
@Builder
public class ResponseDto<D> {

    private boolean success;
    private int statusCode;
    private String message;
    private D data;

    public static <D> ResponseDto<D> success(String message, D data) {
        return ResponseDto.set(true, HttpStatus.OK.value(), message, data);
    }

    public static <D> ResponseDto<D> success(HttpStatus httpStatus, String message, D data) {
        return ResponseDto.set(true, httpStatus.value(), message, data);
    }

    public static <D> ResponseDto<D> failed(String message, D data) {
        return ResponseDto.set(false, HttpStatus.BAD_GATEWAY.value(), message, data);
    }

    public static <D> ResponseDto<D> failed(HttpStatus httpStatus, String message, D data) {
        return ResponseDto.set(false, httpStatus.value(), message, data);
    }
}

