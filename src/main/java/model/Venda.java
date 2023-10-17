package model;

public class Venda {

	private int id;
	private Cliente cliente;
	private PacoteViagem pacoteViagem;
	private int numPrestacoes;
	
	public Venda() {}
	
	@Override
	public String toString() {
		return "Venda [id=" + id + ", CPF_cliente=" + cliente.getCpf() + ", ID_pacoteViagem=" + pacoteViagem.getId() + ", numPrestacoes="
				+ numPrestacoes + "]";
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public PacoteViagem getPacoteViagem() {
		return pacoteViagem;
	}
	public void setPacoteViagem(PacoteViagem pacoteViagem) {
		this.pacoteViagem = pacoteViagem;
	}
	public int getNumPrestacoes() {
		return numPrestacoes;
	}
	public void setNumPrestacoes(int numPrestacoes) {
		this.numPrestacoes = numPrestacoes;
	}
	
	
}
