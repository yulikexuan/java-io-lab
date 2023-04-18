//: com.yulikexuan.io.channels.ChannelCopy.java


package com.yulikexuan.io.channels;


import static java.nio.file.StandardOpenOption.*;

import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;


@AllArgsConstructor(staticName = "of")
class ChannelCopy {

    static final int BUFFER_CAPACITY = 64 * 1024;

    private final Path src;
    private final Path dest;

    public void copyFile() {

        if (!Files.exists(this.src)) {
            return;
        }

        try (SeekableByteChannel rbc = Files.newByteChannel(src, READ);
             SeekableByteChannel wbc = Files.newByteChannel(dest,
                     CREATE, WRITE, TRUNCATE_EXISTING);) {

            copyChannel(rbc, wbc);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void copyChannel(@NonNull final SeekableByteChannel source,
                     @NonNull final SeekableByteChannel dest) throws IOException {

        ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER_CAPACITY);

        // source.read(buffer) returns -1 if the channel has reached end-of-stream
        while (source.read(buffer) != -1) {
            buffer.flip();
            dest.write(buffer);
            buffer.compact();
        }

        buffer.flip();
        while (buffer.hasRemaining()) {
            dest.write(buffer);
        }
    }

}///:~