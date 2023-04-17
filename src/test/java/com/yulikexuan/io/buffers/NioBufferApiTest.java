//: com.yulikexuan.io.buffers.NioBufferApiTest


package com.yulikexuan.io.buffers;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.nio.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@Slf4j
@DisplayName("Test NIO Buffer API - ")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NioBufferApiTest {

    static final int CAPACITY = 8;
    static final int DATA_LENGTH = 6;

    @Test
    void test_Byte_Buffer_Allocation() {

        // Given

        // When
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // Then
        assertThat(byteBuffer).satisfies(buffer -> {
            assertThat(buffer.capacity()).isEqualTo(CAPACITY);
            assertThat(buffer.limit()).isEqualTo(CAPACITY);
            assertThat(buffer.position()).isEqualTo(0); });
    }

    @Test
    void test_Byte_Buffer_Put() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer).satisfies(buffer -> {
            assertThat(buffer.capacity()).isEqualTo(CAPACITY);
            assertThat(buffer.limit()).isEqualTo(CAPACITY);
            assertThat(buffer.position()).isEqualTo(DATA_LENGTH);
        });
    }

    @Test
    void test_Byte_Buffer_Absolute_Put() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        byteBuffer.put(0, (byte)'h');

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);
    }

    @Test
    void test_Byte_Buffer_Flipping_For_Data_Draining() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.flip();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(0);
    }

    @Test
    void test_Byte_Buffer_Rewinding() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.rewind();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(0);
    }

    @Test
    void test_Byte_Buffer_Rewinding_After_Flipping() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.flip();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(0);

        // When
        byteBuffer.put((byte) 'W')
                .put((byte) 'E')
                .put((byte) 'L')
                .put((byte) 'C')
                .put((byte) 'O')
                .put((byte) 'M');

        byteBuffer.rewind();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(0);
    }

    @Test
    void double_Flipped_Buffer_Is_Zero_Sized() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.flip();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(0);

        // When
        byteBuffer.flip();

        // Then
        assertThat(byteBuffer.capacity()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.limit()).isEqualTo(0);
        assertThat(byteBuffer.position()).isEqualTo(0);

        // When
        assertThatThrownBy(() -> byteBuffer.put((byte) 'X'))
                .isExactlyInstanceOf(BufferOverflowException.class);
        assertThatThrownBy(byteBuffer::get)
                .isExactlyInstanceOf(BufferUnderflowException.class);
    }

    @Test
    void test_Byte_Buffer_Marking() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        // When
        fillBuffer(byteBuffer);

        // Then
        assertThat(byteBuffer.limit()).isEqualTo(CAPACITY);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.flip();

        // Then
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(0);

        // When
        byteBuffer.position(2)
                .mark()
                .position(4);

        // Then
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(4);

        // When
        byteBuffer.get();
        byteBuffer.get();

        // Then
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);

        // When
        byteBuffer.reset();

        // Then
        assertThat(byteBuffer.limit()).isEqualTo(DATA_LENGTH);
        assertThat(byteBuffer.position()).isEqualTo(2);
    }

    @Test
    void different_Type_Buffers_Never_Equal_To_Each_Other() {

        // Given
        CharBuffer cb = CharBuffer.allocate(CAPACITY);
        ByteBuffer bb = ByteBuffer.allocate(CAPACITY);

        // When & Then
        assertThat(cb).isNotEqualTo(bb);
        assertThat(bb).isNotEqualTo(cb);
    }

    @Test
    void two_Buffers_Are_Equal_If_All_Have_Same_Remaining_Elements_Even_Capacity_Are_Different() {

        // Given
        ByteBuffer b1 = ByteBuffer.allocate(CAPACITY);
        ByteBuffer b2 = ByteBuffer.allocate(CAPACITY * 2);

        // When
        fillBuffer(b1);
        fillBuffer(b2);

        // Then
        assertThat(b1).isNotEqualTo(b2);

        // When
        b1.flip();
        b2.flip();

        // Then
        assertThat(b1).isEqualTo(b2);
        assertThat(b1.compareTo(b2)).isZero();

        // When
        b2.get();
        b2.get();

        // Then
        assertThat(b1.compareTo(b2)).isLessThan(0);

        // When
        b1.get();
        b1.get();
        b1.get();

        // Then
        assertThat(b1.compareTo(b2)).isEqualTo(111 - 108);
    }

    @Test
    void bulk_Draining_A_Buffer() {

        // Given
        ByteBuffer b1 = ByteBuffer.allocate(CAPACITY);
        fillBuffer(b1);
        b1.flip();

        final byte[] data = new byte[DATA_LENGTH];

        // When & Then
        assertThat(b1.get(data)).satisfies(b -> {
            assertThat(data).containsExactly(72, 101, 108, 108, 111, 33);
            assertThat(b.position()).isEqualTo(DATA_LENGTH);
        });

        // When
        b1.clear();
        fillBuffer(b1);
        b1.flip();
        final byte[] data2 = new byte[CAPACITY];

        // Then
        assertThat(b1.get(data2, 0, DATA_LENGTH)).satisfies(b -> {
            assertThat(data2).containsExactly(72, 101, 108, 108, 111, 33, 0, 0);
            assertThat(b.position()).isEqualTo(DATA_LENGTH);
        });
    }

    @Test
    void bulk_Draining_Small_Buffer_Into_Large_Array() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
        fillBuffer(byteBuffer);
        byteBuffer.flip();

        final byte[] dataArray = new byte[2 + CAPACITY];

        // When
        assertThatThrownBy(() -> byteBuffer.get(dataArray))
                .isExactlyInstanceOf(BufferUnderflowException.class);

        // Then
        assertThat(dataArray).containsExactly(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

        // When
        byteBuffer.get(dataArray, 0, byteBuffer.remaining());

        // Then
        assertThat(byteBuffer.position()).isEqualTo(DATA_LENGTH);
        assertThat(dataArray).containsExactly(72, 101, 108, 108, 111, 33, 0, 0, 0, 0);
    }

    @Test
    void bulk_Draining_Big_Buffer_With_Small_Array() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);
        fillBuffer(byteBuffer);
        byteBuffer.flip();

        final byte[] dataArray = new byte[CAPACITY / 4];

        StringBuffer result = new StringBuffer();

        // When
        while (byteBuffer.hasRemaining()) {
            int length = Math.min(byteBuffer.remaining(), dataArray.length);
            byteBuffer.get(dataArray, 0, length);
            for (byte b : dataArray) {
                result.append(b);
            }
        }

        // Then
        assertThat(result).contains("7210110810811133");
    }

    @Test
    void bulk_Filling_Buffer() {

        // Given
        ByteBuffer byteBuffer = ByteBuffer.allocate(CAPACITY);

        byte[] data = {72, 101, 108, 108, 111, 33};

        // When
        byteBuffer.put(data);

        // Then
        assertThat(byteBuffer).satisfies(b -> {
            assertThat(b.position()).isEqualTo(data.length);
            assertThat(b.limit()).isEqualTo(CAPACITY);
        });

        // When
        byte[] data2 = {72, 101, 108, 108, 111, 33, 72, 101, 108, 108, 111, 33};
        byteBuffer.clear();
        assertThatThrownBy(() -> byteBuffer.put(data2))
                .isExactlyInstanceOf(BufferOverflowException.class);
    }

    @Test
    void bulk_Filling_Draining_Buffer_With_Another_Buffer() {

        // Given
        final ByteBuffer srcBuffer = ByteBuffer.allocate(CAPACITY);
        final ByteBuffer targetBuffer = ByteBuffer.allocate(CAPACITY);
        fillBuffer(srcBuffer);
        srcBuffer.flip();

        // When
        targetBuffer.put(srcBuffer);

        // Then
        assertThat(targetBuffer).satisfies(b -> {
            assertThat(b.position()).isEqualTo(DATA_LENGTH)
                    .isEqualTo(srcBuffer.position());
            assertThat(b.limit()).isEqualTo(CAPACITY);
        });
        assertThat(srcBuffer.limit()).isEqualTo(DATA_LENGTH);
    }

    @Test
    void bulk_Filling_Small_Buffer_With_Another_Big_Buffer() {

        // Given
        final ByteBuffer srcBuffer = ByteBuffer.allocate(CAPACITY);
        final ByteBuffer targetBuffer = ByteBuffer.allocate(CAPACITY / 2);
        fillBuffer(srcBuffer);
        srcBuffer.flip();

        // When
        assertThatThrownBy(() -> targetBuffer.put(srcBuffer))
                .isExactlyInstanceOf(BufferOverflowException.class);
    }

    @Test
    void bulk_Filling_CharBuffer_String() {

        // Given
        final String data = "Hello!";
        final CharBuffer charBuffer = CharBuffer.allocate(CAPACITY);

        // When
        charBuffer.put(data, 0, data.length()); // charBuffer.put(data);

        // Then
        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isEqualTo(DATA_LENGTH);
            assertThat(cb.limit()).isEqualTo(CAPACITY);
        });

        // When
        charBuffer.flip();

        // Then
        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isZero();
            assertThat(cb.limit()).isEqualTo(DATA_LENGTH);
        });

        // Given
        final String newData = "Welcome to Java NIO!";
        charBuffer.clear();

        // When & Then
        assertThatThrownBy(() -> charBuffer.put(newData)).isExactlyInstanceOf(
                BufferOverflowException.class);
    }

    @Test
    void allocate_New_Buffer_With_Wrapping_Array_Then_Position_At_Zero() {

        // Given
        byte[] data = {72, 101, 108, 108, 111, 33};

        // When
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);

        // Then
        assertThat(byteBuffer).satisfies(bb -> {
            assertThat(byteBuffer.position()).isEqualTo(0);
            assertThat(byteBuffer.limit()).isEqualTo(data.length);
            assertThat(byteBuffer.capacity()).isEqualTo(data.length);
        });

        // When
        byteBuffer.clear();
    }

    @Test
    void allocate_New_Buffer_With_Wrapping_Array_From_Offset_And_Length() {

        // Given
        byte[] data = {72, 101, 108, 108, 111, 33};
        final int offset = 2;
        final int length = 3;

        // When
        ByteBuffer byteBuffer = ByteBuffer.wrap(data, offset, length);

        // Then
        assertThat(byteBuffer).satisfies(bb -> {
            assertThat(byteBuffer.position()).isEqualTo(offset);
            assertThat(byteBuffer.limit()).isEqualTo(offset + length);
            assertThat(byteBuffer.capacity()).isEqualTo(data.length);
        });

        // When
        byteBuffer.clear();

        // Then
        assertThat(byteBuffer).satisfies(bb -> {
            assertThat(byteBuffer.position()).isEqualTo(0);
            assertThat(byteBuffer.limit()).isEqualTo(data.length);
            assertThat(byteBuffer.capacity()).isEqualTo(data.length);
        });

        boolean hasBackArray = byteBuffer.hasArray();
        assertThat(hasBackArray).isTrue();
        assertThat(byteBuffer.array()).isSameAs(data);
    }

    @Test
    void allocate_CharBuffer_With_String() {

        // Given
        CharBuffer charBuffer = CharBuffer.wrap("Hello!");

        // When & Then
        assertThat(charBuffer).satisfies(cb -> {
            assertThat(cb.position()).isEqualTo(0);
            assertThat(cb.limit()).isEqualTo(DATA_LENGTH);
            assertThat(cb.isReadOnly()).isTrue();
        });
    }

    private void logBuffer(Buffer buffer) {
        log.info("------------------------------------");
        log.info(">>> Capacity: {}", buffer.capacity());
        log.info(">>> Limit: {}", buffer.limit());
        log.info(">>> Position: {}", buffer.position());
        log.info(">>> Mark: {}", buffer.mark());
    }

    private static void fillBuffer(@NonNull final ByteBuffer byteBuffer) {
        byteBuffer.put((byte) 'H')    // 72
                .put((byte) 'e')      // 101
                .put((byte) 'l')      // 108
                .put((byte) 'l')      // 108
                .put((byte) 'o')      // 111
                .put((byte) '!');     // 33
    }

}///:~