package br.com.alura.forum.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.StatusTopico;
import br.com.alura.forum.model.Topico;
import lombok.Data;

@Data
public class DetalhesDoTopicoDto {

	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCricao;
	private String nomeAutor;
	private StatusTopico status;
	private List<RespostaDto> respostas;
	
	public DetalhesDoTopicoDto(Topico topico){
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCricao = topico.getDataCriacao();
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<RespostaDto>();
		this.respostas.addAll(topico.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
	}

	public static List<TopicoDto> converter(List<Topico> topicos) {
		// Apartir do java 8, faz o Stream do pasta lista 
		return topicos.stream().map(TopicoDto::new).collect(Collectors.toList());
	}
}
