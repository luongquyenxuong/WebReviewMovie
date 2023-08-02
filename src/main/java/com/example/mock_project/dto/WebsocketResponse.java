package com.example.mock_project.dto;

import com.example.mock_project.constant.WebsocketAction;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class WebsocketResponse<T> implements Serializable {
    private WebsocketAction action;
    private T data;

    public static <T> WebsocketResponse<T> build(T data, WebsocketAction action) {
        return WebsocketResponse.<T>builder().data(data).action(action).build();
    }

}
