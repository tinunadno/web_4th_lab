package org.web_4th_lab.web_4th_lab.DTO;

import jakarta.validation.constraints.NotNull;

public class AuthenticationRequest {
    @NotNull(message =  "user name is required")
    private String username;
    @NotNull(message =  "password is required")
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public AuthenticationRequest() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
