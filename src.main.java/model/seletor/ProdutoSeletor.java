package model.seletor;

import java.time.LocalDate;

public class ProdutoSeletor extends BaseSeletor{
	
	// Atributos:
	private String ean;
	private Double valorMinimo; 
	private Double valorMaximo;
    private String nome;
	
	// Métodos:
	@Override
	public boolean temFiltro() {
		return (this.ean != null && this.ean.trim().length() > 0)
				|| this.valorMaximo != null 
				|| this.valorMinimo != null
				|| (this.nome != null && this.nome.trim().length() > 0);
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public Double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(Double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ProdutoSeletor(String ean, Double valorMinimo, Double valorMaximo, String nome) {
		super();
		this.ean = ean;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.nome = nome;
	}

	public ProdutoSeletor() {
		super();

	}
	
	
	// Construtores:


	

}
