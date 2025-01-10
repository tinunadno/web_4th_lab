package org.web_4th_lab.web_4th_lab.DTO;

import jakarta.validation.constraints.NotBlank;

public class AuthenticationRequest {
    @NotBlank(message =  "user name coordinate is required")
    private String username;
    @NotBlank(message =  "password coordinate is required")
    private String password;

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
