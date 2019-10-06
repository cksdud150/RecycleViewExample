package com.example.recycleviewexample.model;

public class DefaultResponse {
    private Result result;

    public DefaultResponse(Result result) {
        this.result = result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}