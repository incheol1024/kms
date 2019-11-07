package com.devworker.kms.component;

import java.io.File;

import com.devworker.kms.dto.common.FileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.common.DocRepo;

@Component
public class FileHandlerImplLocal implements FileHandler {

	@Autowired
	UserRepo userRepo;

	@Autowired
	DocRepo docRepo;

	private static String FILE_PATH = FileHandler.DEFAULT_PATH;

	@Override
	public File processDownloadFile(String key) {
		//return new File(fileKey);
		return null;
	}

	@Override
	public String processUploadFile(File file) {

		/*
		 * File destFile = new File(FILE_PATH + file.getOriginalFilename());
		 * file.transferTo(destFile);
		 * 
		 * FileDto fileDto = new FileDto.FileDtoBuilder(destFile.getAbsolutePath())
		 * .setFileName(destFile.getName()) .setFileSize((int)destFile.length())
		 * .setFileExt(FilenameUtils.getExtension(destFile.getName())) .build();
		 * 
		 * return fileDto;
		 */
		
		return null;
		}

	@Override
	public boolean deleteFile(String key) {
		
		File file = new File(key);
		if(file.exists()) {
			return file.delete();
		}
		
		return false;
	}


}
