package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionFactory;

public class TestUpdate {

	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("UPDATE producto SET "
				+ "nombre = 'Mesa', "
				+ "descripcion = 'Mesa de 4 lugares',"
				+ "cantidad = 10 WHERE id = 1");
		System.out.println(statement.getUpdateCount());
	}

}
