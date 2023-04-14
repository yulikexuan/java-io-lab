//: com.yulikexuan.io.LargeFileReadingTest


package com.yulikexuan.io;


import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.yulikexuan.io.domain.model.FileLineIterator;
import com.yulikexuan.io.domain.model.FileScanner;
import com.yulikexuan.io.domain.model.JavaBufferedReader;
import com.yulikexuan.io.domain.model.JavaFileChannelReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;


@Disabled(">>> Processing Large Files - ")
@Slf4j
@DisplayName("Test IO for Large Files - ")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LargeFileReadingIT {

    static final int ONE_MB = 1048576;

    String SOURCE_FILE_PATH = "../tmp/data.txt";

    private StopWatch stopWatch;

    @BeforeEach
    void setUp() {
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
     * ------------------------------------
     * >>> Total Memory: 1664 Mb
     * >>> Free Memory: 487 Mb
     * ------------------------------------
     * >>> Time: 1041 ms.
     *
     */
    @Test
    void reading_Large_File_With_Java_NIO_Read_All_Bytes() throws IOException {

        // Given
        logMemory();

        // When
        byte[] bytes = java.nio.file.Files.readAllBytes(Path.of(SOURCE_FILE_PATH));

        // Then
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Total Memory: 1856 Mb
     * >>> Free Memory: 426 Mb
     * ------------------------------------
     * >>> Time: 3389 ms.
     *
     */
    @Test
    void reading_Large_File_With_Guava() throws Exception {
        logMemory();
        Files.readLines(new File(SOURCE_FILE_PATH), Charsets.UTF_8);
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 491 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 443 Mb
     * ------------------------------------
     * >>> Time: 15390 ms.
     *
     */
    @Test
    void reading_Large_File_With_Scanner() {

        // Given
        FileScanner fileScanner = new FileScanner();
        logMemory();

        // When
        fileScanner.scan(SOURCE_FILE_PATH);

        // Then
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 491 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 273 Mb
     * ------------------------------------
     * >>> Time: 2459 ms.
     *
     */
    @Test
    void reading_Large_File_With_Commons_IO_Iterator() throws Exception {

        // Given
        logMemory();
        FileLineIterator fileLineIterator = new FileLineIterator();

        // When
        fileLineIterator.iterate(SOURCE_FILE_PATH);

        // Then
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 283 Mb
     * ------------------------------------
     * >>> Time: 2527 ms.
     *
     */
    @Test
    void reading_Large_File_With_Java_IO_Buffered_Reader() throws Exception {

        // Given
        logMemory();
        JavaBufferedReader javaBufferedReader = new JavaBufferedReader();

        // When
        javaBufferedReader.reading(SOURCE_FILE_PATH);

        // Then
        logMemory();
    }

    /*
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Total Memory: 508 Mb
     * >>> Free Memory: 490 Mb
     * ------------------------------------
     * >>> Time: 884 ms / 4Kb Buffer Size
     * >>> Time: 526 ms / 10Kb Buffer Size
     * >>> Time: 306 ms / 100Kb Buffer Size
     * >>> Time: 269 ms / 512Kb Buffer Size
     * >>> Time: 262 ms / 1Mb Buffer Size
     */
    @Test
    void reading_Large_File_With_Java_NIO_File_Channel() {

        // Given
        logMemory();
        JavaFileChannelReader javaFileChannelReader = new JavaFileChannelReader();

        // When
        javaFileChannelReader.reading(SOURCE_FILE_PATH, 100 * 1024);

        // Then
        logMemory();
    }

    ////////////////////////////////////////////////////////////////////////////////

    private void logMemory() {
        log.info("------------------------------------");
        // log.info(">>> Max Memory: {} Mb", maxMemory());
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