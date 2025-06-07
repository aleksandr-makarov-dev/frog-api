package com.github.frog.features.resources.repository;

import com.github.frog.features.resources.exception.S3StorageException;
import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
@RequiredArgsConstructor
public class S3StorageRepositoryImpl implements S3StorageRepository {

    private final MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucket;

    @Value("${spring.minio.presigned-url-expiry-seconds}")
    private Integer presignedUrlExpirySeconds;

    @PostConstruct
    public void postConstruct() {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucket)
                    .build());

            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder()
                        .bucket(bucket)
                        .build());
            }
        } catch (Exception e) {
            throw new S3StorageException(e);
        }
    }

    @Override
    public void putObject(String key, MultipartFile file) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build();

            minioClient.putObject(putObjectArgs);

        } catch (Exception e) {
            throw new S3StorageException(e);
        }
    }

    @Override
    public String getPresignedUrl(String key) {
        try {
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .method(Method.GET)
                    .expiry(presignedUrlExpirySeconds)
                    .build();

            return minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
        } catch (Exception e) {
            throw new S3StorageException(e);
        }
    }
}
