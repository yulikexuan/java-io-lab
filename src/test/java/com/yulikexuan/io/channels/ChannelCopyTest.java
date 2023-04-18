package com.yulikexuan.io.channels;//: com.yulikexuan.io.channels.ChannelCopyTest.java


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;


@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("Test Channel Copy Process - ")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ChannelCopyTest {

    static final int ONE_MB = 1024 * 1024;

    static final int BUFFER_CAPACITY = 64 * 1024;

    static final String SOURCE_FILE_PATH = "../tmp/Big_Msg_Body.txt";
    static final String DEST_FILE_PATH = "../tmp/Big_Msg_Body_Copy.txt";

    private Path source;
    private Path dest;

    private ChannelCopy channelCopy;

    private StopWatch stopWatch;

    @BeforeEach
    void setUp() {
        this.source = Path.of(SOURCE_FILE_PATH);
        this.dest = Path.of(DEST_FILE_PATH);
        this.channelCopy = ChannelCopy.of(this.source, this.dest);
        this.stopWatch = StopWatch.createStarted();
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(this.dest);
        this.stopWatch.stop();
        log.info("------------------------------------");
        log.info(">>> Time: {} ms. ",
                this.stopWatch.getTime(TimeUnit.MILLISECONDS));
        log.info(" ");
    }

    @Test
    void copy_File_With_Channel() throws Exception {

        // Given
        logMemory();

        // When
        this.channelCopy.copyFile();

        // Then
        assertThat(Files.exists(this.dest)).isTrue();
        assertThat(Files.size(this.source)).isEqualTo(Files.size(this.dest));
        logMemory();
    }

////////////////////////////////////////////////////////////////////////////////

    private void logMemory() {
        log.info("------------------------------------");
        log.info(">>> Total Memory: {} Mb", totalMemory());
        log.info(">>> Free Memory: {} Mb", freeMemory());
    }

    private static long freeMemory() {
        return Runtime.getRuntime().freeMemory() / ONE_MB;
    }

    private static long totalMemory() {
        return Runtime.getRuntime().totalMemory() / ONE_MB;
    }

    private long maxMemory() {
        return Runtime.getRuntime().maxMemory() / ONE_MB;
    }

}///:~