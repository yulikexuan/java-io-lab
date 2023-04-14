//: com.yulikexuan.io.domain.model.JavaFileChannelReader


package com.yulikexuan.io.domain.model;


import lombok.NonNull;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class JavaFileChannelReader {

    public void reading(@NonNull final String path, final int bufferSize) {

        try (FileInputStream fis = new FileInputStream(path);
             FileChannel inputChannel = fis.getChannel();) {

            ByteBuffer buffer = ByteBuffer.allocateDirect(bufferSize);

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                buffer.limit();
                buffer.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}///:~