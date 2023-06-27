package model.bo;

import java.util.ArrayList;

import model.vo.Produto;
import model.dao.ProdutoDAO;

public class ProdutoBO {
	
	private ProdutoDAO produtoDAO = new ProdutoDAO();
	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) {
		// Tudo ok, passa adiante!
		return produtoDAO.buscarProdutosAtivosPorNomeOuEan(nomeOuEan);
	}
	public ArrayList<Produto> buscarTodosProdutos() {
		
		return produtoDAO.buscarTodosProdutos();
	}
	public int consultarEstoque(int id) {
		
		return produtoDAO.ConstularEstoque(id);
	}
	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado) {
		
		return produtoDAO.atualizarEstoque(quantidadeDigitada , produtoSelecionado);
	}
	public Produto criarProduto(Produto produtoNovo) {
		
		return produtoDAO.criarProduto(produtoNovo);
	}

}
