package com.saeed.fitnessWorkoutTracker.exception;

public class ApiResponse<T> {
    private String response;
    private T data;

    public ApiResponse(String response, T data) {
        this.response = response;
        this.data = data;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

