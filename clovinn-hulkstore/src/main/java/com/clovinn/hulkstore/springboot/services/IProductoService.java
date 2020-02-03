package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import java.util.Optional;

import com.clovinn.hulkstore.springboot.entities.Producto;

public interface IProductoService {

	List<Producto> findAll();

	Optional<Producto> findByID(Long id);

	Producto save(Producto producto);

	void delete(Long id);

}
