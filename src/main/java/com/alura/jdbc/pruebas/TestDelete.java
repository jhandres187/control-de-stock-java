package com.alura.jdbc.pruebas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.alura.jdbc.factory.ConnectionFactory;

public class TestDelete {
	public static void main(String[] args) throws SQLException {
		Connection con = new ConnectionFactory().recuperaConexion();
		Statement statement = con.createStatement();
		statement.execute("DELETE FROM producto WHERE id = 99");
		System.out.println(statement.getUpdateCount());
	}
}
