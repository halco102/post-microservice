package com.reddit.post.service.cloudinary;

import org.springframework.web.multipart.MultipartFile;

public interface ICloudinary {

    String getUrlFromUploadedMedia(MultipartFile multipartFile);

}
