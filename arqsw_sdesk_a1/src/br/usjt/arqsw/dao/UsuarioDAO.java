package br.usjt.arqsw.dao;


import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.usjt.arqsw.entity.Usuario;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Repository
public class UsuarioDAO {
	@PersistenceContext
	EntityManager manager;
	
	@SuppressWarnings("unchecked")
	public boolean validarUsuario(Usuario usuario) throws IOException{
		String jpsql = "select u from Usuario u where u.nome = :username and u.senha = :password";
		Query query = manager.createQuery(jpsql);
		query.setParameter("username", usuario.getNome());
		query.setParameter("password", usuario.getSenha());
		List<Usuario> result = query.getResultList();
		return (result != null && result.size() == 1);
	}
}
