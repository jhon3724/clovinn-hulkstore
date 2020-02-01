package com.clovinn.hulkstore.springboot.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovinn.hulkstore.springboot.dao.IInventarioDao;
import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.ResultadoMovimiento;

@Service
public class InventarioServiceImpl implements IInventarioService {
	
	private static final String AUMENTAR_INVENTARIO ="A";

	@Autowired
	private IInventarioDao inventarioDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Inventario> findAll() {
		return (List<Inventario>) inventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Inventario findByProductoID(Long idProducto) {
		return inventarioDao.findByProductoId(idProducto);
	}

	@Override
	@Transactional
	public Inventario save(Inventario inventario) {
		return inventarioDao.save(inventario);
		
	}

	@Override
	public ResultadoMovimiento movimientoInventario(Inventario inventarioInicial, Inventario movimientoInventario) {
		ResultadoMovimiento resultado = new ResultadoMovimiento();
		if(AUMENTAR_INVENTARIO.equals(movimientoInventario.getUltimaAccion())) {
			if(inventarioInicial != null) {
				double costoPromedio = calcularCostoPromedio(movimientoInventario, inventarioInicial);
				int cantidad = movimientoInventario.getCantidad() + inventarioInicial.getCantidad();
				movimientoInventario.setCostoUnitario(costoPromedio);
				movimientoInventario.setCantidad(cantidad);
				resultado.setMovimiento(movimientoInventario);
			}else {
				resultado.setMovimiento(movimientoInventario);
			}
		}else{
			if(inventarioInicial != null) {
				if(movimientoInventario.getCantidad() > inventarioInicial.getCantidad()) {
					resultado.setMensaje("La cantidad solicitada no se encuentra disponible en el Inventario");
					resultado.setTipoMensaje("warning");
				}else {
					int nuevaCantidad = inventarioInicial.getCantidad() - movimientoInventario.getCantidad();
					movimientoInventario.setCantidad(nuevaCantidad);
					movimientoInventario.setCostoUnitario(inventarioInicial.getCostoUnitario());
					resultado.setMovimiento(movimientoInventario);
				}
			}else {
				resultado.setMensaje("El producto no tiene inventario");
				resultado.setTipoMensaje("warning");
			}
		}
		return resultado;
	}
	
	@Override
	/**
	 * Se calcula el costo promedio del Inventario. 
	 * Costo Total Entrada + Costo Total Inventario / Cantidad Total
	 */
	public double calcularCostoPromedio(Inventario entradaInventario, Inventario inventarioInicial) {
		double costoTotalEntrada = entradaInventario.getCantidad() * entradaInventario.getCostoUnitario();
		double costoTotalInventario = inventarioInicial.getCantidad() * inventarioInicial.getCostoUnitario();
		int cantidadTotal = inventarioInicial.getCantidad() + entradaInventario.getCantidad();
		double costoPromedio = (costoTotalEntrada + costoTotalInventario) / cantidadTotal; 
		return costoPromedio;
	}

}
