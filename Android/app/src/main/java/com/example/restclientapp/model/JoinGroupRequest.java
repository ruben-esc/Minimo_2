package com.example.restclientapp.model;

public class JoinGroupRequest {
    private String email;
    private String groupId;

    public JoinGroupRequest(String email, String groupId) {
        this.email = email;
        this.groupId = groupId;
    }
}
