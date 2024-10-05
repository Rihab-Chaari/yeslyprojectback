package com.java.demo.springboot.payload.request;

import javax.validation.constraints.NotBlank;
public class LoginRequest {

	



		@NotBlank
	  private String username;

		@NotBlank
		private String password;

		public LoginRequest() {
			super();
			// TODO Auto-generated constructor stub
		}

		public LoginRequest(@NotBlank String username, @NotBlank String password) {
			super();
			this.username = username;
			this.password = password;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	
}
