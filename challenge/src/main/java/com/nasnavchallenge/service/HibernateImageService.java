package com.nasnavchallenge.service;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;

import com.nasnavchallenge.dao.ImageDAO;
import com.nasnavchallenge.model.Image;

import org.springframework.stereotype.Service;



@Service("hibernateImageService")
@Transactional
public class HibernateImageService implements ImageService {
   private ImageDAO imageDAO ;
   
	public ImageDAO getImageDAO() {
	return imageDAO;
}
	@Resource
public void setImageDAO(ImageDAO imageDAO) {
	this.imageDAO = imageDAO;
}

	@Override
	public void saveImage(Image uploadedImageByTheUser) {
		this.imageDAO.saveImage(uploadedImageByTheUser);
		
	}

	@Override
	public void rejectImage(Image rejectedImageByTheAdmin) {
		
		this.imageDAO.rejectImage(rejectedImageByTheAdmin);
	}

	@Override
	public void approveImage(Image aprovedImagebyTheAmdin) {
		this.imageDAO.approveImage(aprovedImagebyTheAmdin);
		
	}

	
	@Transactional(readOnly=true)
	public List<Image> getApprovedImages() {
		// TODO Auto-generated method stub
		return this.imageDAO.getApprovedImages();
	}

	
	@Transactional(readOnly=true)
	public List<Image> getUnprocessedImages() {
		// TODO Auto-generated method stub
		 return this.imageDAO.getUnprocessedImages();
	}

	@Transactional(readOnly=true)
	public List<Image> getRejectedImages() {
		// TODO Auto-generated method stub
		return this.imageDAO.getRejectedImages();
	}
	@Override
	public Image getImageById(long id) {
		return this.getImageDAO().getByID(id);
	}

}
