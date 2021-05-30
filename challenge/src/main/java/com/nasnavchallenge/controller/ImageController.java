package com.nasnavchallenge.controller;

import java.io.File;
import java.util.List;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import com.nasnavchallenge.model.Image;
import com.nasnavchallenge.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags="this is the image controller")
@RestController
@RequestMapping("api/image")
public class ImageController implements ServletContextAware {
	private static final String IMAGE_DIR = "resources\\images\\";
	@Autowired
	@Qualifier("hibernateImageService")
	private  ImageService imageService;
	@Autowired
	@Qualifier("multipartResolve")
	private MultipartResolver multipartResolve;
	 private ServletContext context;
	 private HttpHeaders httpHeaders= new HttpHeaders();
	@ApiOperation(value="upload an image")
	@SuppressWarnings("null")
	@PostMapping(value = "upload")
	@ResponseBody
	public ResponseEntity<Image> upload(@ApiParam(value="file",required = true)@RequestParam("file") MultipartFile file,@ApiParam(value="description",required = true) @RequestParam("description") String description) {
		 httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		if (file != null || !file.isEmpty()) {
			 if(file.getOriginalFilename().endsWith("jpg")
			 ||file.getOriginalFilename().endsWith("png")||file.getOriginalFilename().endsWith("gif"))
			 {
				 String newFileName = context.getRealPath("/") + IMAGE_DIR + file.getOriginalFilename();
			
			try {
				file.transferTo(new File(newFileName));
			} catch (Exception e) {
            
				 
					return  new ResponseEntity<Image>( null,httpHeaders,HttpStatus.INTERNAL_SERVER_ERROR );
			}
			Image uploadedImageByTheUser = new Image(description, newFileName, file.getSize());
			this.imageService.saveImage(uploadedImageByTheUser);

			return  new ResponseEntity<Image>( uploadedImageByTheUser,httpHeaders,HttpStatus.CREATED );
		}
			 else { return  new ResponseEntity<Image>( null,httpHeaders,HttpStatus.NOT_ACCEPTABLE );}
			 }
		return null;
	}
	
	@ApiOperation(value="returns the list of the unprocessed images by the admin")
	@GetMapping(value = "unprocessedimages")
	@ResponseBody
	public ResponseEntity<List<Image>> getUnprocessedImages() {
		 httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		 List<Image> unprocessedImages=this.imageService.getUnprocessedImages();;
		 return  new ResponseEntity<List<Image>>( unprocessedImages,httpHeaders,HttpStatus.OK );
		 
	}
	
	@ApiOperation(value="returns the list of the approved images by the admin")
	@GetMapping(value = "approvedimages")
	@ResponseBody
	public ResponseEntity<List<Image>> getApprovedImages() {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		List<Image> approvedImages= this.imageService.getApprovedImages();
		return  new ResponseEntity<List<Image>>( approvedImages,httpHeaders,HttpStatus.OK );
	}
	@ApiOperation(value="returns the list of the rejected images by the admin")
	@GetMapping(value = "rejectededimages")
	@ResponseBody
	public ResponseEntity<List<Image>>  geRejectededImages() {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		List<Image> rejectedImages= this.imageService.getRejectedImages();
		return  new ResponseEntity<List<Image>>( rejectedImages,httpHeaders,HttpStatus.OK );
	}
	@ApiOperation(value="approve the image by id")
	@PutMapping(value = "approve/{id}")
	@ResponseBody
	public ResponseEntity<Image> approveImage(@ApiParam(value="id",required = true)@PathVariable("id") long id) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Image approvedImagebyTheAmdin = this.imageService.getImageById(id);

		approvedImagebyTheAmdin.setProcessed(true);
		approvedImagebyTheAmdin.setApproved(true);
		this.imageService.approveImage(approvedImagebyTheAmdin);
		return  new ResponseEntity<Image>( approvedImagebyTheAmdin,httpHeaders,HttpStatus.ACCEPTED );
	}
	@ApiOperation(value="reject the image by id")
	@PutMapping(value = "reject/{id}")
	@ResponseBody
	public ResponseEntity<Image> rejectImage(@ApiParam(value="id",required = true)@PathVariable("id") long id) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Image rejectedImagebyTheAmdin = this.imageService.getImageById(id);

		rejectedImagebyTheAmdin.setProcessed(true);
		rejectedImagebyTheAmdin.setApproved(false);
		new File(rejectedImagebyTheAmdin.getLocation()).delete();
		this.imageService.approveImage(rejectedImagebyTheAmdin);
		return  new ResponseEntity<Image>( rejectedImagebyTheAmdin,httpHeaders,HttpStatus.ACCEPTED );
	}

	@Override
	public void setServletContext(ServletContext servletContext) {

		this.context = servletContext;

	}
}