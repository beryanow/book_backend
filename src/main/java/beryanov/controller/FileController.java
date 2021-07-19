package beryanov.controller;

import beryanov.dto.FileNameDto;
import beryanov.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/file")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        return fileService.uploadFile(multipartFile);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value = "/download", consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] downloadFile(@Valid @RequestBody FileNameDto fileNameDto) {
        return fileService.downloadFile(fileNameDto.getFileName());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/download/{name}", produces = {MediaType.IMAGE_PNG_VALUE})
    public byte[] directDownloadFile(@PathVariable String name) {
        return fileService.downloadFile(name);
    }
}
