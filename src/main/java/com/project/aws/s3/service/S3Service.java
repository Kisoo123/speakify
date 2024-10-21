package com.project.aws.s3.service;

import com.project.security.model.dto.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.util.Objects;

import static com.project.alarm.service.AlarmService.getCurrentUser;
@Service
public class S3Service {
    private final S3Client s3Client;
    @Value("${cloud.aws.credentials.accessKey}")
    String accessKey;
    @Value("${cloud.aws.credentials.secretKey}")
    String secretKey;
    @Value("${cloud.aws.region.static}")
    String region;
    @Value("${cloud.aws.s3.bucket}")
    String bucket;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file,String path,int serializer) {
        User user = getCurrentUser();
        String fileName = path + serializer + file.getOriginalFilename();
        String oldFileName = path + user.getProfilePictureUrl();
        if(Objects.equals(file.getOriginalFilename(), "default-profile-img-white.png")){
            return file.getOriginalFilename();
        }
        try {
            HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                    .bucket(bucket) // S3 버킷 이름
                    .key(oldFileName)         // 조회할 파일 경로
                    .build();

            // 파일이 존재하는지 확인 (존재하지 않으면 NoSuchKeyException 발생)
            HeadObjectResponse headObjectResponse = s3Client.headObject(headObjectRequest);

            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(oldFileName)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        } catch (NoSuchKeyException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            // 파일을 S3에 업로드
            s3Client.putObject(putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
