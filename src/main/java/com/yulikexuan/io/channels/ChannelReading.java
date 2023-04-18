//: com.yulikexuan.io.channels.ChannelReading.java


package com.yulikexuan.io.channels;


import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.READ;


@AllArgsConstructor(staticName = "of")
public class ChannelReading {

    static final int BUFFER_CAPACITY = 16 * 1024;

    private final Path src;

    public void reading() {

        if (!Files.exists(this.src)) {
            return;
        }

        try (SeekableByteChannel rbc = Files.newByteChannel(src, READ);) {

            final ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_CAPACITY);

            while (rbc.read(buffer) != -1) {
                buffer.flip();
                readByte(buffer);
                buffer.compact();
            }

            buffer.flip();
            readByte(buffer);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void readByte(@NonNull final ByteBuffer buffer) {
        while (buffer.hasRemaining()) {
            buffer.get();
        }
    }

}///:~