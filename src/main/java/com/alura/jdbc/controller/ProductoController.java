package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.ProductoDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;
import com.alura.jdbc.modelo.Producto;

public class ProductoController {

	private ProductoDAO productoDao;
	
	public ProductoController() {
		var factory = new ConnectionFactory();
		this.productoDao = new ProductoDAO(factory.recuperaConexion());
	}

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) {
		
		return productoDao.modificar(nombre, descripcion, id, cantidad);
	}

	public int eliminar(Integer id) {
		return productoDao.eliminar(id);
	}

	public List<Producto> listar() {
		return productoDao.listar();
	}
	
	public List<Producto> listar(Categoria categoria){
		return productoDao.listar(categoria.getId());
	}

    public void guardar(Producto producto, Integer categoriaId) {
    	producto.setCategoriaId(categoriaId);
    	productoDao.guardar(producto);
	}

	

}
