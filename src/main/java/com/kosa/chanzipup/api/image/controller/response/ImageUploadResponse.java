package com.kosa.chanzipup.api.image.controller.response;

import lombok.Getter;

@Getter
public class ImageUploadResponse {

    private String path;
    private String originalFileName;

    public ImageUploadResponse(String path, String originalFileName) {
        this.path = path;
        this.originalFileName = originalFileName;
    }
}
