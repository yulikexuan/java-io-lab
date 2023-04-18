//: com.yulikexuan.io.mmfs.MemoryMappedFileReading.java


package com.yulikexuan.io.mmfs;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;


@Slf4j
@AllArgsConstructor(staticName = "of")
class MemoryMappedFileReading {

    static final int ONE_MB = 1024 * 1024;

    private final Path src;

    public void reading() {

        if (!Files.exists(this.src)) {
            return;
        }

        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(
                this.src, EnumSet.of(READ))) {

            final long fileSize = fileChannel.size();
            log.info(">>> File Size to Read: {} Mb", fileSize / ONE_MB);

            final MappedByteBuffer byteBuffer = fileChannel.map(
                    READ_ONLY, 0, fileSize);

            for (int i = 0; i < byteBuffer.limit(); i++) {
                byteBuffer.get(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}///:~