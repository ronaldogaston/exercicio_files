package model.entities;

public class ItensDoPedido {
	
	private Integer quantidade;
	private Double preco;
	
	private Produto produto;

	public ItensDoPedido() {
	}

	public ItensDoPedido(Integer quantidade, Double preco, Produto produto) {
		this.quantidade = quantidade;
		this.preco = preco;
		this.produto = produto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getpreco() {
		return preco;
	}

	public void setpreco(Double preco) {
		this.preco = preco;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public double Total() {
		return preco * quantidade;
	}
	
	public String exportFile() {
		return produto.getNomeP()
				+ ","
				+ produto.getPreco()
				+ ","
				+ quantidade;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
			sb.append(produto.toString() + ", Quantidade: ");
			sb.append(quantidade);
			sb.append(", SubTotal do Item R$ ");
			sb.append(String.format("%.2f", Total()));
		return sb.toString();
	}
	
	
}
