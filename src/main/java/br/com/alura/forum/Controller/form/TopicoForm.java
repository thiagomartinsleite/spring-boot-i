package br.com.alura.forum.Controller.form;



import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import lombok.Data;

@Data
public class TopicoForm {
	
	@NotNull @NotEmpty @Length(min = 5)// Anotação de Validação - bean Validation
	private String titulo;
	@NotNull @NotEmpty
	private String mensagem;
	@NotNull @NotEmpty
	private String nomeCurso;
	
	public Topico converter(CursoRepository repository) {
		
		Curso curso = repository.findByNome(this.nomeCurso);
		return new Topico(titulo,mensagem,curso);
		
	}

}
