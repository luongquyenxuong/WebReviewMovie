package com.example.mock_project.constant;

public enum NotificationType {
    POST(1L),COMMENT(2L);
    private final Long typeId;
    NotificationType(Long typeId){
        this.typeId=typeId;
    }
    public Long getId(){
        return typeId;
    }
}
