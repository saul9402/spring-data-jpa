package com.bolsadeideas.springboot.datajpa.app.models.dao.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);

	public Cliente save(Cliente cliente);

	public Optional<Cliente> findById(Long id);

	public void deleteById(Long id);
	
}
