package com.clovinn.hulkstore.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.services.ProductoServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

	@InjectMocks
	private ProductoController controller;

	@Mock
	private ProductoServiceImpl productoService;

	@Mock
	private BindingResult result;

	@Mock
	private RedirectAttributes redirect;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		controller = new ProductoController(productoService);
	}

	private Producto crearProductoDummy() {
		Producto producto = new Producto();

		producto.setId(1L);
		producto.setNombre("ProductoDummy");
		producto.setDescripcion("Descripcion Producto Dummy");
		producto.setPrecioVenta(500L);
		return producto;
	}

	@Test
	void testIndex() {
		String esperado = "listar-producto";

		Mockito.when(productoService.findAll()).thenReturn(new ArrayList<Producto>());

		String obtenido = controller.index(model);

		assertEquals(esperado, obtenido);
	}

	@Test
	void testEditarProducto() {
		String esperado = "formulario-producto";
		Producto prod = crearProductoDummy();
		Optional<Producto> producto = Optional.of(prod);

		Mockito.when(productoService.findByID(1L)).thenReturn(producto);

		String obtenido = controller.editarProducto(1L, model);

		assertEquals(esperado, obtenido);
	}

	@Test
	void testEliminarProducto() {
		String esperado = "redirect:/productos";

		String obtenido = controller.eliminarProducto(model, 1L);

		assertEquals(esperado, obtenido);
	}

	@Test
	void testGuardar() {
		String esperado = "redirect:/productos";
		Producto producto = crearProductoDummy();

		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(productoService.save(producto)).thenReturn(producto);
		Mockito.when(redirect.addFlashAttribute("mensaje", "El producto " + producto.getNombre() + " ha sido Creado y/o Actualizado con éxito!")).thenReturn(redirect);

		String obtenido = controller.guardar(producto, result, redirect);

		assertEquals(esperado, obtenido);
	}

}
