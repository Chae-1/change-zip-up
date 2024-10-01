package com.kosa.chanzipup.config.cofing;

import com.kosa.chanzipup.application.images.FileSystemService;
import com.kosa.chanzipup.application.images.ImageService;
import com.kosa.chanzipup.application.images.S3ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class ImageUploadConfig {

    @Bean
    public ImageService imageService(S3Client s3Client,
                                     @Value("${aws.s3.name}") String bucketName,
                                     @Value("${domain.address}") String domainAddress,
                                     @Value("${file.location}") String location) {
        return new FileSystemService(location, domainAddress);
//        return new S3ImageService(s3Client, bucketName, domainAddress);
    }
}
