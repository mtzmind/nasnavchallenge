package com.nasnavchallenge.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import com.nasnavchallenge.model.Image;
@Transactional
@Repository("hibernateImageDAO")
public class HipernateImageDAO implements ImageDAO {
	private static final String  GETAPPROVEDIMAGES="from Image as image where image.approved=true";
	private static final String  GETREJECTEDIMAGES="from Image as image where image.approved=false and image.processed=true";
	private static final String  GETRUNPROCESSEDIMAGES="from Image as image where image.processed=false ";
	
 private SessionFactory sessionFacotry ;	
 
public SessionFactory getSessionFacotry() {
	return sessionFacotry;
}
@Resource(name="sessionFactory")
public void setSessionFacotry(SessionFactory sessionFacotry) {
	this.sessionFacotry = sessionFacotry;
}
public void saveImage(Image uploadedImageByTheUser) {this.sessionFacotry.getCurrentSession().save(uploadedImageByTheUser);}
public void rejectImage(Image rejectedImageByTheAdmin) {this.sessionFacotry.getCurrentSession().saveOrUpdate(rejectedImageByTheAdmin);}
public void approveImage(Image aprovedImagebyTheAmdin) {this.sessionFacotry.getCurrentSession().saveOrUpdate(aprovedImagebyTheAmdin);}
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public List<Image> getApprovedImages() { return this.getSessionFacotry().getCurrentSession().createQuery(GETAPPROVEDIMAGES).list() ;}
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public List<Image> getUnprocessedImages() {return this.getSessionFacotry().getCurrentSession().createQuery(GETRUNPROCESSEDIMAGES).list() ;}
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public List<Image> getRejectedImages() {return this.getSessionFacotry().getCurrentSession().createQuery(GETREJECTEDIMAGES).list() ;}

@Transactional(readOnly = true)
public Image getByID(long id) {
	return this.getSessionFacotry().getCurrentSession().get(Image.class, id);
}

}
