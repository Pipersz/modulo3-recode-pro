package model;

import java.util.List;

import database.ItemCrud;

public class Destino implements ItemCrud {

	int id;
	String nome;
	String descricao;
	List<Imagem> imagens;
	
	public Destino() {}
	
	@Override
	public String toString() {
		String result = "Destino [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", imagens={";
		
		for (Imagem imagem : imagens) {
			result += imagem + ", ";
		}
		
		result += "}]";
		
		return result;
	}

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Imagem> getImagens() {
		return imagens;
	}

	public void setImagens(List<Imagem> imagens) {
		this.imagens = imagens;
	}
}
