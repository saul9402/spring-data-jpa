package com.bolsadeideas.springboot.datajpa.app.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "clientes") // como buena practica las tablas llevan el nombre en plural
@Data
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String nombre;

	@NotEmpty
	private String apellido;

	@NotEmpty
	@Email
	private String email;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createAt;

	/**
	 * también se seta a LAZY para que no traiga sus facturas hasta que se mande a
	 * llamar el metodo getFaturas()
	 */
	/** inidica que un cliente tiene muchas facturas */
	/**
	 * Con mappedBy se indica que será bidireccional y al mismo tiempo indicamos que
	 * Cliente será el "owner" (propietario) de la relación
	 */
	/**
	 * orphanRemoval sirve para quitar registros de facturas que no esten asociados a ningun cliente  
	 */
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente", orphanRemoval = true)
	private List<Factura> facturas;

	private String foto;

	public Cliente() {
		// como buena practica se inicializan los arreglos para evitar nullPointer
		facturas = new ArrayList<Factura>();
	}

}
