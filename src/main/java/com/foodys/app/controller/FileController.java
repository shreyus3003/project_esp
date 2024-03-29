package com.foodys.app.controller;

//import java.awt.PageAttributes.MediaType;
import java.io.IOException;
//import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.foodys.app.models.UploadFileResponse;
import com.foodys.app.service.FoodOrderService;
import com.foodys.app.service.FileStorageService;




	@RestController
	public class FileController {

	    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	    @Autowired
	    private FileStorageService fileStorageService;
	    
	    @Autowired
	    private FoodOrderService foodOrderService;
	    

	    @PostMapping("/uploadFile")
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("id") long cid) {
	        
	    	
	    	String fileName = fileStorageService.storeFile(file);
	        logger.info("Food Order Id:"+cid);
	        
	       foodOrderService.updateFoodOrderWithFilename(fileName, cid);
	        
	        
	        
//	        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//	                .path("/downloadFile/")
//	                .path(fileName)
//	                .toUriString();
	       String fileDownloadUri = fileName;
	       
	        return new UploadFileResponse(fileName, fileDownloadUri,
	                file.getContentType(), file.getSize());
	    }

//	    @PostMapping("/uploadMultipleFiles")
//	    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//	        return Arrays.asList(files)
//	                .stream()
//	                .map(file -> uploadFile(file))
//	                .collect(Collectors.toList());
//	    }

	    @GetMapping("/downloadFile/{fileName:.+}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
//	            logger.info("File location:{}",resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            logger.info("Could not determine file type.");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }

	}



