package com.reddit.post.service.cloudinary.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.reddit.post.service.cloudinary.ICloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudinaryService implements ICloudinary {

    private final Cloudinary cloudinary;

    @Override
    public String getUrlFromUploadedMedia(MultipartFile multipartFile) {
        try {
            log.info("Uploading file...");

            Map uploadResult = null;


            // for video I have to make a class mime type and change the db for it (TODO) for later
            if (multipartFile.getContentType().matches("video/mp4")) {
                uploadResult = cloudinary.uploader().uploadLarge(multipartFile.getBytes(), ObjectUtils.emptyMap());
                log.info("Successful video upload!");
            }else {
                uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
                log.info("Successful iamge upload!");
            }
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
