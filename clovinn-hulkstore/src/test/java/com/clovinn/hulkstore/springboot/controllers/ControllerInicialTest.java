package com.clovinn.hulkstore.springboot.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ControllerInicialTest {

	private ControllerInicial controller = new ControllerInicial();

	@Test
	void testIndex() {
		String esperado = "inicio";

		assertEquals(esperado, controller.index());
	}

}
