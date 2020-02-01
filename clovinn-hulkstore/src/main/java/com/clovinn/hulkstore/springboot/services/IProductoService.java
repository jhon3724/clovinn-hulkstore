package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.clovinn.hulkstore.springboot.entities.Producto;

public interface IProductoService {

	List<Producto> findAll();

	Optional<Producto> findByID(Long id);

	Page<Producto> findAll(Pageable pageable);

	Producto save(Producto producto);

	void delete(Long id);

}
