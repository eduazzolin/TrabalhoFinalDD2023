package model.exception;

public class VendaInvalidaException extends Exception {
	private static final long serialVersionUID = 2181610866788105253L;

	public VendaInvalidaException(String mensagem) {
		super(mensagem);
	}
}
