package com.nasnavchallenge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "user")

public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private long id;
	
	@Version
	@Column(name = "version")
	private int version;
	
	@Column(name = "user_Name", length = 64, unique = true)
	private String userName;
	
	@Column(name = "password")
	private String passWord;
	@Embedded
	@Column(name = "email")
	private Email email;
	@Column(name="role")
	private String role ;
	public AppUser() {

	}

	public AppUser( String userName, String passWord,Email email) {
		super();
		
		this.userName = userName;
		this.passWord = passWord;
		this.email = email;
		
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", userName=" + userName + ", passWord=" + passWord + "]";
	}

	public Email getEmail() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public long getId() {
		return id;
	}

	

	

	
}
