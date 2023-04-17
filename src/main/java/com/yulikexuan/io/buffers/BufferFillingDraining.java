//: com.yulikexuan.io.buffers.BufferFillingDraining.java


package com.yulikexuan.io.buffers;


import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
import java.util.List;


@Slf4j
@AllArgsConstructor(staticName = "of")
class BufferFillingDraining {

    static final int BUFFER_CAPACITY = 128;

    static final List<String> DATA = List.of(
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Excuse me while I kiss this fly",
            "Help Me! Help Me!");

    private CharBuffer charBuffer;

    public List<String> copy(@NonNull final List<String> data) {
        return data.stream()
                .map(this::copy)
                .collect(ImmutableList.toImmutableList());
    }

    String copy(@NonNull final String line) {
        this.charBuffer.clear();
        this.fill(line);
        this.charBuffer.flip();
        return this.drain();
    }

    void fill(@NonNull final String line) {
        for (int i = 0; i < line.length(); i++) {
            charBuffer.put(line.charAt(i));
        }
    }

    String drain() {
        StringBuilder stringBuilder = new StringBuilder();
        while (this.charBuffer.hasRemaining()) {
            stringBuilder.append(this.charBuffer.get());
        }
        return stringBuilder.toString();
    }

}///:~