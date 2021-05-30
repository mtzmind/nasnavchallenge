package com.nasnavchallenge.service;

import java.util.List;

import com.nasnavchallenge.model.AppUser;



public interface UserService {
	AppUser findByID(long id);
	AppUser saveUser( AppUser user) ;
	void deleteUser(AppUser user) ;
	 List<AppUser> getAllUsers() ;
	 AppUser updateUser(AppUser user) ;
}
