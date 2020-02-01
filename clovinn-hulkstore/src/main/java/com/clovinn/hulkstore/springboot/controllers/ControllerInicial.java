package com.clovinn.hulkstore.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerInicial {

	@GetMapping("/")
	public String index(){
		return "inicio";
	}	
}
