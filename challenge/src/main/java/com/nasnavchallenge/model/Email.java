package com.nasnavchallenge.model;

import javax.persistence.Embeddable;

@Embeddable
public class Email {

	private String email;

	public Email() {
		super();
	}

	public Email(String address) {
		super();
		this.email = address;
	}

	@Override
	public String toString() {
		return "Email [address=" + email + "]";
	}

	public String getAddress() {
		return email;
	}

	public void setAddress(String address) {
		this.email = address;
	}

	public void sendEmail() {
	}

}
