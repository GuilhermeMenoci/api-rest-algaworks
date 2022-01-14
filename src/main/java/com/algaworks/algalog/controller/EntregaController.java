package com.algaworks.algalog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.config.EntregaMapper;
import com.algaworks.algalog.dto.EntregaDTO;
import com.algaworks.algalog.dto.EntregaInputDTO;
import com.algaworks.algalog.model.Entrega;
import com.algaworks.algalog.repository.EntregaRepository;
import com.algaworks.algalog.service.SolicitacaoEntregaService;

@RestController
@RequestMapping("/entregas")
public class EntregaController {
	
	@Autowired
	private EntregaRepository entregaRepository;

	@Autowired
	private SolicitacaoEntregaService solicitacaoService;
	
	@Autowired
	private EntregaMapper entregaMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EntregaDTO solicitarEntrega(@Valid @RequestBody EntregaInputDTO entrega) {
		Entrega novaEntrega = entregaMapper.toEntity(entrega);
		Entrega entregaSolicitada = solicitacaoService.solicitarEntrega(novaEntrega); 
		return entregaMapper.toModel(entregaSolicitada);
	}
	
	@GetMapping
	public List<EntregaDTO> listar(){
		return entregaMapper.toCollectionDTO(entregaRepository.findAll());
	}
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaDTO> buscar(@PathVariable Long entregaId){
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaMapper.toModel(entrega)))
				.orElse(ResponseEntity.notFound().build());
	}
	
}
