package br.usjt.arqsw.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Entity
public class Usuario {
	private static final long serialVersionUID = 1L;

	public static final String LOGADO = "logado";
	
	@Id
	//@Column(name="username")
	private String nome;
	
	@NotNull
	@Size(max=100)
	//@Column(name="password")
	private String senha;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return "Usuario [nome=" + nome + ", senha=" + senha + "]";
	}
}
