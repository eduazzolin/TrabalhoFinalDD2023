package controller;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.vo.Produto;
import model.exception.CampoInvalidoException;

public class ProdutoController {

	private ProdutoBO produtoBO = new ProdutoBO();
	
	public ArrayList<Produto> buscarProdutosPorNomeOuEan(String nomeOuEan) throws CampoInvalidoException{
		
		// verificar se o nome é válido:
		if (nomeOuEan.trim().length() == 0) {
			throw new CampoInvalidoException("Campo inválido");
		}
		return produtoBO.buscarProdutosPorNomeOuEan(nomeOuEan);
	}

}
