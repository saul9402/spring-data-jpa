package com.bolsadeideas.springboot.datajpa.app.models.dao;

import java.util.List;
import java.util.Optional;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

public interface IClienteDao {
	
	public List<Cliente> findAll();
	
	public Cliente save(Cliente cliente);
	
	public Optional<Cliente> findById(Long id);
	
	public void deleteById(Long id);

}
