package com.bolsadeideas.springboot.datajpa.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

}
