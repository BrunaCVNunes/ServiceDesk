package br.usjt.arqsw.entity;


import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981

 *
 */
public class Fila implements Serializable{



	private static final long serialVersionUID = 1L;

	@NotNull(message="Não pode existir fila.")
	@Min(value=1, message="Não pode existir fila vazia")
	private int id;
	
	@NotNull
	@Size(min=5, max=45, message="O nome da fila deve estar entre 5 e 45 caracteres.")
	private String nome;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Fila [id=" + id + ", nome=" + nome + "]";
	}
	
}
