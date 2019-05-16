package com.bolsadeideas.springboot.datajpa.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String descripcion;
	private String observacion;

	@Temporal(TemporalType.DATE)
	private Date createAt;

	/**
	 * Se pone a LAZY para evitar que traiga al cliente en la consulta que trae a la
	 * factura. Traera solo al cliente cuando se mande a llamar a su método
	 * getCliente()
	 */
	/** Indica que tenemos muchas facturas asociadas a un solo cliente. */
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	/**
	 * Este metodo se ejecutará justo antes de que se guarde cualquier factura
	 */
	@PrePersist
	public void prePersist() {
		createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
