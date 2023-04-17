//: com.yulikexuan.io.buffers.BufferDuplicatingTest.java


package com.yulikexuan.io.buffers;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Test NIO Buffer Duplicating API - ")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BufferDuplicatingTest {

    static final int CAPACITY = 8;
    static final int DATA_LENGTH = 6;

    static final String DATA = "Hello!";

    @Test
    void two_Buffers_Are_Independent() {

        // Given
        CharBuffer charBuffer = CharBuffer.wrap(DATA);

        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isZero();
            assertThat(cb.limit()).isEqualTo(DATA.length());
            assertThat(cb.capacity()).isEqualTo(DATA.length());
        });

        int pos = 3;
        int limit = 6;
        int newPos = 5;

        charBuffer.position(pos).limit(limit).mark().position(newPos);

        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isEqualTo(newPos);
            assertThat(cb.limit()).isEqualTo(limit);
            assertThat(cb.capacity()).isEqualTo(DATA.length());
        });

        CharBuffer dupeBuffer = charBuffer.duplicate();

        charBuffer.clear();

        // When
        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isZero();
            assertThat(cb.limit()).isEqualTo(DATA.length());
            assertThat(cb.capacity()).isEqualTo(DATA.length());
            assertThat(cb.isDirect()).isFalse();
        });

        assertThat(dupeBuffer).satisfies(cb -> {
            assertThat(cb.position()).isEqualTo(newPos);
            assertThat(cb.limit()).isEqualTo(limit);
            assertThat(cb.capacity()).isEqualTo(DATA.length());
        });

        dupeBuffer.reset();
        assertThat(dupeBuffer.position()).isEqualTo(pos);
        assertThat(dupeBuffer.isDirect()).isFalse();
    }

}///:~