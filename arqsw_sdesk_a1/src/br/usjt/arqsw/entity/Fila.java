package br.usjt.arqsw.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
/**
 * 
 * @author BrunaCamariniVieiraNunes-8162257981
 *
 */
@Entity
public class Fila{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_fila")
	@NotNull(message="A fila não pode ser vazia")
	@Min(value=1, message="A fila não pode ser vazia")
	private int id;
	
	@Column(name="nm_fila")
	@NotNull
	@Size(min=5, max=45, message="O nome da fila deve estar entre 5 e 45 caracteres.")
	private String nome;
	
	@Size(max = 45)
	@Column(name="nm_figura")
	private String figura;
	
	@Max(value=256,message="O caminho não pode ter mais do que 256 caracteres")
	private String caminho_figura;
	
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
	public String getFigura() {
		return figura;
	}
	public void setFigura(String figura) {
		this.figura = figura;
	}
	
	public String getCaminho_figura() {
		return caminho_figura;
	}
	public void setCaminho_figura(String caminho_figura) {
		this.caminho_figura = caminho_figura;
	}
	
	@Override
	public String toString() {
		return "Fila [id=" + id + ", nome=" + nome + ", figura=" + figura + ", caminho_figura=" + caminho_figura + "]";
	}	
	
}
