package br.com.alura.forum.Controller.form;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lombok.Data;

@Data
public class LoginForm {

	private String email;
	private String senha;
	
	public UsernamePasswordAuthenticationToken converter() {
		// TODO Auto-generated method stub
		return new UsernamePasswordAuthenticationToken(email, senha);
	}
}
