//: com.yulikexuan.io.app.controller.FileIoController


package com.yulikexuan.io.app.controller;


import com.yulikexuan.io.app.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * curl -v <a href="http://localhost:8080/io/fasted/file/size">Fetch File Size</a>
 * curl -v <a href="http://localhost:8080/io/fasted/file/target/exists">File Exists?</a>
 * curl -v -X PATCH <a href="http://localhost:8080/io/fasted/file/target">Copy File</a>
 * curl -v -X DELETE <a href="http://localhost:8080/io/fasted/file/target">Delete File</a>
 */
@RestController
@RequestMapping("/io")
@AllArgsConstructor
class FileIoController {

    private final FileService fileService;

    @GetMapping("/fasted/file/size")
    public long dataFileSize() throws IOException {
        return fileService.fileSize();
    }

    @GetMapping("/fasted/file/target/exists")
    public boolean targetFileExists() throws IOException {
        return fileService.targetFileExists();
    }

    @PatchMapping ("/fasted/file/target")
    public long copyDataFile() throws IOException {
        return fileService.fastedCopy();
    }

    @DeleteMapping("/fasted/file/target")
    public boolean deleteTargetFile() throws IOException {
        return fileService.deleteTargetFile();
    }

}///:~