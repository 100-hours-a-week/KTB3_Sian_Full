package com.sian.community_api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${file.upload.dir}")
    private String uploadDir;

    public String save(MultipartFile file) {

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String original = file.getOriginalFilename().replaceAll("[\\s()]", "");

        String ext = "";

        int idx = original.lastIndexOf(".");
        if (idx != -1) {
            ext = original.substring(idx);
        }

        String fileName = UUID.randomUUID().toString() + ext;

        File dest = new File(dir, fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }

        return "/uploads/" + fileName;
    }

}
