package com.devworker.kms.component;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.devworker.kms.entity.common.BoardDao;
import com.devworker.kms.entity.common.DocDao;
import com.devworker.kms.dto.common.FileDto;
import com.devworker.kms.repo.UserRepo;
import com.devworker.kms.repo.common.DocRepo;

@Component
public class FileHandlerImplLocal implements FileHandler {

	@Autowired
	UserRepo userRepo;

	@Autowired
	DocRepo docRepo;

	@Value("${fileDestPath}")
	private String FILE_PATH;

	@Override
	public FileDto processDownloadFile(FileDto fileDto) {
		//return new File(fileKey);
		return null;
	}

	@Override
	public FileDto processUploadFile(FileDto fileDto) {

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
