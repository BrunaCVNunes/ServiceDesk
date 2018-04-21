package br.usjt.aqrsw.factory;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectionFactory {

	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch (ClassNotFoundException e){
			throw new RuntimeException(e);
		}
	}
	
	public static Connection conectar() throws SQLException{
		return (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/servicedesk?useSSL=false", "root","batServerjokesonyou");
	}
}
