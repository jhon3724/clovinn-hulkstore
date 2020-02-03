package com.clovinn.hulkstore.springboot.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "inventario")
public class Inventario implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Producto producto;
	
	@NotNull
	@Min(1)
	private int cantidad;
	
	@NotNull
	@Min(1)
	@Column(name = "costo_unitario")
	private double costoUnitario;
	
	@NotEmpty
	@Column(name = "ultima_accion")
	private String ultimaAccion;
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public int getCantidad() {
		return cantidad;
	}


	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}


	public double getCostoUnitario() {
		return costoUnitario;
	}


	public void setCostoUnitario(double costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public String getUltimaAccion() {
		return ultimaAccion;
	}


	public void setUltimaAccion(String ultimaAccion) {
		this.ultimaAccion = ultimaAccion;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
