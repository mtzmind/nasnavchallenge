package com.nasnavchallenge.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.nasnavchallenge.dao.UserDAO ;
import com.nasnavchallenge.model.AppUser;
@Component("appUserDetailService")
public class AppUserDetailService implements UserDetailsService {

	@Autowired @Qualifier("hibernateUserDAO")
	private UserDAO hibernateUserDAO ;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 AppUser user=hibernateUserDAO.findByUsername(username);
		 
		 System.out.println(user.getRole());
		return new User (user.getUserName(),user.getPassWord(),AuthorityUtils.createAuthorityList(user.getRole()));
	}

}
