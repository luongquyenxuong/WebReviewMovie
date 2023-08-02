package com.example.mock_project.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class ConditionPost {
    private String author;

    private String title;

    private Integer status;

    private String fromDate;

    private String toDate;
}
