package model.exception;

public class ProdutoInvalidoException extends Exception {
	private static final long serialVersionUID = -5511129068721672555L;

	public ProdutoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
