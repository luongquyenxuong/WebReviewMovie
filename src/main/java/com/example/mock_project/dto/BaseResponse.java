package com.example.mock_project.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class BaseResponse<T> implements Serializable {
    private String status;
    private String message;
    private T data;

    public static BaseResponse<Void> buildEmpty() {
        return BaseResponse.<Void>builder().status("00").build();
    }
}
