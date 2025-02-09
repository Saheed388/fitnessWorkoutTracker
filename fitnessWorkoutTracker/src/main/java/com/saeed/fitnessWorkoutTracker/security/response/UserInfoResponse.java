package com.saeed.fitnessWorkoutTracker.security.response;

public class UserInfoResponse {
    private Long id;
    private String jwtToken;
    private String username;
    private String role;  // Now stores only one role

    public UserInfoResponse(Long id, String username, String role, String jwtToken) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.jwtToken = jwtToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {  // Single role instead of a list
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
