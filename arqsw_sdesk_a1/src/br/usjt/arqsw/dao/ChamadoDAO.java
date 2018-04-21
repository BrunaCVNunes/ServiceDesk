package br.usjt.arqsw.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Repository
public class ChamadoDAO {
	@PersistenceContext
	EntityManager manager;
	
	public int criarChamado(Chamado chamado) {
		manager.persist(chamado);
		return chamado.getNumero();
	}

	@SuppressWarnings("unchecked")
	public List<Chamado> listarChamadosAbertos(Fila fila) {
		fila = manager.find(Fila.class, fila.getId());
		
		String jpsql = "select c from Chamado c where c.fila= :fila and c.status = :status";
		
		Query query = manager.createQuery(jpsql);
		query.setParameter("fila", fila);
		query.setParameter("status", Chamado.ABERTO);
		
		List<Chamado> result = query.getResultList();
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Chamado> listarChamados(Fila fila) throws IOException{
		fila = manager.find(Fila.class, fila.getId());
		String jpql = "select c from Chamado c where c.fila = :fila";
		return manager.createQuery(jpql).setParameter("fila", fila).getResultList();
	}
	
	public void fecharChamados(ArrayList<Integer> lista) throws IOException {
		for(int id:lista){
			Chamado chamado = manager.find(Chamado.class,id);
			chamado.setDataFechamento(new Date());
			chamado.setStatus(Chamado.FECHADO);
			manager.merge(chamado);
		}
	}

	@SuppressWarnings("unchecked")
	public List<Chamado> listarTodosChamados() throws IOException{
		return manager.createQuery("select c from Chamado c").getResultList();
	}

}
