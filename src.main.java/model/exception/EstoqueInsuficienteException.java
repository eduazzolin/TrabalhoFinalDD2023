package model.exception;

public class EstoqueInsuficienteException extends Exception {
	private static final long serialVersionUID = 4174859349346854289L;


	public EstoqueInsuficienteException(String mensagem) {
		super(mensagem);
	}
}
