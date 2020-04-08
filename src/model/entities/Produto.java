package model.entities;

public class Produto {
	
	private String nomeP;
	private Double preco;
	
	public Produto() {
	}
	
	public Produto(String nomeP, Double preco) {
		this.nomeP = nomeP;
		this.preco = preco;
	}

	public String getNomeP() {
		return nomeP;
	}

	public void setNomeP(String nomeP) {
		this.nomeP = nomeP;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
			sb.append("Descrição: ");
			sb.append(nomeP);
			sb.append(", Valor Unitário R$ ");
			sb.append(String.format("%.2f", preco));
		return sb.toString();
	}
}
