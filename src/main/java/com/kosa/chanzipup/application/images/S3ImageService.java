package com.kosa.chanzipup.application.images;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@Slf4j
public class S3ImageService implements ImageService {

    private final S3Client s3Client;
    private final String bucketName;
    private final String domainAddress;

    public S3ImageService(S3Client s3Client,
                          String bucketName,
                          String domainAddress) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.domainAddress = domainAddress;

    }

    @Override
    public String store(String detailPath, MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            String fileUri = String.format("%s/%s", detailPath, fileName);

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileUri)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return String.format("/images/%s",fileUri);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public Resource loadAsResource(String subPath, String fileName) {
        try {
            String fileUri = String.format("%s/%s", subPath, fileName);

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileUri)
                    .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);

            byte[] data = objectBytes.asByteArray();

            return new ByteArrayResource(data);
        } catch (Exception e) {
            log.error("업로드 실패");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllImages(List<String> deleteImageUrls) {
        List<String> imageSaveUrls = deleteImageUrls.stream()
                .map(this::doResourceMatching)
                .toList();

        for (String imageSaveUrl : imageSaveUrls) {
            log.info("{} ", imageSaveUrl);
            deleteImage(imageSaveUrl);
        }
    }

    public void deleteImage(String imageUri) {
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(imageUri)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String doResourceMatching(String url) {
        return url.replace(domainAddress, "")
                .replace("/images/", "");
    }
}
