package br.com.messagebuddy.util;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileSaver {
	
	public String write(String baseFolder, MultipartFile file) {
		String baseFolderPath = "F:/Documents/JavaWorkspace/usercrud/src/main/resources/static/img/" + baseFolder;
		String transferPath = baseFolderPath + "/" + file.getOriginalFilename();
		String filePath = "img/" + baseFolder + "/" + file.getOriginalFilename();
		try {
			file.transferTo(new File(transferPath));
			return filePath;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
