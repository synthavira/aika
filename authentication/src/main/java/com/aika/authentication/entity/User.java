package com.aika.authentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

import java.util.Set;

@Entity
@Table(name = "user_table", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
        })
public class User {
	
	@Id
	@Column(nullable = false)
	private long user_id;
	
	@Column(nullable = false)
	private long organisation_id;
	
	@Column(nullable = false)
	private String first_name;
	
	@Column(nullable = false)
	private String last_name;
	
	@Column(nullable = false)
	private long contact;
	
		
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private LocalDateTime created_at;
	
	@Column(nullable = false)
	private String created_by;
	
	@Column(nullable = false) 
	private String email;
	
	  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	    @JoinTable(name = "user_roles",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	    private Set<Role> roles;

    public UserSession getSession() {
		return session;
	}

	public void setSession(UserSession session) {
		this.session = session;
	}

	@OneToOne
    private UserSession session;

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	

	public long getOrganisation_id() {
		return organisation_id;
	}

	public void setOrganisation_id(long organisation_id) {
		this.organisation_id = organisation_id;
	}

	public long getContact() {
		return contact;
	}

	public void setContact(long contact) {
		this.contact = contact;
	}

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

}
