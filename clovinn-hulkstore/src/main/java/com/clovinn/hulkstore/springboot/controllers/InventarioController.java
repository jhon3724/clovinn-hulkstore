package com.clovinn.hulkstore.springboot.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.services.IInventarioService;
import com.clovinn.hulkstore.springboot.services.IProductoService;
import com.clovinn.hulkstore.springboot.utilities.InventarioException;
import com.clovinn.hulkstore.springboot.utilities.Utilidades;

/**
 * Esta clase define los métodos para la comunicación con la capa de
 * presentación del manejo de inventarios
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
@Controller
public class InventarioController {

	// Campos de la clase
	private IInventarioService inventarioService;
	private IProductoService productoService;

	private final Logger log = LoggerFactory.getLogger(InventarioController.class);

	/**
	 * Constructor de la Clase que a su vez se encarga de la inyección de
	 * dependencias
	 * 
	 * @param inventarioService - Dependencia
	 * @param productoService   - Dependencia
	 */
	@Autowired
	public InventarioController(IInventarioService inventarioService, IProductoService productoService) {
		this.inventarioService = inventarioService;
		this.productoService = productoService;
	}

	/**
	 * Método que toma la petición del navegador y redirige a la vista de Inventario
	 * 
	 * @param model
	 * @return - nombre de la vista
	 */
	@GetMapping("/inventario")
	public String index(Model model) {
		List<Inventario> inventario = inventarioService.findAll();
		List<Producto> productos = productoService.findAll();
		model.addAttribute("inventario", inventario);
		model.addAttribute("productos", productos);
		model.addAttribute("inventarioForm", new Inventario());
		return "inventario";
	}

	/**
	 * Método donde se controlan los movimientos de Inventario
	 * 
	 * @param movimientoInventario
	 * @param validacion
	 * @param redirectAttributes
	 * @return - Nombre de la vista
	 */
	@PostMapping("/actualizar-inventario")
	public String actualizarInventario(@Valid Inventario movimientoInventario, BindingResult validacion,
			RedirectAttributes redirectAttributes) {
		String resultadoProceso = "";
		if (validacion.hasErrors()) {
			String resultadoValidacion = Utilidades.validarFormulario(validacion);
			redirectAttributes.addFlashAttribute("mensaje", resultadoValidacion).addFlashAttribute("clase", "warning");
			return "redirect:/inventario";
		}

		try {
			resultadoProceso = inventarioService.actualizarInventario(movimientoInventario);
			redirectAttributes.addFlashAttribute("mensaje", resultadoProceso).addFlashAttribute("clase", "success");
		} catch (InventarioException e) {
			redirectAttributes.addFlashAttribute("mensaje", e.getMessage()).addFlashAttribute("clase", "warning");
			log.error(e.getLocalizedMessage());
			return "redirect:/inventario";
		}
		return "redirect:/inventario";
	}
}
