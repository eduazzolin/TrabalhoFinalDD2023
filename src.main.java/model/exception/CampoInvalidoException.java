package model.exception;

public class CampoInvalidoException extends Exception {
	private static final long serialVersionUID = 5512032266041780537L;


	public CampoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
