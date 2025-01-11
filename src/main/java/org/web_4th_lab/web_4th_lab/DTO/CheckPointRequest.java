package org.web_4th_lab.web_4th_lab.DTO;

import jakarta.validation.constraints.NotNull;

import javax.validation.constraints.Pattern;

public class CheckPointRequest {

    @NotNull(message =  "x coordinate is required")
    double x_cord;
    @NotNull(message =  "y coordinate is required")
    double y_cord;
    @NotNull(message =  "radius is required")
    double radius;
    @NotNull(message =  "user id is required")
    long id;
    @NotNull(message = "token is required")
    @Pattern(
            regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
            message = "token is invalid"
    )
    String token;

    public CheckPointRequest(float x_cord, float y_cord, float radius, long id, String token) {
        this.x_cord = x_cord;
        this.y_cord = y_cord;
        this.radius = radius;
        this.id = id;
        this.token = token;
    }

    public CheckPointRequest() {}

    public double getX_cord() {
        return x_cord;
    }

    public void setX_cord(double x_cord) {
        this.x_cord = x_cord;
    }

    public double getY_cord() {
        return y_cord;
    }

    public void setY_cord(double y_cord) {
        this.y_cord = y_cord;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
