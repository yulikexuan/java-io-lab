//: com.yulikexuan.io.buffers.ViewBufferTest.java


package com.yulikexuan.io.buffers;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@Slf4j
@DisplayName("Test NIO View Buffers - ")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ViewBufferTest {

    static final String DATA = "Hello Java NIO!";

    static final int CAPACITY = 32;

    private ByteBuffer originBuffer;

    private CharBuffer viewBuffer;

    @BeforeEach
    void setUp() {
        this.originBuffer = ByteBuffer.allocateDirect(CAPACITY);
    }

    @Test
    void view_Buffer_Has_Its_Own_Pos_Limit() {

        // Given
        this.viewBuffer = this.originBuffer.asCharBuffer();

        // When
        filling(originBuffer);

        // Then
        assertThat(this.originBuffer).satisfies(ob -> {
            assertThat(ob.position()).isZero();
            assertThat(ob.limit()).isEqualTo(CAPACITY);
            assertThat(ob.limit()).isEqualTo(CAPACITY);
            assertThat(ob.isDirect()).isTrue();
        });

        assertThat(this.viewBuffer).satisfies(vb -> {
            assertThat(vb.position()).isZero();
            assertThat(vb.limit()).isEqualTo(CAPACITY / 2);
            assertThat(vb.capacity()).isEqualTo(CAPACITY / 2);
            assertThat(vb.toString().trim()).isEqualTo(DATA);
            assertThat(vb.isDirect()).isTrue();
        });

        assertThat(this.originBuffer.order()).isSameAs(this.viewBuffer.order());
    }

    private void filling(ByteBuffer buffer) {

        int i = 0;

        for (char ch : DATA.toCharArray()) {
            buffer.put(i++, (byte)0);
            buffer.put(i++, (byte)ch);
        }
    }

}///:~