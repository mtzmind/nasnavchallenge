package com.nasnavchallenge.dao;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import com.nasnavchallenge.model.Image;

public class HibernateImageDAOTest {
	private static Logger logger = LoggerFactory.getLogger(HibernateImageDAOTest.class);

	private ClassPathXmlApplicationContext ctx;

	private ImageDAO hibernateImageDAO;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("root-context.xml");

		hibernateImageDAO = (ImageDAO) ctx.getBean(ImageDAO.class);
		assertNotNull(hibernateImageDAO);
	}

	@Test
	public void testSaveImage() {
		Image imageToBeSaved = new Image();
		imageToBeSaved.setLocation("home");
		imageToBeSaved.setDescription("this is a test image");
		hibernateImageDAO.saveImage(imageToBeSaved);
		assertNotNull(imageToBeSaved.getId());
		
		
	}

	@Test
	public void testrejectImage() {
		Image imageToBeRejected = new Image();
		imageToBeRejected.setLocation("home");
		imageToBeRejected.setDescription("this is a test image");
		imageToBeRejected.setProcessed(true);
		imageToBeRejected.setApproved(false);
		hibernateImageDAO.rejectImage(imageToBeRejected);
		Image rejectedImage = hibernateImageDAO.getByID(1L);
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
		hibernateImageDAO.saveImage(imageToBeApproved);
		Image approvedImage = hibernateImageDAO.getByID(1L);
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
		hibernateImageDAO.approveImage(imageToBeApproved);
		List<Image> approvedImages = hibernateImageDAO.getApprovedImages();
		assertEquals(1, approvedImages.size());
		listImages(approvedImages);
	}
	
	@Test
	public void testGetUnprocessedImages() {
		Image unprocessedImage = new Image();
		unprocessedImage.setLocation("home");
		unprocessedImage.setDescription("this is a test image");
		unprocessedImage.setProcessed(false);
		hibernateImageDAO.saveImage(unprocessedImage);
		List<Image> unprocessedImages = hibernateImageDAO.getUnprocessedImages();
		assertEquals(1, unprocessedImages.size());
		listImages(unprocessedImages);
	}
	@Test
	public void testGetRejectedImages() {
		Image imageToBeRejected = new Image();
		imageToBeRejected.setLocation("home");
		imageToBeRejected.setDescription("this is a test image");
		imageToBeRejected.setProcessed(true);
		imageToBeRejected.setApproved(false);
		hibernateImageDAO.rejectImage(imageToBeRejected);
		List<Image> rejectedImages = hibernateImageDAO.getRejectedImages();
		assertEquals(1, rejectedImages.size());
		listImages(rejectedImages);
	}
	@Test
	public void testFindByID() {
		testSaveImage();
		Image image = hibernateImageDAO.getByID(1L);
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
