package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // Anotação para habilitar a segurança do spring
@Configuration // Com nessa anotação o spring passa a entender que tem que carregar ela no start da aplicação
public class SecurityConfigurations extends WebSecurityConfigurerAdapter { // precisa extender a classe WebSecurityConfigurerAdapter


	@Autowired
	private AutenticacaoService AutenticacaoService;
	
    //Configuração de autenticação
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	//Encripta a mensagem
    	auth.userDetailsService(AutenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuração de autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests() // Adiciona quais requests serão autorizados
            .antMatchers(HttpMethod.GET, "/topicos").permitAll() // Define as permissões // HttpMethod.GET define o verbos de acesso a api que tera acesso
            .antMatchers(HttpMethod.GET, "/topicos/*").permitAll() // Define as permissões // HttpMethod.GET define o metodo que tera acesso
        	.anyRequest().authenticated() // Configura a security
        	.and().formLogin(); //Cria form de login, pelo spring
    }

    //Configuração de recursos estatitcos (css,javascript, imagens e etc)
    @Override
    public void configure(WebSecurity web) {

    }
    /* gerar o hash da senha
    public static void main(String[] args){
    	System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
    */
}
