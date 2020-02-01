package com.clovinn.hulkstore.springboot.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.core.annotation.AliasFor;

@Entity
@Table(name = "inventario")
public class Inventario implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Producto producto;
	
	@NotNull
	private int cantidad;
	
	@NotNull
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
