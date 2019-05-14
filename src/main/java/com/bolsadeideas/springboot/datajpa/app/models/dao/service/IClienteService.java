package com.bolsadeideas.springboot.datajpa.app.models.dao.service;

import java.util.List;
import java.util.Optional;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

public interface IClienteService {

	public List<Cliente> findAll();

	public Cliente save(Cliente cliente);

	public Optional<Cliente> findById(Long id);

	public void deleteById(Long id);

}
