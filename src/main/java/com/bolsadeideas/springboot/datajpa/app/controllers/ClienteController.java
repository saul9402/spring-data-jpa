package com.bolsadeideas.springboot.datajpa.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.datajpa.app.models.dao.service.IClienteService;
import com.bolsadeideas.springboot.datajpa.app.models.entity.Cliente;
import com.bolsadeideas.springboot.datajpa.app.util.paginator.PageRender;

@Controller
//con esto va a guardar en la sesion un atributo con el nombre cliente
@SessionAttributes({ "cliente" })
public class ClienteController {

	@Autowired
	private IClienteService clienteServiceImpl;

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteServiceImpl.findById(id).orElse(null);
		if (cliente == null) {
			flash.addFlashAttribute("error", "el cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		System.out.println("EL CLIENTE ES: " + cliente.toString());
		return "ver";
	}

	@RequestMapping(value = { "/listar" }, method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 4);
		Page<Cliente> clientes = clienteServiceImpl.findAll(pageRequest);

		PageRender<Cliente> pageRender = new PageRender<Cliente>("/listar", clientes);

		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@GetMapping(value = { "/form" })
	public String crear(Model model) {
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", new Cliente());
		return "form";
	}

	@GetMapping(value = { "/form/{id}" })
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteServiceImpl.findById(id).orElse(null);
			if (cliente == null) {
				flash.addFlashAttribute("error", "El id del cliente no existe en la base de datos.");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "El id del cliente no puede ser cero.");
			return "redirect:/listar";
		}
		model.addAttribute("titulo", "Formulario de cliente.");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@PostMapping(value = { "/form" })
	// si llegara a ser diferente el nombre de esta variable con respecto al nombre
	// que se dió en el metodo crear se deberá usar la anotacion
	// @ModelAttribute({nombre_con_que_se _mando} (sin llaves))
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus sessionStatus) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		if (!foto.isEmpty()) {
			String uniqueFileName = UUID.randomUUID().toString() + foto.getOriginalFilename();
			Path rootPath = Paths.get("uploads").resolve(uniqueFileName);
			Path rootAbsolutePath = rootPath.toAbsolutePath();

			try {
				// con esto se crea la imagen en el directorio del proyecto
				Files.copy(foto.getInputStream(), rootAbsolutePath);
				flash.addFlashAttribute("info", "Has subido correctamente '" + uniqueFileName + "'");
				cliente.setFoto(uniqueFileName);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		String mensajeFlash = cliente.getId() != null ? "Cliente editado con éxito" : "Cliente creado con éxito";

		clienteServiceImpl.save(cliente);
		sessionStatus.setComplete();
		// se utiliza para crear los mensajes que aparecen y desaparecen con cada
		// petición
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = { "/eliminar/{id}" })
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			clienteServiceImpl.deleteById(id);
			flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		}
		return "redirect:/listar";
	}

}
