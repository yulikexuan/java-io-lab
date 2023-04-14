//: com.yulikexuan.io.domain.model.FileLineIterator


package com.yulikexuan.io.domain.model;


import lombok.NonNull;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;

public class FileLineIterator {

    public void iterate(@NonNull final String path) {

        try (LineIterator it = FileUtils.lineIterator(
                new File(path), "UTF-8");) {

            while (it.hasNext()) {
                String line = it.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}///:~