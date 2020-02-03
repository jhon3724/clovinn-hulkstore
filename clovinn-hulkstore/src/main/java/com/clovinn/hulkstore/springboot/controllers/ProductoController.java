package com.clovinn.hulkstore.springboot.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.services.IProductoService;
import com.clovinn.hulkstore.springboot.utilities.Utilidades;

/**
 * Esta clase define los métodos para el control del CRUD de Productos
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
@Controller
public class ProductoController {

	private IProductoService productoService;
	private final Logger log = LoggerFactory.getLogger(ProductoController.class);

	/**
	 * Constructor
	 * 
	 * @param productoService
	 */
	@Autowired
	public ProductoController(IProductoService productoService) {
		this.productoService = productoService;
	}

	/**
	 * Método que redirecciona a la vista de listar productos
	 * 
	 * @param model
	 * @return - Nombre de la vista
	 */
	@GetMapping("/productos")
	public String index(Model model) {
		List<Producto> productos = productoService.findAll();
		model.addAttribute("productos", productos);
		return "listar-producto";
	}

	/**
	 * Método que redirecciona al formulario para crear productos
	 * 
	 * @param model
	 * @return Nombre de la vista
	 */
	@GetMapping("/nuevo-producto")
	public String nuevoProducto(Model model) {
		model.addAttribute("producto", new Producto());
		return "formulario-producto";
	}

	/**
	 * Método que recibe la petición para actualizar el producto
	 * 
	 * @param id    - Identificador del Producto
	 * @param model
	 * @return - Nombre de la vista
	 */
	@GetMapping("/editar/{id}")
	public String editarProducto(@PathVariable Long id, Model model) {
		Optional<Producto> producto;
		try {
			producto = productoService.findByID(id);
		} catch (DataAccessException e) {
			model.addAttribute("mensaje", "Error al realizar la consulta en la base de datos");
			log.error(e.getMessage().concat(":_ ").concat(e.getMostSpecificCause().getMessage()));
			return "redirect:/nuevo-producto";
		}

		model.addAttribute("producto", producto);
		return "formulario-producto";
	}

	/**
	 * Metodo que recibe la petición para eliminar el producto
	 * 
	 * @param model
	 * @param id    - Identificador del Producto a eliminar
	 * @return Nombre de la vista
	 */
	@GetMapping("/eliminar-producto/{id}")
	public String eliminarProducto(Model model, @PathVariable Long id) {
		try {
			productoService.delete(id);
		} catch (DataAccessException e) {
			model.addAttribute("mensaje", "Error al eliminar el Producto en la base de datos");
			log.error("error", e.getMessage().concat(":_ ").concat(e.getMostSpecificCause().getMessage()));
		}
		return "redirect:/productos";
	}

	/**
	 * Método que recibe la petición para guardar un Producto
	 * 
	 * @param producto           - Objeto con los datos del Producto
	 * @param validacion         - Clase encargada de validar los datos de acuerdo a
	 *                           las restricciones
	 * @param redirectAttributes
	 * @return Nombre de la vista
	 */
	@PostMapping("/guardar-producto")
	public String guardar(@Valid Producto producto, BindingResult validacion, RedirectAttributes redirectAttributes) {
		Producto nuevoProducto = null;

		if (validacion.hasErrors()) {
			String resultadoValidacion = Utilidades.validarFormulario(validacion);
			redirectAttributes.addFlashAttribute("mensaje", resultadoValidacion).addFlashAttribute("clase", "warning");

			return "redirect:/nuevo-producto";
		}

		try {
			nuevoProducto = productoService.save(producto);
		} catch (DataAccessException e) {
			redirectAttributes.addFlashAttribute("mensaje", "Error al realizar el insert en la base de datos")
					.addFlashAttribute("clase", "warning");
			log.error("error", e.getMessage().concat(":_ ").concat(e.getMostSpecificCause().getMessage()));

			return "redirect:/nuevo-producto";
		}

		redirectAttributes.addFlashAttribute("mensaje",
				String.format("El producto %s ha sido Creado y/o Actualizado con éxito!", nuevoProducto.getNombre()))
				.addFlashAttribute("clase", "success");
		return "redirect:/productos";
	}

}
