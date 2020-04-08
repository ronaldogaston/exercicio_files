package model.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.entities.enums.StatusDoPedido;

public class Pedido {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm");
	
	private Date momento;
	
	private Cliente cliente;
	private StatusDoPedido status;
	List<ItensDoPedido> pedido = new ArrayList<>();
	
	public Pedido() {
	}
	
	public Pedido(Date momento, Cliente cliente, StatusDoPedido status) {
		this.momento = momento;
		this.cliente = cliente;
		this.status = status;
	}

	public Date getMomento() {
		return momento;
	}

	public void setMomento(Date momento) {
		this.momento = momento;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItensDoPedido> getPedido() {
		return pedido;
	}

	public StatusDoPedido getStatus() {
		return status;
	}

	public void setStatus(StatusDoPedido status) {
		this.status = status;
	}

	public void addItem(ItensDoPedido itens) {
		pedido.add(itens);
	}
	
	public void removeItem(ItensDoPedido itens) {
		pedido.remove(itens);
	}
	
	public Double subTotal() {
		double total = 0;
		for (ItensDoPedido x : pedido) {
			total += x.Total();
		}
		return total;
	}
	
	public String File() {
		StringBuilder sb = new StringBuilder();
			for (ItensDoPedido x : pedido) {
				sb.append(x.exportFile() + "\n");
			}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
			sb.append(cliente.toString() + " - Status do Pedido: ");
			sb.append(status + " - ");
			sb.append("Horário do Pedido: " + sdf.format(momento));
			sb.append("\n");
			sb.append("\n");
			for(ItensDoPedido x : pedido) {
				sb.append(x.toString() + "\n");
			}
			sb.append("\n");
			sb.append("Total do Pedido R$ ");
			sb.append(String.format("%.2f", subTotal()));
		return sb.toString();
	}
}
