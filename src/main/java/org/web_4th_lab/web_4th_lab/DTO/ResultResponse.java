package org.web_4th_lab.web_4th_lab.DTO;

import org.web_4th_lab.web_4th_lab.entities.Result;
import org.web_4th_lab.web_4th_lab.entities.User;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ResultResponse {

    private double xCord;
    private double yCord;
    private double radius;
    private boolean result;
    String requestTime;
    private long executionTime;

    public ResultResponse(double xCord, double yCord, double radius, boolean result, String requestTime, long executionTime) {
        this.xCord = xCord;
        this.yCord = yCord;
        this.radius = radius;
        this.result = result;
        this.requestTime = requestTime;
        this.executionTime = executionTime;
    }

    public ResultResponse(Result result) {
        this.xCord = result.getxCord();
        this.yCord = result.getyCord();
        this.radius = result.getRadius();
        this.result = result.getResult();
        this.requestTime = result.getRequestTime();
        this.executionTime = result.getExecutionTime();
    }

    public double getxCord() {
        return xCord;
    }

    public void setxCord(double xCord) {
        this.xCord = xCord;
    }

    public double getyCord() {
        return yCord;
    }

    public void setyCord(double yCord) {
        this.yCord = yCord;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(long executionTime) {
        this.executionTime = executionTime;
    }
}
