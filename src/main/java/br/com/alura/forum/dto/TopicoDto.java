package br.com.alura.forum.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.model.Topico;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class TopicoDto {
	
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCricao;
	
	public TopicoDto(Topico topico){
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCricao = topico.getDataCriacao();
	}

	public static Page<TopicoDto> converter(Page<Topico> topicos) {
		// Apartir do java 8, faz o Stream do pasta lista 
		return topicos.map(TopicoDto::new);
		//topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}
	
}
