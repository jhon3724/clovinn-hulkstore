package com.clovinn.hulkstore.springboot.services;

import java.util.List;

import javax.validation.Valid;
import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.ResultadoMovimiento;



public interface IInventarioService {

	List<Inventario> findAll();

	Inventario findByProductoID(Long id);

	double calcularCostoPromedio(Inventario inventario, Inventario inventarioExistente);

	Inventario save(Inventario inventario);

	ResultadoMovimiento movimientoInventario(Inventario inventarioInicial, @Valid Inventario entradaInventario);

}
