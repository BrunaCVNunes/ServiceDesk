package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Usuario;

@Repository
public class UsuarioDAO {
		private Connection conn;
		
		@Autowired
		public UsuarioDAO(DataSource dataSource) throws IOException{
			try{
				this.conn = dataSource.getConnection();
			}catch(SQLException e){
				throw new IOException(e);
			}
		}
		
		public boolean validarUsuario(Usuario usuario) throws IOException{
			String sql = "SELECT username, password FROM usuario Where username=? and password=?";
			boolean validado = false;
			try(PreparedStatement stmt = conn.prepareStatement(sql);){
				stmt.setString(1, usuario.getNome());
				stmt.setString(2, usuario.getSenha());
				try(ResultSet rs = stmt.executeQuery();){
					if(rs.next()){
						usuario.setNome(rs.getString("username"));
						usuario.setSenha(rs.getString("password"));
						validado = true;
					}else{
						usuario.setNome(null);
						usuario.setSenha(null);
					}
					
				}catch(SQLException e1){
					e1.printStackTrace();
					throw new IOException(e1);
				}
				
			}catch(SQLException e){
				e.printStackTrace();
				throw new IOException(e);
			}
			
			return validado;
		}


}
