package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.utilities.InventarioException;

public interface IInventarioService {

	List<Inventario> findAll();

	Inventario findByProductoID(Long id);

	double calcularCostoPromedio(Inventario inventario, Inventario inventarioExistente);

	Inventario save(Inventario inventario);

	String actualizarInventario(Inventario movimientoInventario) throws InventarioException;

	Inventario movimientoInventario(Inventario inventarioInicial, Inventario entradaInventario)
			throws InventarioException;

}
