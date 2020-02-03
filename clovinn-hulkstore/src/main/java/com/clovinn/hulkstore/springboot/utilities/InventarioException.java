package com.clovinn.hulkstore.springboot.utilities;

/**
 * Clase creada para el control de Excepciones con mensajes personalizados.
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
public class InventarioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructores
	 */
	public InventarioException() {
	}

	public InventarioException(String message) {
		super(message);
	}

	public InventarioException(String message, Throwable cause) {
		super(message, cause);
	}

}
