package com.ex.commercetestbackjpa.config.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Slf4j
public class FileUtil {

    static final String path = "C:/projects/upload/image/";

    public static String uploadFile(MultipartFile file) {
        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + file.getOriginalFilename();

        try {
            new File(path).mkdirs();
            file.transferTo(new File(path + imageFileName));
        } catch (Exception e) {
            log.error("fileUploadException : {}", e.getMessage());
        }
        return imageFileName;
    }

    public static Boolean deleteFile(String fileName) {

        File deleteFile = new File(path + fileName);

        // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
        if(deleteFile.exists()) {

            // 파일을 삭제합니다.
            deleteFile.delete();

            return true;

        } else {
            return false;
        }
    }
}
