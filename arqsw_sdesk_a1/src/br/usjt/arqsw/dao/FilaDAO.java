package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.usjt.aqrsw.factory.ConnectionFactory;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.FilaService;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Repository
public class FilaDAO {
	
private Connection conn;
	
	@Autowired
	public FilaDAO(DataSource dataSource) throws IOException{
		try{
			this.conn = dataSource.getConnection();
		}catch(SQLException e){
			throw new IOException(e);
			
		}
		
	}

	public ArrayList<Fila> listarFilas() throws IOException {
		String query = "select id_fila, nm_fila from fila";
		ArrayList<Fila> lista = new ArrayList<>();
		try (PreparedStatement pst = conn.prepareStatement(query);
				ResultSet rs = pst.executeQuery();) {

			while (rs.next()) {
				Fila fila = new Fila();
				fila.setId(rs.getInt("id_fila"));
				fila.setNome(rs.getString("nm_fila"));
				lista.add(fila);
			}
			
		} catch (SQLException e) {
			throw new IOException(e);
		}
		return lista;
	}

	public Fila carregar(int id) throws IOException {
		Fila fila = new Fila();
		fila.setId(id);
		String sql = "select nm_fila from fila where id_fila=?";
		try (PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, fila.getId());
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					fila.setNome(rs.getString("nm_fila"));
				}else{
					fila.setNome(null);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new IOException(e1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
		}

		return fila;
	}
}
