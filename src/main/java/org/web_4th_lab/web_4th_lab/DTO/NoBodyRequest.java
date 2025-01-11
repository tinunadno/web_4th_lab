package org.web_4th_lab.web_4th_lab.DTO;

import jakarta.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;

public class NoBodyRequest {
    @NotNull(message = "user id is required")
    private long userId;
    @NotNull(message = "token is required")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "token is invalid"
    )
    private String token;

    public NoBodyRequest(long userId, String token) {
        this.userId = userId;
        this.token = token;
    }
    public NoBodyRequest() {}

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
