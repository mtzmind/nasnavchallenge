package com.nasnavchallenge.dao;

import java.util.List;

import com.nasnavchallenge.model.AppUser;

public interface UserDAO {
	AppUser findByID(long id);
	 AppUser saveUser( AppUser user) ;
	 void deleteUser(AppUser user) ;
	 List<AppUser> getAllUsers() ;
	 AppUser updateUser(AppUser user) ;
	 AppUser findByUsername(String username);
}
