package com.alura.jdbc.controller;

import java.util.List;

import com.alura.jdbc.dao.CategoriaDAO;
import com.alura.jdbc.factory.ConnectionFactory;
import com.alura.jdbc.modelo.Categoria;

public class CategoriaController {
	
	private CategoriaDAO categoriaDao;
	
	public CategoriaController() {
		var factory = new ConnectionFactory();
		this.categoriaDao = new CategoriaDAO(factory.recuperaConexion());
	}

	public List<Categoria> listar() {
		return categoriaDao.listar();
	}

	public List<Categoria> cargaReporte() {
        return this.categoriaDao.listarConProductos();
    }

}
