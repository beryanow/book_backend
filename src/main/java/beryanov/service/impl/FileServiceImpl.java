package beryanov.service.impl;

import beryanov.exception.file.EmptyFileException;
import beryanov.exception.file.FailedCreationException;
import beryanov.exception.file.UnavailableFileException;
import beryanov.service.FileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@Service
@Slf4j
public class FileServiceImpl implements FileService {
    @SneakyThrows
    @Override
    public String uploadFile(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String name = multipartFile.getOriginalFilename();
            byte[] bytes = multipartFile.getBytes();

            File dir = new File("uploaded");

            if (!dir.exists() && !dir.mkdirs()) {
                throw new FailedCreationException();
            }

            File uploadedFile = new File(dir.getAbsolutePath() + File.separator + name);
            boolean isFileExisted = uploadedFile.exists();

            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile))) {
                stream.write(bytes);
                stream.flush();
            }

            if (isFileExisted) {
                log.info("Перезаписан файл: " + name);
            } else {
                log.info("Загружен файл: " + name);
            }

            return name;
        } else {
            throw new EmptyFileException();
        }
    }

    @SneakyThrows
    @Override
    public byte[] downloadFile(String fileName) {
        File uploadedFolder = new File("uploaded");
        File[] matchingFiles = uploadedFolder.listFiles((dir, existingFileName) -> existingFileName.equals(fileName));

        if (matchingFiles == null || matchingFiles.length == 0) {
            throw new UnavailableFileException(fileName);
        }

        byte[] documentBody = new FileInputStream(matchingFiles[0]).readAllBytes();

        log.info("Скачан файл: " + fileName);

        return documentBody;
    }
}
