package com.nasnavchallenge.security;




import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.util.Arrays;
import com.nasnavchallenge.controller.UserController;
import com.nasnavchallenge.model.AppUser;
import com.nasnavchallenge.model.Email;
import com.nasnavchallenge.service.UserService;








@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/root-context.xml","/servlet-context.xml" ,"/security-context.xml"})

public class UserControllerTest {
	
	private MockMvc mvc ;
	@InjectMocks
	private UserController userController ;
	 @Mock
	private UserService  userService;
	
	 @Mock
     PasswordEncoder passwordEncoder ;
     
	@Before 
	public void init()  throws Exception{
		userController = new UserController();
		
		mvc=MockMvcBuilders.standaloneSetup(userController).build();
		MockitoAnnotations.initMocks(this);
		MockitoAnnotations.initMocks(userService);
		
		
	    
	}
	@Test
	
	public void testCreateNewUser() throws Exception{
		when(passwordEncoder.encode("empty")).thenReturn("empty");
		AppUser user = new AppUser();
		user.setUserName("taha");
		user.setPassWord("empty");
		user.setEmail(new Email("email"));
		when(userService.saveUser(user)).thenReturn(user);
		mvc.perform(MockMvcRequestBuilders.post("/api/user/create?username=taha&password=empty&email=mtzmind1@gmail.com" ).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.*",Matchers.hasSize(5)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("taha"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passWord").value("empty"));
		
		
	}
	
	@Test
	@WithMockUser()
	public void testFindUserByID() throws Exception{
		AppUser user = new AppUser( "taha", "empty",  new Email("email"));

		when(userService.findByID(1L)).thenReturn(user);
		mvc.perform(MockMvcRequestBuilders.get("/api/user/{id}",1L ).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.*",Matchers.hasSize(5)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("taha"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passWord").value("empty"));
		
		
		verify(userService,times(1)).findByID(1l);
		verifyNoMoreInteractions(userService);
				
	}
	@Test
	//@WithUserDetails("admin")
	public void testDeleteUser() throws Exception{
		AppUser user = new AppUser("taha", "empty",  new Email("mtzmind1@gmail.com"));
		when(userService.findByID(1L)).thenReturn(user);
		mvc.perform(MockMvcRequestBuilders.delete("/api/user/{id}",1L ).contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().string("deleted"));
		
		
		verify(userService,times(1)).findByID(1l);
		verify(userService,times(1)).deleteUser(user);
		verifyNoMoreInteractions(userService);
				
	}
	@Test
	public void testUpdateUser() throws Exception{
		AppUser user = new AppUser("taha", "empty",  new Email("mtzmind1@gmail.com"));
		when(userService.findByID(1L)).thenReturn(user);
		
		mvc.perform(MockMvcRequestBuilders.put("/api/user/1?username=taha&password=empty&email=Email[[address=mtzmind1@gmail.com]]")
	    .contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.*",Matchers.hasSize(5)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("taha"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.passWord").value("empty"));
		
		verify(userService,times(1)).findByID(1l);
		verify(userService,times(1)).updateUser(user);
		verifyNoMoreInteractions(userService);
	}
  @Test
	public void testFindAll() throws Exception {
		AppUser user = new AppUser("taha", "empty",  new Email("email"));
 	    when(userService.getAllUsers()).thenReturn(Arrays.asList(user));
 	   
		mvc.perform(MockMvcRequestBuilders.get("/api/user/getall").contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(print())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("$.*",Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].userName").value("taha"))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].passWord").value("empty"));
        
		verify(userService,times(1)).getAllUsers();
		verifyNoMoreInteractions(userService);
		
	}
}
