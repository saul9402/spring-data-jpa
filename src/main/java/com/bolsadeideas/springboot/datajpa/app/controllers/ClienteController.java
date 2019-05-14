package com.bolsadeideas.springboot.datajpa.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsadeideas.springboot.datajpa.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

@Controller
public class ClienteController {

	@Autowired
	@Qualifier("clienteDaoImplCrudRepository")
	private IClienteDao clienteDao;

	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}

	@GetMapping(value = { "/form" })
	public String crear(Model model) {
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", new Cliente());
		return "form";
	}

	@PostMapping(value = { "/form" })
	public String guardar(Cliente cliente) {
		System.out.println("LLEGUE CON: " + cliente);
		clienteDao.save(cliente);
		return "redirect:listar";
	}

}
