package org.web_4th_lab.web_4th_lab.DTO;

public class AuthenticationResponse {
    private long userId;
    private String token;

    public AuthenticationResponse(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
