package com.bolsadeideas.springboot.datajpa.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

/**
 * Con esta implementacion dejas que Spring haga magia
 * 
 * @author Saul Avila
 *
 */
@Repository("clienteDaoImplCrudRepository")
public interface ClienteDaoImplCrudRepository extends CrudRepository<Cliente, Long>, IClienteDao {

}
