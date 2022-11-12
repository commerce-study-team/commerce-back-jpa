package com.ex.commercetestbackjpa.config.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
@Slf4j
public class FileUploadUtil {

    public static String uploadFile(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String imageName = file.getOriginalFilename();
        String imageFileName = uuid + file.getOriginalFilename();
        String path = "C:/projects/upload/image/";

        try {
            new File(path).mkdirs();
            file.transferTo(new File(path + imageFileName));
        } catch (Exception e) {
            log.error("fileUploadException : {}", e.getMessage());
        }
        return imageFileName;
    }
}
