package com.project.customer.user.service;

import com.project.customer.exception.user.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImgService {
    private final S3Client s3Client;

    @Value("${aws.s3.folder}")
    private String FOLDER_NAME;

    @Value("${aws.s3.bucket}")
    private String BUCKET_NAME;

    public String save(final MultipartFile imgFile) {
        final String originalFilename = imgFile.getOriginalFilename();
        final String s3Key = FOLDER_NAME + originalFilename;

        uploadImageToS3(BUCKET_NAME,s3Key,imgFile);

        return s3Client.utilities()
                .getUrl(GetUrlRequest.builder()
                .bucket(BUCKET_NAME)
                .key(s3Key)
                .build()).toExternalForm();
    }

    private void uploadImageToS3(
            final String bucketName,
            final String key,
            final MultipartFile imageFile
    ) {
        try {
            final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            final RequestBody requestBody = RequestBody.fromInputStream(imageFile.getInputStream(), imageFile.getSize());
            s3Client.putObject(putObjectRequest, requestBody);
        } catch (IOException e) {
            throw new UserException(e);
        }
    }
}
