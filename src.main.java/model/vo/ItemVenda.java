package model.vo;

public class ItemVenda {

	// Atributos:
	private int id;
	private int idVenda;
	private int idProduto;
	private int qtde;
	private int valorUnitario;

	// Construtores:
	public ItemVenda(int id, int idVenda, int idProduto, int qtde, int valorUnitario) {
		super();
		this.id = id;
		this.idVenda = idVenda;
		this.idProduto = idProduto;
		this.qtde = qtde;
		this.valorUnitario = valorUnitario;
	}

	public ItemVenda(int idVenda, int idProduto, int qtde, int valorUnitario) {
		super();
		this.idVenda = idVenda;
		this.idProduto = idProduto;
		this.qtde = qtde;
		this.valorUnitario = valorUnitario;
	}

	public ItemVenda() {
		super();
	}

	// Getters e setters:
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

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getQtde() {
		return qtde;
	}

	public void setQtde(int qtde) {
		this.qtde = qtde;
	}

	public int getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(int valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

}
