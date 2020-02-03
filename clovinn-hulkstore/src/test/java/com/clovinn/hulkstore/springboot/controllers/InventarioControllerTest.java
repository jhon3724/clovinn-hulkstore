package com.clovinn.hulkstore.springboot.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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

import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.services.InventarioServiceImpl;
import com.clovinn.hulkstore.springboot.services.ProductoServiceImpl;
import com.clovinn.hulkstore.springboot.utilities.InventarioException;

@ExtendWith(MockitoExtension.class)
class InventarioControllerTest {

	@InjectMocks
	private InventarioController controller;

	@Mock
	private InventarioServiceImpl inventarioService;

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
		controller = new InventarioController(inventarioService, productoService);
	}

	@Test
	void testIndex() {
		String esperado = "inventario";

		Mockito.when(inventarioService.findAll()).thenReturn(new ArrayList<Inventario>());
		Mockito.when(productoService.findAll()).thenReturn(new ArrayList<Producto>());

		String obtenido = controller.index(model);

		assertEquals(esperado, obtenido);
	}

	@Test
	void testActualizarInventario() throws InventarioException {
		Inventario inventario = new Inventario();
		String esperado = "redirect:/inventario";

		Mockito.when(result.hasErrors()).thenReturn(false);
		Mockito.when(inventarioService.actualizarInventario(inventario)).thenReturn("Exitoso");
		Mockito.when(redirect.addFlashAttribute("mensaje", "Exitoso")).thenReturn(redirect);

		String obtenido = controller.actualizarInventario(inventario, result, redirect);

		assertEquals(esperado, obtenido);

	}

}
