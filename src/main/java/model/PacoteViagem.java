package model;

import java.time.LocalDate;

import database.ItemCrud;

public class PacoteViagem implements ItemCrud {
	
	int id;
	Destino destino;
	LocalDate dataIda;
	LocalDate dataVolta;
	float preco;
	int numMaxPrestacoes;
	
	public PacoteViagem() {}

	@Override
	public String toString() {
		return "PacoteViagem [id=" + id + ", destino=" + destino + ", dataIda=" + dataIda + ", dataVolta=" + dataVolta
				+ ", preco=" + preco + ", numMaxPrestacoes=" + numMaxPrestacoes + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public LocalDate getDataIda() {
		return dataIda;
	}

	public void setDataIda(LocalDate dataIda) {
		this.dataIda = dataIda;
	}

	public LocalDate getDataVolta() {
		return dataVolta;
	}

	public void setDataVolta(LocalDate dataVolta) {
		this.dataVolta = dataVolta;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

	public int getNumMaxPrestacoes() {
		return numMaxPrestacoes;
	}

	public void setNumMaxPrestacoes(int numMaxPrestacoes) {
		this.numMaxPrestacoes = numMaxPrestacoes;
	}
}
