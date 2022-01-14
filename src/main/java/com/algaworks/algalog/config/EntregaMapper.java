package com.algaworks.algalog.config;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.dto.EntregaDTO;
import com.algaworks.algalog.dto.EntregaInputDTO;
import com.algaworks.algalog.model.Entrega;

@Component
public class EntregaMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	public EntregaDTO toModel(Entrega entrega) {
		return modelMapper.map(entrega, EntregaDTO.class);
	}
	
	public List<EntregaDTO> toCollectionDTO(List<Entrega> entrega){
		return entrega.stream()
				.map(this::toModel)
				.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInputDTO entregaInput) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
	
}
