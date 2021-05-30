package com.nasnavchallenge.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "images")
public class Image implements Serializable {
	static final private long serialVersionUID = 3L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "image_id")
    private long id;
	
	
	

	public long getId() {
		return id;
	}

	@Version
	@Column(name = "version")
	private int version;
	
	@Column(name = "img_desc")
	private String description;

	@Column(name = "location")
	private String location;

	@Column(name = "image_size")
	private long size;

	@Column(name = "approved")
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean approved;

	@Column(name = "processed")
	@org.hibernate.annotations.Type(type = "yes_no")
	private boolean processed;

	public Image() {
		super();

	}

	public Image(String description, String location, long size) {
		super();

		this.description = description;
		this.location = location;
		this.size = size;

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String url) {
		this.location = url;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	@Override
	public String toString() {
		return "Image [description=" + description + ", size=" + size + ", approved=" + approved +", processed=" + processed + "]";
	}

}
