package com.alura.jdbc.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	private DataSource datasource;
	
	public ConnectionFactory() {
		var pooledDataSource = new ComboPooledDataSource();
		pooledDataSource.setJdbcUrl("jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC");
		pooledDataSource.setUser("root");
		pooledDataSource.setPassword("");
		pooledDataSource.setMaxPoolSize(10);
		this.datasource = pooledDataSource;
 	}
	//esta refactorizacion es un design pattern
	//Petron de diseño Factory Method
	//tiene como objetivo encapsular el codigo de creacion de un objeto especifico centralizando la logica
	//en un solo punto la clase creaconexion es una fabrica de conexiones
	public Connection recuperaConexion() throws SQLException {
		//return DriverManager.getConnection(
                //"jdbc:mysql://localhost/control_de_stock?useTimeZone=true&serverTimeZone=UTC",
                //"root",
                //"");
		return  this.datasource.getConnection();
	}

}
