package com.nasnavchallenge.dao;

import java.util.List;

import com.nasnavchallenge.model.Image;

public interface ImageDAO {
	public void saveImage(Image uploadedImageByTheUser) ;
	public void rejectImage(Image rejectedImageByTheAdmin) ;
	public void approveImage(Image aprovedImagebyTheAmdin) ;
	public List<Image> getApprovedImages() ;
	public List<Image> getUnprocessedImages() ;
	public List<Image>getRejectedImages();
	public Image getByID(long id);
}
