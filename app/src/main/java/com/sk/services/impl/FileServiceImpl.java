package com.sk.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sk.exception.BadApiRequestException;
import com.sk.services.FileService;


@Service

public class FileServiceImpl implements FileService {

	
	
	private Logger logger=LoggerFactory.getLogger(FileServiceImpl.class);
	
	
	
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException  {
		// TODO Auto-generated method stub
		
		
		
		String originalFilename=file.getOriginalFilename();
		logger.info("Filename:{}", originalFilename);
		
		String filename=UUID.randomUUID().toString();
		String extension=originalFilename.substring(originalFilename.lastIndexOf("."));
		String fileNamewithExtension=filename+extension;
		
		
		String fullPathWithFileName=path+fileNamewithExtension;
		
		logger.info("full image path: {}",fullPathWithFileName);
		if(extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg") || extension.equalsIgnoreCase(".png"))
		{
			
			
			
			logger.info("file extension {}",extension);
			File folder=new File(path);
			if(!folder.exists())
			{
				//create folder
				
				folder.mkdirs();
				
			}
			
			
			//upload 
			
			Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));
			return fileNamewithExtension;
			
			
			
		}else {
			
			throw new BadApiRequestException("File with this "+extension+" is not allowed");
			
		}
		
		
		
		
		
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		
		String fullPath=path+File.separator+name;
		InputStream inputStream=new FileInputStream(fullPath);
		
		
		return inputStream;
		
	}
	
	

}
