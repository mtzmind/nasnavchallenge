package com.nasnavchallenge.dao;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import static org.junit.Assert.*;
import com.nasnavchallenge.model.Email;
import com.nasnavchallenge.model.AppUser;

public class HibernateUserDAOTest {
	private static Logger logger = LoggerFactory.getLogger(HibernateUserDAOTest.class);

	private ClassPathXmlApplicationContext ctx;

	private UserDAO hibernateUserDAO;

	@Before
	public void setUp() {
		ctx = new ClassPathXmlApplicationContext("root-context.xml");

		hibernateUserDAO = (UserDAO) ctx.getBean(UserDAO.class);
		assertNotNull(hibernateUserDAO);
	}

	@Test
	public void testSaveUser() {
		AppUser user = new AppUser();
		user.setUserName("taha");
		user.setPassWord("tenme");
		user.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user);
		assertNotNull(user.getId());
		logger.info(user.toString());

	}

	@Test
	public void testSaveUserWith2users() {
		AppUser user1 = new AppUser();
		user1.setUserName("taha");
		user1.setPassWord("tenme");
		user1.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user1);
		AppUser user2 = new AppUser();
		user2.setUserName("hamza");
		user2.setPassWord("onlyme");
		user2.setEmail(new Email("hamza@gmail.com"));
		hibernateUserDAO.saveUser(user2);
		assertNotNull(user1.getId());
		assertNotNull(user2.getId());
		logger.info(user1.toString());
		logger.info(user2.toString());
	}

	@Test
	public void testFindByID() {
		AppUser user1 = new AppUser();
		user1.setUserName("taha");
		user1.setPassWord("tenme");
		user1.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user1);
		AppUser user = hibernateUserDAO.findByID(1L);
		assertNotNull(user);
		logger.info(user.toString());
	}

	@Test
	public void testGetAllUsers() {
		AppUser user1 = new AppUser();
		user1.setUserName("taha");
		user1.setPassWord("tenme");
		user1.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user1);
		AppUser user2 = new AppUser();
		user2.setUserName("hamza");
		user2.setPassWord("onlyme");
		user2.setEmail(new Email("hamza@gmail.com"));
		hibernateUserDAO.saveUser(user2);
		List<AppUser> users = hibernateUserDAO.getAllUsers();
		assertEquals(2, users.size());
		listUsers(users);
	}

	@Test
	public void testUpdate() {
		AppUser user1 = new AppUser();
		user1.setUserName("taha");
		user1.setPassWord("tenme");
		user1.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user1);

		AppUser beforeUpdatedUSer = hibernateUserDAO.findByID(1L);
		beforeUpdatedUSer.setUserName("hamza");

		hibernateUserDAO.updateUser(beforeUpdatedUSer);

		AppUser updatedUser = hibernateUserDAO.findByID(1L);

		assertEquals("hamza", updatedUser.getUserName());
	}

	@Test
	public void testDelete() {
		AppUser user = new AppUser();
		user.setUserName("taha");
		user.setPassWord("tenme");
		user.setEmail(new Email("mtzmind1@gmail.com"));
		hibernateUserDAO.saveUser(user);
		AppUser userToBeDelted = hibernateUserDAO.findByID(1l);

		hibernateUserDAO.deleteUser(userToBeDelted);

		AppUser deletedUser = hibernateUserDAO.findByID(1l);

		assertNull(deletedUser);
	}

	private static void listUsers(List<AppUser> users) {
		logger.info(" ---- Listing users :");
		for (AppUser user : users) {
			logger.info(user.toString());
		}
	}

	@After
	public void tearDown() {
		ctx.close();
	}
}
