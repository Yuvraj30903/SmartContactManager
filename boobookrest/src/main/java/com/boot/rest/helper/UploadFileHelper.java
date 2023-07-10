package com.boot.rest.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class UploadFileHelper {
		
	
//	private final String UPLOAD_DIR="C:\\Users\\Shruti\\Documents\\workspace-spring-tool-suite-4-4.19.0.RELEASE\\boobookrest\\src\\main\\resources\\static\\image";
	private final String UPLOAD_DIR=new ClassPathResource("static\\image").getFile().getAbsolutePath();
	
	
	public UploadFileHelper() throws IOException{
		super();
		// TODO Auto-generated constructor stub
	}


	public boolean fileUpload(MultipartFile data)
	{
		boolean f=false;
		try {		
			Files.copy(data.getInputStream(),Paths.get(UPLOAD_DIR+"\\"+data.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			f=true;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
}
