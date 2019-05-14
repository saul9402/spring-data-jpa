package com.bolsadeideas.springboot.datajpa.app.models.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

/**
 * Con esta implementacion lo haces "a mano"
 * 
 * @author Saul Avila
 *
 */
@Repository("clienteDaoJPA")
public class ClienteDaoImpl implements IClienteDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Cliente> findAll() {
		return em.createQuery("from Cliente").getResultList();
	}

	@Override
	public Cliente save(Cliente cliente) {
		
		if(cliente.getId() != null && cliente.getId() > 0 ) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
		return cliente;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Cliente> findById(Long id) {
		return Optional.ofNullable(em.find(Cliente.class, id));
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		em.remove(findById(id).get());
	}

}
