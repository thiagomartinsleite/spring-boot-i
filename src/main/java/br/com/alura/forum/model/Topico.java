package br.com.alura.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
public class Topico {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao = LocalDateTime.now();
	@Enumerated(EnumType.STRING) // Serve para Gravar o conteudo
	private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
	@ManyToOne // Relacionamento de Muitos para um
	private Usuario autor;
	@ManyToOne
	private Curso curso;
	@OneToMany(mappedBy="topico") //Como a classe já faz o relacionametno, precisa colocar o MappedBy
	private List<Resposta> respostas = new ArrayList<Resposta>();
	
	public Topico(String titulo, String mensagem, Curso curso){
		super();
		this.titulo = titulo;
		this.mensagem = mensagem;
		this.curso = curso;
	}

}
