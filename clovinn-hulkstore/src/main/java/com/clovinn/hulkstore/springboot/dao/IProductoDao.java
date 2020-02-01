package com.clovinn.hulkstore.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.clovinn.hulkstore.springboot.entities.Producto;


public interface IProductoDao extends JpaRepository<Producto, Long>{
	
}
