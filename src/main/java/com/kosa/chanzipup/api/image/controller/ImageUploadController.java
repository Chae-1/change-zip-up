package com.kosa.chanzipup.api.image.controller;

import com.kosa.chanzipup.api.image.controller.response.ImageUploadResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/uploads")
public class ImageUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping
    public ResponseEntity<ImageUploadResponse> uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        // 경로 확인 및 파일 저장
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 디렉토리 생성
        }

        // 이미지 저장 경로 생성
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        Path imagePath = Paths.get(uploadDir, fileName);

        // 파일 저장
        Files.write(imagePath, image.getBytes());

        // 이미지 URL 생성 및 반환
        String imageUrl = "/images/" + fileName;
        ImageUploadResponse response = new ImageUploadResponse(imageUrl, image.getOriginalFilename());

        return ResponseEntity.ok(response);
    }
}
