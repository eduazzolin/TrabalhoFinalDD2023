package model.vo;

public class Produto {

	// Atributos:
	private int id;
	private String nome;
	private String descricao;
	private String ean;
	private double valor;
	private int estoque;
	private boolean ativo;

	// Construtores:
	public Produto(int id, String nome, String descricao, String ean, double valor, int estoque, boolean ativo) {
		super();
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.ean = ean;
		this.valor = valor;
		this.estoque = estoque;
		this.ativo = ativo;
	}

	public Produto(String nome, String descricao, String ean, double valor, int estoque, boolean ativo) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.ean = ean;
		this.valor = valor;
		this.estoque = estoque;
		this.ativo = ativo;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	
	// Demais métodos:
	@Override
	public String toString() {
		return String.format("%-16s [%s] %s", this.getEan(),(this.getEstoque()>0 ? "+" : "-"), this.getNome());
	}

	public String toStringDescricaoCompleta() {
		return String.format(""
				+ "Nome: %s\n"
				+ "Descrição: %.200s\n"
				+ "EAN: %s\n"
				+ "Valor unitário: R$ %.2f\n"
				, this.getNome(), this.getDescricao(), this.getEan(), this.getValor());
	}

}
