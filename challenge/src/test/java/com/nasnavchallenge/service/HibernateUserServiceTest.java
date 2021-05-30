package com.nasnavchallenge.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.nasnavchallenge.model.Email;
import com.nasnavchallenge.model.AppUser;

public class HibernateUserServiceTest {
	private static Logger logger =
			LoggerFactory.getLogger(HibernateUserServiceTest.class);

	private ClassPathXmlApplicationContext ctx;
	
	private UserService hibernateUserService;
	@Before
	public void setUp(){
	ctx = new ClassPathXmlApplicationContext("root-context.xml") ;
	
	hibernateUserService = (UserService)ctx.getBean(UserService.class);
	assertNotNull(hibernateUserService);
	}
	@Test
	
	public void testSaveUser(){
	AppUser user = new AppUser();
	user.setUserName("taha");
	user.setPassWord("tenme");
	user.setEmail(new Email("mtzmind1@gmail.com"));
	hibernateUserService.saveUser(user);
	assertNotNull(user.getId());
	List<AppUser> users = hibernateUserService.getAllUsers();
	assertEquals(1, users.size());
	listUsers(users);
	}
	@Test
	public void testFindByID(){
		testSaveUser();
	AppUser user = hibernateUserService.findByID(1L);
	assertNotNull(user);
	logger.info(user.toString());
	} 
	@Test
	public void testGetAllUsers(){
		testSaveUser();
	List<AppUser> users = hibernateUserService.getAllUsers();
	assertEquals(1, users.size());
	listUsers(users);
	}
	
	@Test public void testUpdate() {
		testSaveUser();
	    long id = 1l;
	    AppUser beforeUpdatedUSer = hibernateUserService.findByID(id);
        beforeUpdatedUSer.setUserName("hamza");	     
	    
	    hibernateUserService.updateUser(beforeUpdatedUSer);
	   
	     
	    AppUser updatedUser = hibernateUserService.findByID( id);
	     
	    assertEquals("hamza", updatedUser.getUserName());
	}
	@Test
	public void testDelete() {
		testSaveUser();
	    
	    long id = 1L;
	    AppUser user = hibernateUserService.findByID(id);
	     
	    
	    hibernateUserService.deleteUser(user);
	    
	     
	    AppUser deletedUser= hibernateUserService.findByID( id);
	     
	    assertNull(deletedUser);
	}
	private static void listUsers(List<AppUser> users) {
		logger.info(" ---- Listing users :");
		for (AppUser user : users) {
		logger.info(user.toString());
		}
		}
	@After
	public void tearDown(){
	ctx.close();
	}

}
