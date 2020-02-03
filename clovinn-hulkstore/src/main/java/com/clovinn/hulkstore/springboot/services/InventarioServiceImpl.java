package com.clovinn.hulkstore.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovinn.hulkstore.springboot.dao.IInventarioDao;
import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.utilities.InventarioException;

/**
 * Esta clase define los métodos para el control del Inventario de Productos
 * 
 * @author: Jhon Garcia
 * @version: 1.0
 */
@Service
public class InventarioServiceImpl implements IInventarioService {

	private static final String AUMENTAR_INVENTARIO = "A";
	private IInventarioDao inventarioDao;
	private IProductoService productoService;

	@Autowired
	/**
	 * Constructor
	 */
	public InventarioServiceImpl(IInventarioDao inventarioDao, IProductoService productoService) {
		this.inventarioDao = inventarioDao;
		this.productoService = productoService;
	}

	@Override
	@Transactional(readOnly = true)
	/**
	 * Método para consultar todos los registros de Inventario en BD con Spring JPA
	 */
	public List<Inventario> findAll() {
		return (List<Inventario>) inventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	/**
	 * Método para consultar un registro de Inventario en BD con Spring JPA
	 */
	public Inventario findByProductoID(Long idProducto) {
		return inventarioDao.findByProductoId(idProducto);
	}

	@Override
	@Transactional
	/**
	 * Método para almacenar en BD con Spring JPA
	 */
	public Inventario save(Inventario inventario) {
		return inventarioDao.save(inventario);

	}

	/**
	 * Se realiza el llamado a la persistencia para guardar el resultado del
	 * movimiento siempre y cuando cumpla las condiciones del negocio.
	 */
	@Override
	public String actualizarInventario(Inventario movimientoInventario) throws InventarioException {
		Inventario inventarioInicial = null;
		String mensaje = "";

		Optional<Producto> producto = productoService.findByID(movimientoInventario.getProducto().getId());
		if (producto.isPresent()) {
			movimientoInventario.setProducto(producto.get());
		}
		inventarioInicial = findByProductoID(movimientoInventario.getProducto().getId());
		if (inventarioInicial != null) {
			movimientoInventario.setId(inventarioInicial.getId());
		}
		Inventario movimiento = movimientoInventario(inventarioInicial, movimientoInventario);
		Inventario resultado = save(movimiento);
		if (resultado != null) {
			mensaje = "Se actualizó el inventario exitosamente!";
		} else {
			mensaje = "No se pudo almacenar el registro";
		}
		return mensaje;
	}

	@Override
	/**
	 * Método donde se implementa la lógica del control de movimientos de inventario
	 * 
	 * @param inventarioInicial    - Stock de un producto determinado
	 * @param movimientoInventario - Transacción realizada sobre el inventario
	 * @return resultadoMovimiento - Retorna el resultado de la transacción sobre
	 *         realizada sobre el inventario
	 * 
	 */
	public Inventario movimientoInventario(Inventario inventarioInicial, Inventario movimientoInventario)
			throws InventarioException {
		if (AUMENTAR_INVENTARIO.equals(movimientoInventario.getUltimaAccion())) {
			if (inventarioInicial != null) {
				double costoPromedio = calcularCostoPromedio(movimientoInventario, inventarioInicial);
				int cantidad = movimientoInventario.getCantidad() + inventarioInicial.getCantidad();
				movimientoInventario.setCostoUnitario(costoPromedio);
				movimientoInventario.setCantidad(cantidad);
				return movimientoInventario;
			} else {
				return movimientoInventario;
			}
		} else {
			if (inventarioInicial != null) {
				if (movimientoInventario.getCantidad() > inventarioInicial.getCantidad()) {
					throw new InventarioException("La cantidad solicitada no se encuentra disponible en el Inventario");
				} else {
					int nuevaCantidad = inventarioInicial.getCantidad() - movimientoInventario.getCantidad();
					movimientoInventario.setCantidad(nuevaCantidad);
					movimientoInventario.setCostoUnitario(inventarioInicial.getCostoUnitario());
					return movimientoInventario;
				}
			} else {
				throw new InventarioException("El producto no tiene inventario");
			}
		}
	}

	@Override
	/**
	 * Se calcula el costo promedio del Inventario.
	 * 
	 * @param entradaInventario - Se asocia el tipo de movimiento y la cantidad que
	 *                          afectará el inventario
	 * @param inventarioInicial - Información del inventario actual
	 * @return costoPromedio - Retorna el cálculo del costo promedio de un producto
	 *         en inventario.
	 * 
	 */
	public double calcularCostoPromedio(Inventario entradaInventario, Inventario inventarioInicial) {
		double costoTotalEntrada = entradaInventario.getCantidad() * entradaInventario.getCostoUnitario();
		double costoTotalInventario = inventarioInicial.getCantidad() * inventarioInicial.getCostoUnitario();
		int cantidadTotal = inventarioInicial.getCantidad() + entradaInventario.getCantidad();
		double costoPromedio = (costoTotalEntrada + costoTotalInventario) / cantidadTotal;
		return costoPromedio;
	}
}
