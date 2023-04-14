//: com.yulikexuan.io.app.service.FileService


package com.yulikexuan.io.app.service;


import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public sealed interface FileService permits FileServiceImpl {

    String SOURCE_FILE_PATH = "src/main/resources/data.txt";
    String TARGET_FILE_PATH = "../tmp/data.txt";

    long fastedCopy() throws IOException;

    long fileSize() throws IOException;

    boolean targetFileExists() throws IOException;

    boolean deleteTargetFile() throws IOException;
}

@Service
final class FileServiceImpl implements FileService {

    @Override
    public long fastedCopy() throws IOException {

        Path sourcePath = Path.of(SOURCE_FILE_PATH);
        Path targetPath = Path.of(TARGET_FILE_PATH);

        byte[] bytes = Files.readAllBytes(sourcePath);

        for (int i = 0; i < 50; i++) {
            Files.write(targetPath, bytes, StandardOpenOption.APPEND);
        }

        return Files.size(targetPath) / 1048576;
    }

    @Override
    public long fileSize() throws IOException {
        return Files.size(Path.of(SOURCE_FILE_PATH));
    }

    @Override
    public boolean targetFileExists() throws IOException {
        return Files.exists(Path.of(TARGET_FILE_PATH));
    }

    @Override
    public boolean deleteTargetFile() throws IOException {
        return Files.deleteIfExists(Path.of(TARGET_FILE_PATH));
    }

}

///:~