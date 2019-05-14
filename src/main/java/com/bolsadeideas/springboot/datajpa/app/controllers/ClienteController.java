package com.bolsadeideas.springboot.datajpa.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsadeideas.springboot.datajpa.app.models.dao.service.IClienteService;
import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;

@Controller
//con esto va a guardar en la sesion un atributo con el nombre cliente
@SessionAttributes({"cliente"})
public class ClienteController {

	@Autowired
	private IClienteService clienteServiceImpl;

	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteServiceImpl.findAll());
		return "listar";
	}

	@GetMapping(value = { "/form" })
	public String crear(Model model) {
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", new Cliente());
		return "form";
	}

	@GetMapping(value = {"/form/{id}"})
	public String editar(@PathVariable(value = "id") Long id, Model model) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteServiceImpl.findById(id).get();
		} else {
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@PostMapping(value = { "/form" })
	// si llegara a ser diferente el nombre de esta variable con respecto al nombre
	// que se dió en el metodo crear se deberá usar la anotacion
	// @ModelAttribute({nombre_con_que_se _mando} (sin llaves))
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		clienteServiceImpl.save(cliente);
		sessionStatus.setComplete();
		return "redirect:listar";
	}
	
	@RequestMapping(value = {"/eliminar/{id}"})
	public String eliminar(@PathVariable(value = "id")Long id) {
		if(id > 0) {
			clienteServiceImpl.deleteById(id);
		}
		return "redirect:/listar";
	}

}
