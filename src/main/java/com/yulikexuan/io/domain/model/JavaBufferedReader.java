//: com.yulikexuan.io.domain.model.JavaBufferedReader.java


package com.yulikexuan.io.domain.model;


import lombok.NonNull;

import java.io.*;
import java.util.stream.Stream;


public class JavaBufferedReader {

    public void reading(@NonNull final String path) {

        try (InputStream inputStream = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(isr);
             Stream<String> linesStream = bufferedReader.lines();) {

            linesStream.forEach(s -> {});

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}///:~