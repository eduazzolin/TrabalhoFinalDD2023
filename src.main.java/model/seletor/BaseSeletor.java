package model.seletor;

public abstract class BaseSeletor {

	// Atributos:
	private int pagina;
	private int limite;

	// Métodos:
	public abstract boolean temFiltro();
	
	public boolean temPaginacao() {
		return this.limite > 0 && this.pagina > 0;
	}
	
	public int getOffset() {
		return this.limite * (this.pagina - 1);
	}
	
	// Construtores:
	public BaseSeletor() {
		this.limite = 0;
		this.pagina = 0;
	}

	// Getters e Setters:
	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

}
