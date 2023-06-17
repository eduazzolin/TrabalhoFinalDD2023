package model.vo;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Venda {

	// Atributos:
	private int id;
	private LocalDateTime dataVenda;
	private ArrayList<ItemVenda> listaItemVenda;

	// Construtores:
	public Venda(int id, LocalDateTime dataVenda, ArrayList<ItemVenda> listaItemVenda) {
		super();
		this.id = id;
		this.dataVenda = dataVenda;
		this.listaItemVenda = listaItemVenda;
	}

	public Venda(LocalDateTime dataVenda, ArrayList<ItemVenda> listaItemVenda) {
		super();
		this.dataVenda = dataVenda;
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

}
