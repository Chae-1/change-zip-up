package com.kosa.chanzipup.application.images;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String store(String detailPath, MultipartFile file);

    Resource loadAsResource(String subPath, String fileName);
}
