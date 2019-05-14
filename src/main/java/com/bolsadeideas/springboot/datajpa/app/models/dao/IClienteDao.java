package com.bolsadeideas.springboot.datajpa.app.models.dao;

import java.util.List;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

import ch.qos.logback.core.net.server.Client;

public interface IClienteDao {
	
	public List<Cliente> findAll();
	
	public Cliente save(Cliente cliente);

}
