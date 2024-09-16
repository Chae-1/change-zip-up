package com.kosa.chanzipup.application.images;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/images/{subPath}/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable("subPath") String subPath, @PathVariable("fileName") String fileName) {
        log.info("subPath = {}, filName = {}", subPath, fileName);

        return ResponseEntity.ok(imageService.loadAsResource(subPath, fileName));
    }
}
