package model.seletor;

import java.time.LocalDate;

public class VendaSeletor extends BaseSeletor{
	
	// Atributos:
	private String ean;
	private Double valorMinimo; 
	private Double valorMaximo;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	
	// Métodos:
	@Override
	public boolean temFiltro() {
		return (this.ean != null && this.ean.trim().length() > 0)
				|| this.valorMaximo != null 
				|| this.valorMinimo != null
				|| this.dataFinal != null
				|| this.dataInicial != null;
	}
	
	
	// Construtores:
	public VendaSeletor(String ean, Double valorMinimo, Double valorMaximo, LocalDate dataInicial,
			LocalDate dataFinal) {
		super();
		this.ean = ean;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}

	public VendaSeletor() {
		super();
	}

	// Getters e Setters:
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

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

}
