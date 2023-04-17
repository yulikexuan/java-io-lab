package com.yulikexuan.io.buffers;


import static com.yulikexuan.io.buffers.BufferFillingDraining.DATA;
import static com.yulikexuan.io.buffers.BufferFillingDraining.BUFFER_CAPACITY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import java.nio.CharBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;


@DisplayName("Test BufferFillingDraining class - ")
@DisplayNameGeneration(ReplaceUnderscores.class)
class BufferFillingDrainingTest {

    private BufferFillingDraining demo;

    @BeforeEach
    void setUp() {
        this.demo = BufferFillingDraining.of(CharBuffer.allocate(BUFFER_CAPACITY));
    }

    @Test
    void test_Filling_Draining() {

        // Given

        // When
        List<String> data = demo.copy(DATA);

        // Then
        assertThat(data).containsExactlyElementsOf(DATA);
        assertThat(data).isNotSameAs(DATA);
    }

}///:~