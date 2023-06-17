package model.bo;

import java.util.ArrayList;

import model.vo.Produto;
import model.dao.ProdutoDAO;

public class ProdutoBO {
	
	private ProdutoDAO produtoDAO = new ProdutoDAO();
	public ArrayList<Produto> buscarProdutosPorNomeOuEan(String nomeOuEan) {
		// Tudo ok, passa adiante!
		return produtoDAO.buscarProdutosPorNomeOuEan(nomeOuEan);
	}

}
