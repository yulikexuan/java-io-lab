# Large File IO


## Testing Commands
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/size"
- curl -w "@curl_format.txt" -s -v "http://localhost:8080/io/fasted/file/target/exists"
- curl -w "@curl_format.txt" -s -v -X DELETE "http://localhost:8080/io/fasted/file/target"
- curl -w "@curl_format.txt" -s -v -X PATCH "http://localhost:8080/io/fasted/file/target"


## Memory-Mapped Files

### Resource
- #### [Java & Big Data Interview Companion](https://www.java-success.com/processing-large-files-efficiently-java-part-1/)
- #### [Using Java MappedByteBuffer](https://www.baeldung.com/java-mapped-byte-buffer)


### To Map the Entire File 
- ``` 
  RandomAccessFile aFile = new RandomAccessFile("c:/temp/my-large-file.csv", "r");
  FileChannel inChannel = aFile.getChannel();
  MappedByteBuffer buffer = fileChannel.map(
          FileChannel.MapMode.READ_ONLY, 
          0, // The position within the file at which the mapped region is to start
          fileChannel.size()); // The size of the region to be mapped
  ```
- Mapped File Ranges Should NOT Extend Beyond the Actual Size of the File


### File Mappings can be Writable or Read-Only
- ``` 
  public static class MapMode {
      public static final MapMode READ_ONLY
      public static final MapMode READ_WRITE
      public static final MapMode PRIVATE // a copy-on-write mapping
  }
  ```
  

### A ``` MemoryMappedBuffer ``` Directly Reflects the Disk File 
- A ``` MemoryMappedBuffer ``` has a Fixed Size
  - but the File it's mapped to is Elastic
- If a File's Size Changes while the Mapping is in Effect
  - some or all of the buffer may become inaccessible
  - undefined data could be returned
  - unchecked exceptions could be thrown 


### Be Careful about How Files are Manipulated 
  - by Other Threads or External Processes when they are memory-mapped


### All MappedByteBuffer Objects are Direct
- The memory space they occupy lives outside the JVM heap


### ``` MappedByteBuffers ``` can be Passed to 
- ``` Channel::read ``` 
- ``` Channel::write ``` 


### When Updating a File Through a ``` MappedByteBuffer ```
- Should Always Use ``` MappedByteBuffer::force ``` Rather Than ```FileChannel::force ```
- Metadata is Always Flushed Too
- Calling ``` MappedByteBuffer::force ``` has NO Effect 
  - if the mapping was established with ``` MapMode.READ_ONLY ``` or ``` MAP_MODE.PRIVATE ```


### ``` MappedByteBuffer::load ``` and ``` MappedByteBuffer::isLoaded ```
- ___NOT Encouraged to Use___


### Code Example

``` 

//: com.yulikexuan.io.mmfs.MemoryMappedFileReading.java


package com.yulikexuan.io.mmfs;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.EnumSet;

import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.file.StandardOpenOption.READ;


@Slf4j
@AllArgsConstructor(staticName = "of")
class MemoryMappedFileReading {

    static final int ONE_MB = 1024 * 1024;

    private final Path src;

    public void reading() {

        if (!Files.exists(this.src)) {
            return;
        }

        try (FileChannel fileChannel = (FileChannel) Files.newByteChannel(
                this.src, EnumSet.of(READ))) {

            final long fileSize = fileChannel.size();
            log.info(">>> File Size to Read: {} Mb", fileSize / ONE_MB);

            final MappedByteBuffer byteBuffer = fileChannel.map(
                    READ_ONLY, 0, fileSize);

            for (int i = 0; i < byteBuffer.limit(); i++) {
                byteBuffer.get(i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}///:~

```