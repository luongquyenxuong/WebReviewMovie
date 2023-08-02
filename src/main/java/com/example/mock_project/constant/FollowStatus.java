package com.example.mock_project.constant;

public enum FollowStatus {
    USER("01"),HAVE_FOLLOW("02"),NOT_FOLLOW("03"),USER_NOT_LOGIN("04");

    private final String followStatus;
    FollowStatus(String followStatus){
        this.followStatus=followStatus;
    }

    public String getStatus(){
        return followStatus;
    }
}
