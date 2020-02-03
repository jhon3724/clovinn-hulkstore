package com.clovinn.hulkstore.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Esta clase hace referencia al Controlador Inicial
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
@Controller
public class ControllerInicial {

	/**
	 * Método que direcciona a la vista Inicial
	 * 
	 * @return - Nombre de la Vista
	 */
	@GetMapping("/")
	public String index() {
		return "inicio";
	}
}
