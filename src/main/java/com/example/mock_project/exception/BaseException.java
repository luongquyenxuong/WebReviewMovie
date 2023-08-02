package com.example.mock_project.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends RuntimeException {
    private String status;

    public BaseException(String status, String message) {
        super(message);
        this.status = status;
    }
}
