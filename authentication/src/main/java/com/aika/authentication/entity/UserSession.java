package com.aika.authentication.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_session")
public class UserSession {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
        
		@Column(nullable = false)
	    private String token;
	    
		@Column(nullable = false)
	    private LocalDateTime created_At;
	    
		@Column(nullable = false)
		private Long user_id;
		
		public Long getUser_id() {
			return user_id;
		}

		public void setUser_id(Long user_id) {
			this.user_id = user_id;
		}

		@Column(nullable = false)
	    public LocalDateTime getCreated_At() {
			return created_At;
		}

		public void setCreated_At(LocalDateTime created_At) {
			this.created_At = created_At;
		}

		@OneToOne(mappedBy = "session")
	    private User user;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	
}
