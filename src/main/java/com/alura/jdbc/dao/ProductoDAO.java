package com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.alura.jdbc.modelo.Producto;

public class ProductoDAO {
	
	private Connection con;
	
	public ProductoDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Producto producto) {
		try{			
			final PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO producto(nombre, descripcion, cantidad, categoria_id) "
					+ "VALUES (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try(preparedStatement){	
				ejecutaRegistro(producto, preparedStatement);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	private void ejecutaRegistro(Producto producto, PreparedStatement preparedStatement)
			throws SQLException {
				preparedStatement.setString(1, producto.getNombre());
				preparedStatement.setString(2, producto.getDescripcion());
				preparedStatement.setInt(3, producto.getCantidad());
				preparedStatement.setInt(4, producto.getCategoriaId());
				preparedStatement.execute();
				final ResultSet resultSet = preparedStatement.getGeneratedKeys();
				try(resultSet){			
					while (resultSet.next()) {
						producto.setId(resultSet.getInt(1));
						System.out.println(
								String.format(
										"Fue insertado el producto %s", 
										producto 
								)
						);
					}
				}
		}

	public List<Producto> listar() {
		List<Producto> resultado = new ArrayList<>();
		try{			
			final PreparedStatement statement =  con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");
			try(statement){				
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					Producto fila = new Producto(
							resultSet.getInt("id"), 
							resultSet.getString("nombre"), 
							resultSet.getString("descripcion"), 
							resultSet.getInt("cantidad")
					);
					resultado.add(fila);
				}
				return resultado;
			} 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) {
		try {			
			final PreparedStatement statement = con.prepareStatement("UPDATE producto SET "
					+ "nombre = ?, "
					+ "descripcion = ?,"
					+ "cantidad = ? "
					+ "WHERE id = ?");
			try(statement){				
				statement.setString(1, nombre);
				statement.setString(2, descripcion);
				statement.setInt(3, cantidad);
				statement.setInt(4, id);
				statement.execute();
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int eliminar(Integer id) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
			try(statement) {
				statement.setInt(1, id);
				statement.execute();				
				int updateCount = statement.getUpdateCount();
				return updateCount;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Producto> listar(Integer categoriaId) {
		List<Producto> resultado = new ArrayList<>();
		try{			
			var querySelect = "SELECT id, nombre, descripcion, cantidad "
					+ " FROM producto "
					+ " WHERE categoria_id = ?";
			System.out.println(querySelect);
			final PreparedStatement statement =  con.prepareStatement(querySelect);
			try(statement){		
				statement.setInt(1, categoriaId);
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					Producto fila = new Producto(
							resultSet.getInt("id"), 
							resultSet.getString("nombre"), 
							resultSet.getString("descripcion"), 
							resultSet.getInt("cantidad")
					);
					resultado.add(fila);
				}
				return resultado;
			} 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
