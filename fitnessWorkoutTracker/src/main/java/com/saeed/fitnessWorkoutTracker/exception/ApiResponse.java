package com.saeed.fitnessWorkoutTracker.exception;

public class ApiResponse<T> {
    private String response;
    private int statusCode;
    private T data;

    public ApiResponse(String response, int statusCode, T data) {
        this.response = response;
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
