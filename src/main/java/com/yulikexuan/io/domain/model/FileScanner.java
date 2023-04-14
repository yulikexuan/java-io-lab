//: com.yulikexuan.io.domain.model.FileScanner


package com.yulikexuan.io.domain.model;


import lombok.NonNull;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class FileScanner {

    public void scan(@NonNull final String path) {

        try (FileInputStream inputStream = new FileInputStream(path);
             Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);) {

            while (sc.hasNextLine()) {
                final String line = sc.nextLine();
            }

            if (sc.ioException() != null) {
                throw sc.ioException();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}///:~