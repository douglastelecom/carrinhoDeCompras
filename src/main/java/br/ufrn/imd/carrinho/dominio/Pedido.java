package br.ufrn.imd.carrinho.dominio;

import java.time.LocalDate;
import java.util.List;

public class Pedido {

	private Usuario usuario;

	private List<Item> itens;

	/**
	 * Preço total do pedido, incluindo pedido cobrado pelos itens e pelo frete
	 */
	private Double precoTotal;

	/**
	 * Preço cobrado apenas pelos itens
	 */
	private Double precoItens;

	/**
	 * Preço cobrado pelo serviço de frete
	 */
	private Double precoFrete;

	/**
	 * Data em que foi realizado o pedido
	 */
	private LocalDate dataPedido;

	public Pedido(Usuario usuario, List<Item> itens, Double precoTotal, Double precoItens, Double precoFrete,
			LocalDate dataPedido) {
		super();
		this.usuario = usuario;
		this.itens = itens;
		this.precoTotal = precoTotal;
		this.precoItens = precoItens;
		this.precoFrete = precoFrete;
		this.dataPedido = dataPedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Item> getItens() {
		return itens;
	}

	public void setItens(List<Item> itens) {
		this.itens = itens;
	}

	public Double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(Double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Double getPrecoItens() {
		return precoItens;
	}

	public void setPrecoItens(Double precoItens) {
		this.precoItens = precoItens;
	}

	public Double getPrecoFrete() {
		return precoFrete;
	}

	public void setPrecoFrete(Double precoFrete) {
		this.precoFrete = precoFrete;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataPedido == null) ? 0 : dataPedido.hashCode());
		result = prime * result + ((itens == null) ? 0 : itens.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (dataPedido == null) {
			if (other.dataPedido != null)
				return false;
		} else if (!dataPedido.equals(other.dataPedido))
			return false;
		if (itens == null) {
			if (other.itens != null)
				return false;
		} else if (!itens.equals(other.itens))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pedido [usuario=" + usuario + ", itens=" + itens + ", precoTotal=" + precoTotal + ", precoItens="
				+ precoItens + ", precoFrete=" + precoFrete + ", dataPedido=" + dataPedido + "]";
	}

}
