package beryanov.service.impl;

import beryanov.exception.file.EmptyFileException;
import beryanov.exception.file.FailedCreationException;
import beryanov.exception.file.UnavailableFileException;
import beryanov.service.FileService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
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

            blurAndSave(uploadedFile);

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

    private void blurAndSave(File uploadedFile) {
        OpenCV.loadLocally();

        Mat notBlurredImage = Imgcodecs.imread(uploadedFile.getAbsolutePath());
        Mat toBeBlurredImage = new Mat(300, 300, notBlurredImage.type());
        Mat blurredResizedImage = new Mat();

        Imgproc.GaussianBlur(notBlurredImage, toBeBlurredImage, new Size(99,99),49);
        Imgproc.resize(toBeBlurredImage, blurredResizedImage, new Size(300, 300));

        int extensionDotIndex = uploadedFile.getAbsolutePath().lastIndexOf(".");
        String fullPath = uploadedFile.getAbsolutePath();
        String path = fullPath.substring(0, extensionDotIndex);
        String extension = fullPath.substring(extensionDotIndex);
        String finalBlurPath = path + "_blur" + extension;

        File blurredFile = new File(finalBlurPath);
        boolean isBlurredFileExisted = blurredFile.exists();

        Imgcodecs.imwrite(finalBlurPath, blurredResizedImage);

        int fileNameSlashIndex = finalBlurPath.lastIndexOf("/") + 1;
        String blurredName = finalBlurPath.substring(fileNameSlashIndex);

        if (isBlurredFileExisted) {
            log.info("Перезаписан файл размытого изображения: " + blurredName);
        } else {
            log.info("Создан файл размытого изображения: " + blurredName);
        }
    }
}
