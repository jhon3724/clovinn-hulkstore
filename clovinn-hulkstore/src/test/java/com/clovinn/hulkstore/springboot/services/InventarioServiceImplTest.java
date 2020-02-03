package com.clovinn.hulkstore.springboot.services;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.clovinn.hulkstore.springboot.dao.IInventarioDao;
import com.clovinn.hulkstore.springboot.entities.Inventario;
import com.clovinn.hulkstore.springboot.entities.Producto;
import com.clovinn.hulkstore.springboot.utilities.InventarioException;

@ExtendWith(MockitoExtension.class)
class InventarioServiceImplTest {

	@InjectMocks
	private InventarioServiceImpl inventarioService;

	@Mock
	private ProductoServiceImpl productoService;

	@Mock
	private IInventarioDao inventarioDao;

	@Before
	public void setUp() {
		inventarioService = new InventarioServiceImpl(inventarioDao, productoService);
	}

	private Inventario crearInventarioDummy() {
		Inventario inventario = new Inventario();

		inventario.setProducto(new Producto());
		inventario.getProducto().setNombre("ProductoDummy");
		inventario.getProducto().setId(1L);
		inventario.setCantidad(5);
		inventario.setCostoUnitario(100d);

		return inventario;
	}

	private Inventario crearSaldoInicialDummy() {
		Inventario inventario = new Inventario();

		inventario.setProducto(new Producto());
		inventario.getProducto().setNombre("ProductoDummy");
		inventario.getProducto().setId(1L);
		inventario.setCantidad(15);
		inventario.setCostoUnitario(150d);

		return inventario;
	}

	private Producto crearProductoDummy() {
		Producto producto = new Producto();
		
		producto.setId(1L);
		producto.setNombre("ProductoDummy");
		producto.setDescripcion("Descripcion Producto Dummy");
		producto.setPrecioVenta(500L);
		return producto;
	}

	@Test
	public void actualizarInventarioTest() throws InventarioException {
		Inventario movimientoInventario = crearInventarioDummy();
		Producto prod = crearProductoDummy();
		Optional<Producto> producto = Optional.of(prod);
		
		Mockito.when(productoService.findByID(1L)).thenReturn(producto);
		Mockito.when(inventarioDao.findByProductoId(1L)).thenReturn(movimientoInventario);
		Mockito.when(inventarioDao.save(movimientoInventario)).thenReturn(movimientoInventario);
		
		String resultado = inventarioService.actualizarInventario(movimientoInventario);
		
		assertEquals("Se actualizó el inventario exitosamente!", resultado);
	}

	@Test
	public void movimientoInventarioAumentarSinInventarioTest() throws InventarioException {
		Inventario inventarioInicial = null;
		Inventario movimientoInventario = crearInventarioDummy();
		movimientoInventario.setUltimaAccion("A");

		Inventario resultadoMovimiento = inventarioService.movimientoInventario(inventarioInicial,
				movimientoInventario);

		assertEquals("ProductoDummy", resultadoMovimiento.getProducto().getNombre());
		assertEquals(5, resultadoMovimiento.getCantidad());
		assertEquals(100, resultadoMovimiento.getCostoUnitario(), 0.005);
	}

	@Test
	public void movimientoInventarioAumentarConInventarioTest() throws InventarioException {
		Inventario inventarioInicial = crearSaldoInicialDummy();
		Inventario movimientoInventario = crearInventarioDummy();
		movimientoInventario.setUltimaAccion("A");

		Inventario resultadoMovimiento = inventarioService.movimientoInventario(inventarioInicial,
				movimientoInventario);

		assertEquals("ProductoDummy", resultadoMovimiento.getProducto().getNombre());
		assertEquals(20, resultadoMovimiento.getCantidad());
		assertEquals(137.5, resultadoMovimiento.getCostoUnitario(), 0.005);
	}

	@Test
	public void movimientoInventarioDisminuirSinInventarioTest() throws InventarioException {
		Inventario inventarioInicial = null;
		Inventario movimientoInventario = crearInventarioDummy();
		movimientoInventario.setUltimaAccion("D");

		try {
			inventarioService.movimientoInventario(inventarioInicial, movimientoInventario);
			fail();
		} catch (InventarioException ex) {
			assertEquals("El producto no tiene inventario", ex.getMessage());
		} catch (Exception ex) {
			fail();
		}
	}

	@Test
	public void movimientoInventarioDisminuirConInventarioTest() throws InventarioException {
		Inventario inventarioInicial = crearSaldoInicialDummy();
		Inventario movimientoInventario = crearInventarioDummy();
		movimientoInventario.setUltimaAccion("D");

		Inventario resultadoMovimiento = inventarioService.movimientoInventario(inventarioInicial,
				movimientoInventario);

		assertEquals("ProductoDummy", resultadoMovimiento.getProducto().getNombre());
		assertEquals(10, resultadoMovimiento.getCantidad());
		assertEquals(150, resultadoMovimiento.getCostoUnitario(), 0.005);
	}

	@Test
	public void calcularCostoPromedioTest() {
		Inventario inventarioInicial = crearSaldoInicialDummy();
		Inventario movimientoInventario = crearInventarioDummy();

		double costoPromedio = inventarioService.calcularCostoPromedio(movimientoInventario, inventarioInicial);
		
		assertEquals(137.5, costoPromedio, 0.005);
	}
}
