package com.reddit.post.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICloudinary {

    String getUrlFromUploadedMedia(MultipartFile multipartFile);

    void deleteMedia(List<String> imageUrls) throws Exception;


}
