package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ChamadoDAO {
	
private Connection conn;
	
	@Autowired
	public ChamadoDAO(DataSource dataSource) throws IOException{
		try{
			this.conn = dataSource.getConnection();
		}catch(SQLException e){
			throw new IOException(e);
		}
	}

	public int criarChamado(Chamado chamado) throws IOException {
		int id = -1;
		String sql = "INSERT INTO chamado (descricao, status, dt_abertura, id_fila) VALUES (?,?,?,?)";
		String sql1 = "SELECT LAST_INSERT_ID()";
		try(PreparedStatement stmt = conn.prepareStatement(sql);){
			stmt.setString(1, chamado.getDescricao());
			stmt.setString(2, chamado.getStatus());
			stmt.setDate(3, new Date(chamado.getDataAbertura().getTime()));
			stmt.setInt(4, chamado.getFila().getId());
			stmt.execute();
			
			try(PreparedStatement stm = conn.prepareStatement(sql1);
					ResultSet rs = stm.executeQuery();){
				rs.next();
				id = rs.getInt(1);
			}catch(SQLException e1){
				throw new IOException(e1);
			}
			
		}catch(SQLException e){
			throw new IOException(e);
		}
		
		return id;
	}

	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException {
		ArrayList<Chamado> listaChamados = new ArrayList<Chamado>();

		String sql = "SELECT c.ID_CHAMADO, c.DESCRICAO, c.STATUS, c.DT_ABERTURA, c.DT_FECHAMENTO, f.NM_FILA"
				+ " FROM chamado c INNER JOIN fila f ON c.ID_FILA = f.ID_FILA WHERE c.ID_FILA = ?";

		try (PreparedStatement stm = conn.prepareStatement(sql);) {
			stm.setInt(1, fila.getId());
			try (ResultSet rs = stm.executeQuery();) {

				while (rs.next()) {
					Chamado chamado = new Chamado();
					chamado.setNumero(rs.getInt("ID_CHAMADO"));
					chamado.setDescricao(rs.getString("DESCRICAO"));
					chamado.setStatus(rs.getString("STATUS"));
					chamado.setDataAbertura(rs.getDate("DT_ABERTURA"));
					chamado.setDataFechamento(rs.getDate("DT_FECHAMENTO"));
					// Captura o nome da fila
					fila.setNome(rs.getString("NM_FILA"));
					chamado.setFila(fila);
					listaChamados.add(chamado);
				}

			} catch (SQLException e1) {
				throw new IOException(e1);
			}

		} catch (SQLException e) {
			throw new IOException(e);
		}

		return listaChamados;
	}


}
