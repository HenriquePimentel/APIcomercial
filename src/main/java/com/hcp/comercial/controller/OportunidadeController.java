package com.hcp.comercial.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hcp.comercial.model.Oportunidade;
import com.hcp.comercial.repository.OportunidadeRepository;

/**
 * Esta clase é reponsável por tratar as requisições da chamada GET/POST
 * 
 * chamada GET http://localhost:8080/oportunidades
 * 
 * @author Henrique
 */

//CROS
@CrossOrigin
@RestController
@RequestMapping("/oportunidades")
public class OportunidadeController {

	@Autowired
	public OportunidadeRepository oportunidades;
	
	@GetMapping
	public List<Oportunidade> listar() {
		/**Oportunidade oportunidade = new Oportunidade();
		oportunidade.setId(1L);
		oportunidade.setNomeProspeccao("Desenvolvimento de ERP com Angular e Spring");
		oportunidade.setDescricaoOportunidade("Empresa Aleatória");
		oportunidade.setValor(new BigDecimal(480000));
		// utilizando a classe Arrays ela já permite criar uma lista com o objeto do exemplo
		return Arrays.asList(oportunidade); */
		return oportunidades.findAll();
	}
	
	// placeholder - algo que espera uma variavel
	@GetMapping("/{id}")
	public ResponseEntity<Oportunidade> buscar(@PathVariable Long id) {
		// encapsula a oportunidade e pode ter valor ou ser null
		Optional<Oportunidade> oportunidade = oportunidades.findById(id); 
		
		if (oportunidade.isEmpty()) {
			ResponseEntity.notFound().build();
		}
		
		// método get vai pegar o objeto encapsulado no Optional
		return ResponseEntity.ok(oportunidade.get());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	// a anotação @Valid força que o objeto json seja validado antes de transformar no objeto informado
	// a anotação @RequestBody converte o json no tipo do objeto informado
	public Oportunidade adicionar(@Valid @RequestBody Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades.findByNomeProspeccaoAndDescricao(oportunidade.getNomeProspeccao(), oportunidade.getDescricao());
		
		if (oportunidadeExistente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Oportunidade já cadastrada");
		}
		
		return oportunidades.save(oportunidade);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Oportunidade atualizar(Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades.findById(oportunidade.getId());
		
		if (oportunidadeExistente.isPresent()) {
			return oportunidades.save(oportunidade);
		} else {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Oportunidade não existe! Não é possível atualizar!");
		}
		
	}
	
	@DeleteMapping
//	@ResponseStatus(HttpStatus.ACCEPTED)
	public List<Oportunidade> deletar(Oportunidade oportunidade) {
		Optional<Oportunidade> oportunidadeExistente = oportunidades.findById(oportunidade.getId()); 
		
		if (oportunidadeExistente.isPresent()) {
			oportunidades.delete(oportunidade);
			return oportunidades.findAll();
		} else {
			throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Oportunidade não existe! Não é possível excluir!");
		}
	}
	
}
