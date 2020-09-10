package br.com.alura.forum.config.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

//Service para autentica��o
@Service
public class AutenticacaoService implements UserDetailsService{ //Faz a implementa��o da interface

	@Autowired
	UsuarioRepository repository;
	
	@Override //Implementa��o da interface de valida��o de login
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<Usuario> user = repository.findByEmail(email); //Recebe da repository o optional para tratar possiveis erros de dados n�o encontrados
		
		if (user.isPresent()){
			return user.get();
		}
		
		throw new UsernameNotFoundException("Dados Inv�lidos");
	}

}
