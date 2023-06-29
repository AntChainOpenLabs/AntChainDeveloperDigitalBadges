package com.antgroup.antchain.xbuilders.service.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import com.antgroup.antchain.xbuilders.service.OSSService;
import com.antgroup.antchain.xbuilders.util.DateUtil;
import com.antgroup.antchain.xbuilders.util.GenerateIdUtil;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
public class OSSServiceImpl implements OSSService {

    @Autowired
    private OSSClient ossClient;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    @Override
    public String putObject(String folder, String filename, InputStream in) {
        return this.putObject(folder, filename, in, null);
    }

    @Override
    public String putObject(String folder, String filename, InputStream in, String originalName) {
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotBlank(folder)) {
                sb.append(folder);
                sb.append("/");
            }
            sb.append(GenerateIdUtil.generateUuid());
            int index = filename.lastIndexOf(".");
            if (index != -1) {
                sb.append(filename.substring(index));
            }
            String path = sb.toString();

            ObjectMetadata metadata = null;
            if (StringUtils.isNotBlank(originalName)) {
                metadata = new ObjectMetadata();
                metadata.addUserMetadata("originalname", originalName);
            }

            ossClient.putObject(bucketName, path, in, metadata);
            return path;
        } catch (Exception e) {
            log.error("failed to putObject by folder={}, filename={}, cause: {}",
                    folder, filename, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public byte[] getObject(String path) {
        try {
            OSSObject object = ossClient.getObject(bucketName, path);
            InputStream stream = object.getObjectContent();
            return StreamUtils.copyToByteArray(stream);
        } catch (Exception e) {
            log.error("failed to getObject by path={}, cause: {}", path, Throwables.getStackTraceAsString(e));
        }
        return null;
    }

    @Override
    public void deleteObject(String path) {
        try {
            ossClient.deleteObject(bucketName, path);
        } catch (Exception e) {
            log.error("failed to deleteObject by path={}, cause: {}", path, Throwables.getStackTraceAsString(e));
        }
    }

    @Override
    public String getUrl(String path, long delay) {
        Date expiration = DateUtil.convertLocalDateTimeToDate(LocalDateTime.now().plusMinutes(delay));
        GeneratePresignedUrlRequest generatePresignedUrlRequest;
        generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, path);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }


    @Override
    public String putAsset(String folder, String filename, InputStream in, boolean isPic) {
        return this.putAsset(folder, filename, in, null, isPic);
    }

    @Override
    public String putAsset(String folder, String filename, InputStream in, String originalName, boolean isPic) {
        try {
            StringBuilder sb = new StringBuilder();
            if (StringUtils.isNotBlank(folder)) {
                sb.append(folder);
                sb.append("/");
            }
            sb.append(filename);
            String path = sb.toString();

            ObjectMetadata metadata = null;
            if (StringUtils.isNotBlank(originalName)) {
                metadata = new ObjectMetadata();
                metadata.addUserMetadata("originalname", originalName);
            }

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, path, in, metadata);
            Map<String, String> headers = new HashMap<>(2);
            headers.put("x-oss-object-acl", "public-read");
            // text/html, text/plain, application/xml, etc.
            headers.put("content-type", isPic ? "images/jpeg" : "application/json");
            putObjectRequest.setHeaders(headers);

            ossClient.putObject(putObjectRequest);
            return path;
        } catch (Exception e) {
            log.error("failed to putAsset by folder={}, filename={}, cause: {}",
                    folder, filename, Throwables.getStackTraceAsString(e));
        }
        return null;
    }
}
