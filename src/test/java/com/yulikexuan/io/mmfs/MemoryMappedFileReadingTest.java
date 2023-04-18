package com.yulikexuan.io.mmfs;//: com.yulikexuan.io.mmfs.MemoryMappedFileReadingTest.java


import com.yulikexuan.io.channels.ChannelReading;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static com.yulikexuan.io.mmfs.MemoryMappedFileReading.ONE_MB;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;


@Slf4j
@ExtendWith(MockitoExtension.class)
@DisplayName("Test Large File Reading with MappedByteBuffer - ")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemoryMappedFileReadingTest {

    static final String SRC_FILE_PATH = "../tmp/data.txt";

    private Path srcPath;

    private ChannelReading channelReading;

    private MemoryMappedFileReading memoryMappedReading;


    private StopWatch stopWatch;

    @BeforeEach
    void setUp() {
        this.srcPath = Path.of(SRC_FILE_PATH);
        this.stopWatch = StopWatch.createStarted();
    }

    @AfterEach
    void tearDown() {
        this.stopWatch.stop();
        log.info("------------------------------------");
        log.info(">>> Time: {} ms. ", this.stopWatch.getTime(TimeUnit.MILLISECONDS));
        log.info(" ");
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * >>> File Size to Read: 1164 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Time: 22 ms.
     *
     */
    @Test
    void reading_Large_File_With_Memory_Mapped_Buffer() {

        // Given
        this.memoryMappedReading = MemoryMappedFileReading.of(this.srcPath);
        this.logMemory();

        // When
        this.memoryMappedReading.reading();

        // Then
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 492 Mb
     * >>> File Size to Read: 1164 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Time: 1222 ms.
     *
     */
    @Test
    void reading_Large_File_With_File_Channel() {

        // Given
        this.channelReading = ChannelReading.of(this.srcPath);
        this.logMemory();

        // When
        this.channelReading.reading();

        // Then
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