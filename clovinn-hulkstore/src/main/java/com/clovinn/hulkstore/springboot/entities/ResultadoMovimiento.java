package com.clovinn.hulkstore.springboot.entities;

public class ResultadoMovimiento {
	Inventario movimiento;
	String mensaje;
	String tipoMensaje;
	
	public Inventario getMovimiento() {
		return movimiento;
	}
	public void setMovimiento(Inventario movimiento) {
		this.movimiento = movimiento;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getTipoMensaje() {
		return tipoMensaje;
	}
	public void setTipoMensaje(String tipoMensaje) {
		this.tipoMensaje = tipoMensaje;
	}
	
	
}
