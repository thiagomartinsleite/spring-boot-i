package br.com.alura.forum.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.model.Resposta;
import lombok.Data;

@Data
public class RespostaDto {
	
	private Long id;
	private String mensagem;
	private LocalDateTime dataCriacao;
	private String nomeAutor;

	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
		this.nomeAutor = resposta.getAutor().getNome();
	}
}
