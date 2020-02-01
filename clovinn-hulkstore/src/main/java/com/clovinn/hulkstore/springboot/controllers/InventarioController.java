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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.entities.ResultadoMovimiento;
import com.clovinn.hulkstore.springboot.services.IInventarioService;
import com.clovinn.hulkstore.springboot.services.IProductoService;
import com.clovinn.hulkstore.springboot.utilities.Utilidades;


@Controller
public class InventarioController {
	
	@Autowired
	private IInventarioService inventarioService;
	
	@Autowired
	private IProductoService productoService;
	
	private final Logger log = LoggerFactory.getLogger(InventarioController.class);
	
	@GetMapping("/inventario")
	public String index(Model model){
		List<Inventario> inventario =  inventarioService.findAll();
		List<Producto> productos = productoService.findAll();
		model.addAttribute("inventario", inventario);
		model.addAttribute("productos", productos);
		model.addAttribute("inventarioForm", new Inventario());
		return "inventario";
	}
	
	@PostMapping("/actualizar-inventario")
	public String actualizarInventario(@Valid Inventario movimientoInventario, BindingResult validacion, RedirectAttributes redirectAttributes) {
		Inventario inventarioInicial = null;
		if(validacion.hasErrors()) {
			String resultadoValidacion = Utilidades.validarFormulario(validacion);
			redirectAttributes
            .addFlashAttribute("mensaje", resultadoValidacion)
            .addFlashAttribute("clase", "warning");
			return "redirect:/inventario";
		}
		
		try {
			Optional<Producto> producto = productoService.findByID(movimientoInventario.getProducto().getId());
			if(producto.isPresent()) {
				movimientoInventario.setProducto(producto.get());
			}
			inventarioInicial = inventarioService.findByProductoID(movimientoInventario.getProducto().getId());
			if(inventarioInicial != null) {
				movimientoInventario.setId(inventarioInicial.getId());
			}
			ResultadoMovimiento resultado = inventarioService.movimientoInventario(inventarioInicial, movimientoInventario);
			if(resultado.getMovimiento() != null) {
				Inventario inventario = inventarioService.save(resultado.getMovimiento());
				if(inventario != null) {
					redirectAttributes
			        .addFlashAttribute("mensaje", "Proceso exitoso!")
			        .addFlashAttribute("clase", "success");
				}
			}else {
				redirectAttributes
		        .addFlashAttribute("mensaje", resultado.getMensaje())
		        .addFlashAttribute("clase", resultado.getTipoMensaje());
			}
		} catch(DataAccessException e) {
			redirectAttributes
	        .addFlashAttribute("mensaje", "Error al realizar la consulta en la base de datos")
	        .addFlashAttribute("clase", "warning");
			log.error(e.getMessage().concat(":_ ").concat(e.getMostSpecificCause().getMessage()));
			return "redirect:/nuevo-producto";
		}
		
		return "redirect:/inventario";
	}
}
