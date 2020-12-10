package br.com.alura.forum.Controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.Controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.Controller.form.TopicoForm;
import br.com.alura.forum.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RequestMapping("/topicos")
@RestController
public class TopicosController {

	@Autowired
	TopicoRepository topicoRepository;

	@Autowired
	CursoRepository cursoRepository;

	@GetMapping // Verbos do hhtp method=RequestMethod.GET
	@Cacheable(value = "listaDeTopicos") // Notação para poder fazer cache do metodo, o Value, seria o nome para o cache
	public Page<TopicoDto> lista(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(sort = "id", direction = Sort.Direction.DESC, page = 0, size = 10) Pageable paginacao) { // Paginação
																														// simples
																														// /
																														// PageableDefault
																														// define
																														// a
																														// ordenação
																														// default
		// @RequestParam int pagina, @RequestParam int qtd, @RequestParam String
		// ordenacao){ //Parametro de request @RequestParam

		// Para fazer Paginação //Sentido da ordenacao
		// Pageable paginacao = PageRequest.of(pagina, qtd, Sort.Direction.DESC,
		// ordenacao); //ordenacao é o campo que ordena

		if (nomeCurso == null) {
			Page<Topico> listaTopico = topicoRepository.findAll(paginacao);
			return TopicoDto.converter(listaTopico);
		} else {
			Page<Topico> listaTopico = topicoRepository.findByCursoNome(nomeCurso, paginacao);
			return TopicoDto.converter(listaTopico);
		}

	}

	@PostMapping // Verbos do hhtp method=RequestMethod.POST, trocado pela notação @Valida, usado
					// para aplicar a validação do Bean Validation
	@Transactional // Indica que o spring precisa commitar no final
	@CacheEvict(value = "listaDeTopicos", allEntries = true) // Fazer a limpeza do cache, quando houver um cadastro
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm, UriComponentsBuilder uriBuilder) { // Notação para pegar o body da requisição
		// ResponseEntity fazendo a resposta da mensagem //Criação da uri automatico
		
		Topico topico = topicoForm.converter(cursoRepository);
		topicoRepository.save(topico);
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri(); // Gerando a uri dinamica
		return ResponseEntity.created(uri).body(new TopicoDto(topico)); // o retorno com o Body
	}

	@GetMapping("/{id}") // Os valores entre {} são dinamicos, Mapeamento indica que a classe recebe o id
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {

		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}") // path para pequena atualização, put para atualizar tudo
	@Transactional // Indica que o spring precisa commitar no final
	@CacheEvict(value = "listaDeTopicos", allEntries = true) // Fazer a limpeza do cache, quando houver um cadastro
																// novo, allEntries = todos
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico)); // retorna a mensagem ok, e o que está sendo atualizado
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/{id}") //
	@Transactional // Indica que o spring precisa commitar no final
	@CacheEvict(value = "listaDeTopicos", allEntries = true) // Fazer a limpeza do cache, quando houver um cadastro
																// novo, allEntries = todos
	public ResponseEntity<?> remover(@PathVariable Long id) {

		Optional<Topico> optional = topicoRepository.findById(id);
		if (optional.isPresent()) {
			topicoRepository.deleteById(id); // Delete by id remove do banco
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
