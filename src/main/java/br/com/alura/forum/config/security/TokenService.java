package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${forum.jwt.expiration}")
	private String expiration;

	@Value("${forum.jwt.secret}")
	private String secret;

	public String gerarToken(Authentication authentication) {
		Usuario logado = (Usuario) authentication.getPrincipal(); // pega o usuário logado
		Date hoje = new Date(); // data de geração do token
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration)); // calcula data de expiração do
																					// token

		return Jwts.builder().setIssuer("API do forum da Alura") // quem ta gerando o token
				.setSubject(logado.getId().toString()) // usuário de geração do token
				.setIssuedAt(hoje) // qual foi a data de geração do token
				.setExpiration(dataExpiracao) // quando expira o token
				.signWith(SignatureAlgorithm.HS256, secret) // criptografia do secret
				.compact(); // compacta tudo
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(this.secret) // descriptografar
					.parseClaimsJws(token); // objeto para recuperar o token
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public Long getIdUsuario(String token) {
		try {
			Claims body = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
			return Long.parseLong(body.getSubject());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
