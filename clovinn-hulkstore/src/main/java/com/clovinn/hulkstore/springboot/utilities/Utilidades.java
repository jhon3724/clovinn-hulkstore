package com.clovinn.hulkstore.springboot.utilities;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;

public class Utilidades {

	/**
	 * Método para verificar los datos del formulario de acuerdo a las restricciones de la Entidad
	 * @param datosFormulario - Objeto con la información de validación del formulario
	 * @return Cadena con el resultado de las validaciones
	 */
	public static String validarFormulario(BindingResult datosFormulario) {
		List<String> mensaje = datosFormulario.getFieldErrors()
				.stream()
				.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
				.collect(Collectors.toList());
		String delimitadorMensaje = ", ";
		return String.join(delimitadorMensaje, mensaje);
	}
}
