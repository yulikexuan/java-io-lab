//: com.yulikexuan.io.buffers.BufferSlicingTest.java


package com.yulikexuan.io.buffers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DisplayName("Test NIO Buffer Slicing API - ")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BufferSlicingTest {

    static final int CAPACITY = 8;
    static final int DATA_LENGTH = 6;

    static final String DATA = "Hello!";

    @Test
    void slice_Buffer_Shares_A_Subsequence_Of_The_Data_Elements_Of_Original_Buffer() {

        // Given
        CharBuffer charBuffer = CharBuffer.allocate(CAPACITY);

        int pos = 3;
        int limit = 5;

        charBuffer.position(pos).limit(limit);

        // When
        CharBuffer sliceBuffer = charBuffer.slice();

        // Then
        assertThat(sliceBuffer).satisfies(sb -> {
            assertThat(sb.position()).isEqualTo(0);
            assertThat(sb.limit()).isEqualTo(limit - pos);
            assertThat(sb.capacity()).isEqualTo(limit - pos);
        });
    }

}///:~