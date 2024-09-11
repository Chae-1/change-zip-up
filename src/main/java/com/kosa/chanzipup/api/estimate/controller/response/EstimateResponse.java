package com.kosa.chanzipup.api.estimate.controller.response;

public class EstimateResponse {

    private String content;
    private boolean isWrite;

    private EstimateResponse(String content, boolean isWrite) {
        this.content = content;
        this.isWrite = isWrite;
    }

    public static EstimateResponse write(String content, boolean isWrite) {
        return new EstimateResponse(content, isWrite);
    }

    public static EstimateResponse noWrite(String content, boolean isWrite) {
        return new EstimateResponse(content, isWrite);
    }
}
