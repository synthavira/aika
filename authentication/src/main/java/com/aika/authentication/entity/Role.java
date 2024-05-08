package com.aika.authentication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(nullable = false )
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	

}
