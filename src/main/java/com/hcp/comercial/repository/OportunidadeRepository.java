package com.hcp.comercial.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcp.comercial.model.Oportunidade;

public interface OportunidadeRepository extends JpaRepository<Oportunidade, Long> { 

	/**
	 * Existe uma padronização no Spring JPA no prefixo findBy, portanto basta
	 * adicionar o nome das colunas devem ser procuradas e não é necessário implementar mais nada
	 * 
	 * @param nomeProspeccao
	 * @param descricao
	 * @return
	 */
	public Optional<Oportunidade> findByNomeProspeccaoAndDescricao (String nomeProspeccao, String descricao);
	
}
