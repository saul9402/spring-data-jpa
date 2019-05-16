package com.bolsadeideas.springboot.datajpa.app.models.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	@SuppressWarnings("unchecked")
	public Cliente save(Cliente cliente);
	
	public Optional<Cliente> findById(Long id);
	
	public void deleteById(Long id);

}
