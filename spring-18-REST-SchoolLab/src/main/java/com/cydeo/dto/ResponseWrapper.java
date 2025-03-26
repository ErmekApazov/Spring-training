package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper<T> {

    private boolean success;
    private String message;
    private Integer code;
    private Object data; // will return whatever object you want to return

    public ResponseWrapper(String message, Object data) {
        this.success = true;
        this.message = message;
        this.code = HttpStatus.OK.value();
        this.data = data;
    }

    public ResponseWrapper(String message) {
        this.success = true;
        this.message = message;
        this.code = HttpStatus.OK.value();
    }

    public ResponseWrapper(String message, int code, boolean success, Object data) {
        this.message = message;
        this.code = code;
        this.success = success;
        this.data = data;
    }

    


}
