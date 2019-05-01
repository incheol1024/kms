package com.devworker.kms.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public static File convertToFile(MultipartFile multiPartFile, String path) throws IOException {

		String filePath = convertToEndwithSeperator(path);
		File file = new File(filePath +   StringKeyUtil.generateUniqueKey() + multiPartFile.getName());
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

}
