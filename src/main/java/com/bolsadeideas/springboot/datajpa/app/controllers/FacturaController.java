package com.bolsadeideas.springboot.datajpa.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.models.entity.Factura;
import com.bolsadeideas.springboot.datajpa.app.models.entity.Producto;
import com.bolsadeideas.springboot.datajpa.app.models.service.IClienteService;

@Controller
@RequestMapping(value = "/factura")
@SessionAttributes({ "factura" })
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@GetMapping(value = { "/form/{clienteId}" })
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findById(clienteId).orElse(null);
		if (cliente == null) {
			flash.addFlashAttribute("error", "¡El ciente no existe en la base de datos!");
			return "redirect:/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear Factura");

		return "factura/form";
	}

	@GetMapping(value = { "/cargar-productos/{term}" }, produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable("term") String term) {
		return clienteService.findByName(term);
	}

}
