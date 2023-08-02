package com.example.mock_project.constant;

public enum ArchiveStatus {
    USER("01"),HAVE_ARCHIVE("02"),NOT_ARCHIVE("03"),USER_NOT_LOGIN("04");

    private final String archiveStatus;
    ArchiveStatus(String archiveStatus){
        this.archiveStatus=archiveStatus;
    }

    public String getStatus(){
        return archiveStatus;
    }
}
