package model.vo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Venda {

	// Atributos:
	private int id;
	private LocalDateTime dataVenda;
	private Double valorTotal;
	private Integer qtdeItens;
	private ArrayList<ItemVenda> listaItemVenda = new ArrayList<>();

	// Construtores:
	public Venda(LocalDateTime dataVenda, Double valorTotal, Integer qtdeItens, ArrayList<ItemVenda> listaItemVenda) {
		super();
		this.dataVenda = dataVenda;
		this.valorTotal = valorTotal;
		this.qtdeItens = qtdeItens;
		this.listaItemVenda = listaItemVenda;
	}

	public Venda(int id, LocalDateTime dataVenda, Double valorTotal, Integer qtdeItens,
			ArrayList<ItemVenda> listaItemVenda) {
		super();
		this.id = id;
		this.dataVenda = dataVenda;
		this.valorTotal = valorTotal;
		this.qtdeItens = qtdeItens;
		this.listaItemVenda = listaItemVenda;
	}
	public Venda() {
		super();
	}

	// Getters e setters:
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDateTime dataVenda) {
		this.dataVenda = dataVenda;
	}

	public ArrayList<ItemVenda> getListaItemVenda() {
		return listaItemVenda;
	}

	public void setListaItemVenda(ArrayList<ItemVenda> listaItemVenda) {
		this.listaItemVenda = listaItemVenda;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Integer getQtdeItens() {
		return qtdeItens;
	}

	public void setQtdeItens(Integer qtdeItens) {
		this.qtdeItens = qtdeItens;
	}

	@Override
	public String toString() {
		return "Venda #" + id + "  |  " + dataVenda.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) 
		+ "  |  Valor Total " + String.format("R$ %.2f", this.getValorTotal()) + "  |  "
				+ qtdeItens + " ite" + (qtdeItens > 1 ? "ns" : "m");
	}

}
