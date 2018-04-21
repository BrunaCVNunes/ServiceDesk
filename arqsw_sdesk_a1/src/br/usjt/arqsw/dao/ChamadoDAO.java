package br.usjt.arqsw.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;

import br.usjt.aqrsw.factory.ConnectionFactory;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
import br.usjt.arqsw.service.FilaService;

/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
public class ChamadoDAO {
	
	public Chamado inserirChamado(Chamado chamado) throws IOException {
		String sqlInserir = "INSERT INTO CHAMADO VALUES (DEFAULT, ?, ?, ?, ?, ?)";
		try(Connection conn = ConnectionFactory.conectar();
				PreparedStatement stm = conn.prepareStatement(sqlInserir);){
			stm.setString(1, chamado.getDescricao());
			stm.setString(2,chamado.getStatus());
			String stringData = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			stringData = df.format(chamado.getDataAbertura());
			stm.setDate(3, java.sql.Date.valueOf(stringData));
			if (chamado.getDataFechamento() != null){
				stringData = df.format(chamado.getDataFechamento());
				stm.setDate(4, java.sql.Date.valueOf(stringData));
			}else {
				stm.setDate(4, null);
			}
			stm.setInt(5,chamado.getFila().getId());
			stm.execute();
		String sqlQuery = "SELECT LAST_INSERT_ID()";
		try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery); ResultSet rs = stm2.executeQuery();) {
			if (rs.next()) {
				chamado.setNumero(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return chamado;
	}
	
	public Chamado carregarChamado(int id){
		Chamado chamado = new Chamado();
		chamado.setNumero(id);
		String sqlSelect = "SELECT ID_CHAMADO AS 'idChamado',"
				+ "DESCRICAO AS 'descricao', "
				+ "STATUS AS 'status', "
				+ "DT_ABERTURA AS 'dataAbertura', "
				+ "DT_FECHAMENTO AS 'dataFechamento',"
				+ "ID_FILA AS 'idFila'"
				+ "FROM CHAMADO";
				try (Connection conn = ConnectionFactory.conectar();
					PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
						stm.setInt(1, chamado.getNumero());
						try (ResultSet rs = stm.executeQuery();) {
							if (rs.next()) {
								chamado.setDescricao(rs.getString("descricao"));
								chamado.setStatus(rs.getString("status"));
								DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
								String stringData = df.format(rs.getDate("dataAbertura"));
								chamado.setDataAbertura(java.sql.Date.valueOf(stringData));
								if (rs.getDate("dataFechamento") != null){
									stringData = df.format(rs.getDate("dataAbertura"));
									chamado.setDataFechamento(java.sql.Date.valueOf(stringData));
								}
								Fila fila = new Fila();
								fila.setId(rs.getInt("idFila"));
								FilaService filaSvc = new FilaService();
								try {
									fila = filaSvc.carregar(fila.getId());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								chamado.setNumero(-1);
								chamado.setDescricao(null);
								chamado.setStatus(null);
								chamado.setDataAbertura(null);
								chamado.setDataFechamento(null);
								chamado.setFila(null);
							}
					}  catch (SQLException e) {
						e.printStackTrace();
					}
					} catch (SQLException e1) {
						e1.printStackTrace();
				}
				return chamado;
	}
	
	public void alterarChamado(Chamado chamado){
		String sqlAlterar = "Update CHAMADO set DESCRICAO=?, STATUS=?, DT_ABERTURA=?, DT_FECHAMENTO=?, ID_FILA=? WHERE ID_CHAMADO =?";
		try(Connection conn = ConnectionFactory.conectar();
				PreparedStatement stm = conn.prepareStatement(sqlAlterar);){
			stm.setString(1, chamado.getDescricao());
			stm.setString(2,chamado.getStatus());
			String stringData = null;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			stringData = df.format(chamado.getDataAbertura());
			stm.setDate(3, java.sql.Date.valueOf(stringData));
			if (chamado.getDataFechamento() != null){
				stringData = df.format(chamado.getDataFechamento());
				stm.setDate(4, java.sql.Date.valueOf(stringData));
			}else {
				stm.setDate(4, null);
			}
			stm.setInt(5,chamado.getFila().getId());
			stm.setInt(6, chamado.getNumero());
			stm.execute();
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	
	public boolean excluirChamado(Chamado chamado){
		boolean result;
		String sqlDeletar = "DELETE FROM CHAMADO WHERE ID_CHAMADO =?";
		try(Connection conn = ConnectionFactory.conectar();
				PreparedStatement stm = conn.prepareStatement(sqlDeletar);){
			stm.setInt(1, chamado.getNumero());
			stm.execute();
			result = true;
		}catch(SQLException e){
			e.printStackTrace();
			result = false;
		}
		
		return result;
	}
	
	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException{
		Chamado chamado;
		ArrayList<Chamado> chamados = new ArrayList<>();
		String sqlSelecionarN = "SELECT ID_CHAMADO AS 'idChamado',"
								+ "DESCRICAO AS 'descricao', "
								+ "STATUS AS 'status', "
								+ "DT_ABERTURA AS 'dataAbertura', "
								+ "DT_FECHAMENTO AS 'dataFechamento'"
								+ "FROM CHAMADO where (ID_FILA) like ?";
		try(Connection conn = ConnectionFactory.conectar();
				PreparedStatement stm = conn.prepareStatement(sqlSelecionarN);){
				stm.setInt(1, fila.getId() );
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()){
				chamado = new Chamado();
				chamado.setNumero(rs.getInt("idChamado"));
				chamado.setDescricao(rs.getString("descricao"));
				chamado.setStatus(rs.getString("status"));
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String stringData = df.format(rs.getDate("dataAbertura"));
				chamado.setDataAbertura(java.sql.Date.valueOf(stringData));
				if (rs.getDate("dataFechamento") != null){
					stringData = df.format(rs.getDate("dataAbertura"));
					chamado.setDataFechamento(java.sql.Date.valueOf(stringData));
				}	
				chamados.add(chamado);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return chamados;
	}
	

	public List<Chamado> listarChamados(){
		Chamado chamado;
		ArrayList<Chamado> chamados = new ArrayList<>();
		String sqlSelecionarN = "SELECT ID_CHAMADO AS 'idChamado',"
								+ "DESCRICAO AS 'descricao', "
								+ "STATUS AS 'status', "
								+ "DT_ABERTURA AS 'dataAbertura', "
								+ "DT_FECHAMENTO AS 'dataFechamento',"
								+ "ID_FILA AS 'idFila'"
								+ "FROM CHAMADO";
		try(Connection conn = ConnectionFactory.conectar();
				PreparedStatement stm = conn.prepareStatement(sqlSelecionarN);){;
			try(ResultSet rs = stm.executeQuery()){
				while(rs.next()){
				chamado = new Chamado();
				chamado.setNumero(rs.getInt("idChamado"));
				chamado.setDescricao(rs.getString("descricao"));
				chamado.setStatus(rs.getString("status"));
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				String stringData = df.format(rs.getDate("dataAbertura"));
				chamado.setDataAbertura(java.sql.Date.valueOf(stringData));
				if (rs.getDate("dataFechamento") != null){
					stringData = df.format(rs.getDate("dataAbertura"));
					chamado.setDataFechamento(java.sql.Date.valueOf(stringData));
				}
				Fila fila = new Fila();
				fila.setId(rs.getInt("idFila"));
				FilaService filaSvc = new FilaService();
				try {
					fila = filaSvc.carregar(fila.getId());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				chamados.add(chamado);
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		return chamados;
	}



}
