package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovinn.hulkstore.springboot.dao.IProductoDao;
import com.clovinn.hulkstore.springboot.entities.Producto;

/**
 * Esta clase define los métodos para las operaciones CRUD de Productos
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;

	/**
	 * Constructor
	 */
	public ProductoServiceImpl(IProductoDao productoDao) {
		this.productoDao = productoDao;
	}

	/**
	 * Método para consultar todos los registros de Producto en BD con Spring JPA
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	/**
	 * Método para consultar un Producto por su ID en BD con Spring JPA
	 */
	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findByID(Long id) {
		return productoDao.findById(id);
	}

	/**
	 * Método para almacenar un Producto en BD con Spring JPA
	 */
	@Override
	@Transactional
	public Producto save(Producto cliente) {
		return productoDao.save(cliente);
	}

	/**
	 * Método para eliminar un Producto en BD con Spring JPA
	 */
	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
	}

}
