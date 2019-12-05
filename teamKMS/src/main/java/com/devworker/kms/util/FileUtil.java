package com.devworker.kms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import com.devworker.kms.component.BeanUtilComponent;
import com.fasterxml.jackson.databind.util.BeanUtil;
import javassist.runtime.Inner;
import org.apache.tika.Tika;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUtil {

    private FileUtil() {
    }


    public File convertToFile(MultipartFile multipartFile, String path) throws IOException {
        return InnerFileUtil.convertToFile(multipartFile, path);
    }

    public String getContentType(File file) {
        return InnerFileUtil.getContentType(file);
    }

    public String getContentType(Path path) {
        return InnerFileUtil.getContentType(path);
    }

    public MediaType getMediaType(File file){
        return InnerFileUtil.getMediaType(file);
    }





    private static class InnerFileUtil {

        private static final Tika tika = new Tika();

        private static File convertToFile(MultipartFile multiPartFile, String path) throws IOException {

            String filePath = convertToEndwithSeperator(path);
            File file = new File(filePath + StringKeyUtil.generateUniqueKey() + multiPartFile.getName());
            file.createNewFile();

            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                fileOutputStream.write(multiPartFile.getBytes());
                return file;
            }
        }

        private static String convertToEndwithSeperator(String path) {
            if (path.endsWith(File.separator))
                return path;

            return path + File.separator;
        }

        private static String getContentType(File file) {
            String contentType = "";
            try {
                contentType = tika.detect(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentType;
        }

        private static String getContentType(Path path) {
            String contentType = "";
            try {
                contentType = tika.detect(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return contentType;
        }


        public static MediaType getMediaType(File file) {
            return null;
        }

    }


}
