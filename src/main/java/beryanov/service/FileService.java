package beryanov.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String uploadFile(MultipartFile multipartFile);
    byte[] downloadFile(String fileName);
}
