package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovinn.hulkstore.springboot.dao.IProductoDao;
import com.clovinn.hulkstore.springboot.entities.Producto;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Producto> findByID(Long id) {
		return productoDao.findById(id);
	}

	@Override
	@Transactional
	public Producto save(Producto cliente) {
		return productoDao.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);
	}

}
