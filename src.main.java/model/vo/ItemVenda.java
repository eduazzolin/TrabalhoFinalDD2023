package model.vo;

public class ItemVenda {

	// Atributos:
	private int id;
	private int idVenda;
	private Produto produto;
	private int qtde;
	private double valorUnitario;

	// Construtores:
	public ItemVenda(Produto produto, int qtde, double valorUnitario) {
		super();
		this.produto = produto;
		this.qtde = qtde;
		this.valorUnitario = valorUnitario;
	}

	public ItemVenda(int id, int idVenda, Produto produto, int qtde, double valorUnitario) {
		super();
		this.id = id;
		this.idVenda = idVenda;
		this.produto = produto;
		this.qtde = qtde;
		this.valorUnitario = valorUnitario;
	}

	// Getters e setters:
	public ItemVenda() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
}
