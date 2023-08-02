package com.example.mock_project.exception;

import com.example.mock_project.dto.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class HandlerException extends ResponseEntityExceptionHandler {


    @ExceptionHandler({BaseException.class})
    public ResponseEntity<?> handleBaseEx(BaseException e) {
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .status(e.getStatus())
                        .message(e.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleEx(BaseException e) {
        return ResponseEntity.ok(
                BaseResponse.<Void>builder()
                        .status("99")
                        .message("System error!")
                        .build()
        );
    }
}
