package com.clovinn.hulkstore.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.clovinn.hulkstore.springboot.entities.Inventario;

public interface IInventarioDao extends JpaRepository<Inventario, Long> {

	@Query("select i from Inventario i where i.producto.id = ?1")
	Inventario findByProductoId(Long idProducto);

}
