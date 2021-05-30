package com.nasnavchallenge.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nasnavchallenge.dao.UserDAO;
import com.nasnavchallenge.model.AppUser;
@Service("hibernateUserService")
@Transactional
public class HibernateUserSerivce implements UserService {
	private UserDAO userDAO ;
	
	public UserDAO getHibernateUserDAO() {
		return this.userDAO;
	}

@Resource	
	public void setHibernateUserDAO(UserDAO userDAO) {
		this.userDAO =userDAO;
	}
	
	@Transactional(readOnly=true)
	public AppUser findByID(long id) {return this.userDAO.findByID(id);}
	public AppUser saveUser( AppUser user) {return(this.userDAO.saveUser(user));} 
	public void deleteUser(AppUser user) {this.userDAO.deleteUser(user);} 
	@Transactional(readOnly=true)
	public List<AppUser> getAllUsers(){return this.userDAO.getAllUsers() ;} 
	public AppUser updateUser(AppUser user) {return(this.userDAO.updateUser(user));}
	
}
