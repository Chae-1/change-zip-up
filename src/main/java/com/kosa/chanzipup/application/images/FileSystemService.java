package com.kosa.chanzipup.application.images;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FileSystemService implements ImageService {
    private final Path rootLocation;
    private final String location;
    private final String domainAddress;

    public FileSystemService(@Value("${file.location}") String location,
                             @Value("${domain.address}") String domainAddress) {
        this.rootLocation = Paths.get(location);
        this.location = location;
        this.domainAddress = domainAddress;
        init(rootLocation);
    }

    @Override
    public String store(String detailPathLocation, MultipartFile file) {
        Path detailPath = rootLocation.resolve(detailPathLocation);
        init(detailPath);
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file.");
            }

            Path destinationFile = detailPath.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(detailPath.toAbsolutePath())) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file outside current directory.");
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            String savedFilePath = destinationFile.toString()
                    .replace(location, "")
                    .replace("\\", "/");

            log.info("savedFilePath = {}", savedFilePath);
            return String.format("/images%s", savedFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file.", e);
        }
    }

    @Override
    public Resource loadAsResource(String subPath, String fileName) {
        try {
            Path file = rootLocation.resolve(subPath).resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new RuntimeException(
                    "Could not read file: " + fileName);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + fileName, e);
        }
    }

    @Override
    public void deleteAllImages(List<String> deleteImageUrls) {
        List<String> imageSaveUrls = deleteImageUrls.stream()
                .map(url -> url.replace(domainAddress, location))
                .toList();


    }

    private void init(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
