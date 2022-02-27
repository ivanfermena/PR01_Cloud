package es.codeurjc.mca.practica_1_cloud_ordinaria_2021.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("storageService")
@Profile("production")
public class S3ImageService implements ImageService {

    @Value("${amazon.s3.bucket-name}")
    private String bucketName;
    public static S3Client s3;

    public S3ImageService() {
        s3 = S3Client.builder()
                .region(Region.EU_WEST_1)
                .build();
    }

    @Override
    public String createImage(MultipartFile multiPartFile) throws IOException {
        String fileName = "image_" + UUID.randomUUID() + "_" +multiPartFile.getOriginalFilename();

        File file = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);

        multiPartFile.transferTo(file);

        try {
            s3.headBucket(
                    HeadBucketRequest.builder()
                            .bucket(bucketName)
                            .build()
            );
        } catch (NoSuchBucketException e) {
            s3.createBucket(
                    CreateBucketRequest.builder()
                            .bucket(bucketName)
                            .build()
            );
        }

        s3.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build(),
                RequestBody.fromFile(file)
        );
        return fileName;
    }

    @Override
    public void deleteImage(String image_url) {

        s3.deleteObject(
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(image_url)
                        .build()
        );
    }

}
