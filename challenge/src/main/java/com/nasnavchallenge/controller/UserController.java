package com.nasnavchallenge.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.nasnavchallenge.model.Email;
import com.nasnavchallenge.model.AppUser;
import com.nasnavchallenge.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("api/user")
@Api(tags = "this is the user controller")
public class UserController {

	@Autowired
	 public UserService userService;
      @Autowired
      PasswordEncoder passwordEncoder ;
	@ApiOperation(value = "create a new user")
	@PostMapping(value = "create" )
	@ResponseBody
	public  ResponseEntity<AppUser> createNewUser(
			@ApiParam(value = "username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "email", required = true) @RequestParam("email") @javax.validation.constraints.Email String email) {
		
		AppUser user = new AppUser();
		user.setUserName(username);
		user.setPassWord(passwordEncoder.encode(password));
		user.setEmail(new Email("email"));
		if (username.toString().equals("admin") && password.toString().contentEquals("1234")) {
			user.setRole("ROLE_ADMIN");
		} else {
			user.setRole("ROLE_USER");
		}
		userService.saveUser(user);
		 HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
	    
		return  new ResponseEntity<AppUser>( user,httpHeaders,HttpStatus.OK );
	}

	@ApiOperation(value = "find user by ID")
	@GetMapping(value = "{id}" )
	@ResponseBody
	public ResponseEntity<AppUser> findUserByID(@ApiParam(value = "id", required = true) @PathVariable("id") long id) {
		 HttpHeaders httpHeaders= new HttpHeaders();
		    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return  new ResponseEntity<AppUser>( userService.findByID(id),httpHeaders,HttpStatus.OK);
		

	}

	
	@ApiOperation(value = "delete a user")
	@DeleteMapping(value = "{id}")
	@ResponseBody
	public ResponseEntity<String> delete(@ApiParam(value = "id", required = true) @PathVariable("id") long id) {

		
		AppUser user = userService.findByID(id);
		userService.deleteUser(user);
		HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return  new ResponseEntity<String>( "deleted",httpHeaders,HttpStatus.OK);
	}

	@ApiOperation(value = "update a user")
	@PutMapping(value = "{id}")
	@ResponseBody
	public ResponseEntity<AppUser> upadateUser(@ApiParam(value = "id", required = true) @PathVariable("id") long id,
			@ApiParam(value = "username", required = true) @RequestParam("username") String username,
			@ApiParam(value = "password", required = true) @RequestParam("password") String password,
			@ApiParam(value = "email", required = true) @RequestParam("email") String email) {
		AppUser user = userService.findByID(id);
		if (user != null) {
			if (username != null)
				user.setUserName(username);
			if (password != null)
				user.setPassWord(password);
			if (email != null)
				user.setEmail(new Email(email));
			
			 HttpHeaders httpHeaders= new HttpHeaders();
			    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			   userService.updateUser(user);
			return  new ResponseEntity<AppUser>( user,httpHeaders,HttpStatus.OK);
		}

		return null;
	}

	@ApiOperation(value = "show  all users")
	@GetMapping(value = "getall" )
	@ResponseBody
	public ResponseEntity<List<AppUser>> findAll() {
		 HttpHeaders httpHeaders= new HttpHeaders();
		    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<List<AppUser>>(userService.getAllUsers(), httpHeaders, HttpStatus.OK);

	}
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


}
