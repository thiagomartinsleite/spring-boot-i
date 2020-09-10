package br.com.alura.forum.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.model.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	public Page<Topico> findByCursoNome(String nome, Pageable paginacao);
	
	@Query("SELECT t FROM Topico t where t.curso.nome = :nomeCurso")
	public List<Topico> CarregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
}
