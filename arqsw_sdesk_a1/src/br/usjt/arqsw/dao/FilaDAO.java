package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.usjt.aqrsw.factory.ConnectionFactory;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.FilaService;

public class FilaDAO {

	public ArrayList<Fila> listarFilas() throws IOException {
		String query = "select id_fila, nm_fila from fila";
		ArrayList<Fila> lista = new ArrayList<>();
		try(Connection conn = ConnectionFactory.conectar();
			PreparedStatement pst = conn.prepareStatement(query);
			ResultSet rs = pst.executeQuery();){
			
			while(rs.next()) {
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

	public Fila carregarFila(int id) {
		Fila fila = new Fila();
			fila.setId(id);
		String sqlSelect = "SELECT NM_FILA AS 'nome',"
				+ "FROM FILA WHERE ID_FILA=?";
				try (Connection conn = ConnectionFactory.conectar();
					PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
						stm.setInt(1, id);
						try (ResultSet rs = stm.executeQuery();) {
							if (rs.next()) {								
								fila.setNome(rs.getString("nome"));
							} else {
								fila.setId(-1);
								fila.setNome(null);
							}
					}  catch (SQLException e) {
						e.printStackTrace();
					}
					} catch (SQLException e1) {
						e1.printStackTrace();
				}
				return fila;

	}

}
