package com.nasnavchallenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import com.nasnavchallenge.model.Image;

public class HibernateImageServiceTest {
	private static Logger logger = LoggerFactory.getLogger(HibernateImageServiceTest.class);

	private ClassPathXmlApplicationContext ctx;

	private ImageService hibernateImageService;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("root-context.xml");

		hibernateImageService = (ImageService) ctx.getBean(ImageService.class);
		assertNotNull(hibernateImageService);
	}

	@Test
	public void testSaveImage() {
		Image image = new Image();
		image.setLocation("home");
		image.setDescription("this is a test image");
		hibernateImageService.saveImage(image);
		assertNotNull(image.getId());
		
	}

	@Test
	public void testrejectImage() {
		Image imageToBeRejected = new Image();
		imageToBeRejected.setLocation("home");
		imageToBeRejected.setDescription("this is a test image");
		imageToBeRejected.setProcessed(true);
		imageToBeRejected.setApproved(false);
		hibernateImageService.rejectImage(imageToBeRejected);
		Image rejectedImage = hibernateImageService.getImageById(1L);
		assertTrue(rejectedImage.isProcessed());
		assertFalse(rejectedImage.isApproved());
		
	}

	@Test
	public void testApproveImage() {
		Image imageToBeApproved = new Image();
		imageToBeApproved.setLocation("home");
		imageToBeApproved.setDescription("this is a test image");
		imageToBeApproved.setProcessed(true);
		imageToBeApproved.setApproved(true);
		hibernateImageService.approveImage(imageToBeApproved);
		Image approvedImage = hibernateImageService.getImageById(1L);
		assertTrue(approvedImage.isProcessed());
		assertTrue(approvedImage.isApproved());
		
	}

	@Test
	public void testGetApprovedImage() {
		Image imageToBeApproved = new Image();
		imageToBeApproved.setLocation("home");
		imageToBeApproved.setDescription("this is a test image");
		imageToBeApproved.setProcessed(true);
		imageToBeApproved.setApproved(true);
		hibernateImageService.approveImage(imageToBeApproved);
		
		List<Image> images = hibernateImageService.getApprovedImages();
		assertEquals(1, images.size());
		listImages(images);
	}
	
	@Test
	public void testGetUnprocessedImages() {
		Image unprocessedImage = new Image();
		unprocessedImage.setLocation("home");
		unprocessedImage.setDescription("this is a test image");
		unprocessedImage.setProcessed(false);
		hibernateImageService.approveImage(unprocessedImage);
		
		List<Image> images = hibernateImageService.getUnprocessedImages();
		assertEquals(1, images.size());
		listImages(images);
	}
	@Test
	public void testGetRejectedImages() {
		Image imageToBeApproved = new Image();
		imageToBeApproved.setLocation("home");
		imageToBeApproved.setDescription("this is a test image");
		imageToBeApproved.setProcessed(true);
		imageToBeApproved.setApproved(false);
		hibernateImageService.rejectImage(imageToBeApproved);
		
		List<Image> images = hibernateImageService.getRejectedImages();
		assertEquals(1, images.size());
		listImages(images);
	}
	@Test
	public void testFindByID() {
		testSaveImage();
		Image image = hibernateImageService.getImageById(1L);
		assertNotNull(image);
		logger.info(image.toString());
	}

	

	private static void listImages(List<Image> images) {
		logger.info(" ---- Listing images :");
		for (Image image : images) {
			logger.info(image.toString());
		}
	}

	@After
	public void tearDown() {
		ctx.close();
	}

}
