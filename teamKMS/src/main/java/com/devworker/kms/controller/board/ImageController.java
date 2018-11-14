package com.devworker.kms.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devworker.kms.service.board.FileHandler;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	FileHandler fileHandler;

	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestParam(name = "filekey") String filekey)
			throws IOException {

		File file = fileHandler.downloadFile(filekey.trim());
		
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok().headers(new HttpHeaders()).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);
	}

}
