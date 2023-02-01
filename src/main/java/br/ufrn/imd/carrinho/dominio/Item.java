package br.ufrn.imd.carrinho.dominio;

import java.math.BigDecimal;

public class Item {

	private String nome;

	private String descricao;

	private Double preco;

	private Double peso;

	private ItemTipo tipo;

	public Item(String nome, String descricao, Double preco, Double peso, ItemTipo tipo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
		this.peso = peso;
		this.tipo = tipo;
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

	public BigDecimal getPreco() {
		return BigDecimal.valueOf(preco);
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public BigDecimal getPeso() {
		return BigDecimal.valueOf(peso);
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public ItemTipo getTipo() {
		return tipo;
	}

	public void setTipo(ItemTipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		result = prime * result + ((preco == null) ? 0 : preco.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
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
		Item other = (Item) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		if (preco == null) {
			if (other.preco != null)
				return false;
		} else if (!preco.equals(other.preco))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [nome=" + nome + ", descricao=" + descricao + ", preco=" + preco + ", peso=" + peso + ", tipo="
				+ tipo + "]";
	}
}
