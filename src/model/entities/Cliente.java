package model.entities;

public class Cliente {
	
	private String cliente;
	
	public Cliente() {
	}

	public Cliente(String cliente) {
		this.cliente = cliente;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
			sb.append("Nome: ");
			sb.append(cliente);
		return sb.toString();
	}
}
