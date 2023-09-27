package com.alura.jdbc.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alura.jdbc.factory.ConnectionFactory;

public class ProductoController {

	public int modificar(String nombre, String descripcion, Integer id, Integer cantidad) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con){			
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
		}
	}

	public int eliminar(Integer id) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		PreparedStatement statement = con.prepareStatement("DELETE FROM producto WHERE id = ?");
		statement.setInt(1,Integer.valueOf(id));
		statement.execute();
		int updateCount = statement.getUpdateCount();
		con.close();
		return updateCount;
	}

	public List<Map<String, String>> listar() throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con){			
			final PreparedStatement statement =  con.prepareStatement("SELECT id, nombre, descripcion, cantidad FROM producto");
			try(statement){				
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				
				List<Map<String, String>> resultado = new ArrayList<>();
				while (resultSet.next()) {
					Map<String, String> fila = new HashMap<>();
					fila.put("id", String.valueOf(resultSet.getInt("id")));
					fila.put("nombre", String.valueOf(resultSet.getString("nombre")));
					fila.put("descripcion", String.valueOf(resultSet.getString("descripcion")));
					fila.put("cantidad", String.valueOf(resultSet.getInt("cantidad")));
					resultado.add(fila);
				}
				return resultado;
			}
		}
	}

    public void guardar(Map<String, String> producto) throws SQLException {
    	String nombre = producto.get("nombre");
    	String descripcion = producto.get("descripcion");
    	Integer cantidad = Integer.valueOf(producto.get("cantidad"));
    	Integer maximoCantidad = 50;
    	ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con){			
			con.setAutoCommit(false);
			
			final PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO producto(nombre, descripcion, cantidad) "
					+ "VALUES (?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			try(preparedStatement){	
				do {
					int cantidadParaGuardar = Math.min(cantidad, maximoCantidad);
					ejecutaRegistro(nombre, descripcion, cantidadParaGuardar, preparedStatement);	
					cantidad -= maximoCantidad;
				} while (cantidad > 0);
				con.commit();
				System.out.println("Commit");
			} catch (Exception e) {
				con.rollback();
				System.out.println("Roll back"
						+ "");
			}
		}
		
	}

	private void ejecutaRegistro(String nombre, String descripcion, Integer cantidad, PreparedStatement preparedStatement)
			throws SQLException {
		if(cantidad < 50) {
			throw new RuntimeException("Ocurrio un error");
		}
		preparedStatement.setString(1, nombre);
		preparedStatement.setString(2, descripcion);
		preparedStatement.setInt(3,cantidad);
		preparedStatement.execute();
		final ResultSet resultSet = preparedStatement.getGeneratedKeys();
		try(resultSet){			
			while (resultSet.next()) {
				System.out.println(
						String.format(
								"Fue insertado el producto de ID %d", 
								resultSet.getInt(1)
						)
				);
			}
		}
	}

}
