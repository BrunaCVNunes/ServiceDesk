package br.usjt.arqsw.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.usjt.arqsw.dao.ChamadoDAO;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;

/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
public class ChamadoService {
	ChamadoDAO dao;
	
	
	public ChamadoService(){
		dao = new ChamadoDAO();
	}
	
	public Chamado novoChamado(Chamado chamado) throws IOException{
		chamado.setDataAbertura(new Date());
		chamado.setDataFechamento(null);
		chamado.setStatus(Chamado.ABERTO);
		return dao.inserirChamado(chamado);
	}
	
	public Chamado carregarChamado(int id){
		return dao.carregarChamado(id);
	}
	
	public ArrayList<Chamado> listarChamados(Fila fila) throws IOException{
		return dao.listarChamados(fila);
	}

	public List<Chamado> listarChamados() throws IOException{
		// TODO Auto-generated method stub
			return dao.listarChamados();
	}

}
