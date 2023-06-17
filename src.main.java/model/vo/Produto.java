package model.vo;

public class Produto {

	// Atributos:
	private int id;
	private String nome;
	private String descricao;
	private String ean;
	private double valor;
	private int estoque;

	// Construtores:
	public Produto(int id, String nome, String descricao, String ean, double valor, int estoque) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ean = ean;
		this.valor = valor;
		this.estoque = estoque;
	}

	public Produto(String nome, String descricao, String ean, double valor, int estoque) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ean = ean;
		this.valor = valor;
		this.estoque = estoque;
	}

	public Produto() {
		super();
	}

	// Getters e setters:
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

}
