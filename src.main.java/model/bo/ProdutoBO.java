package model.bo;

import java.util.ArrayList;

import model.vo.Produto;
import model.dao.ProdutoDAO;
import model.seletor.ProdutoSeletor;

public class ProdutoBO {

	private ProdutoDAO produtoDAO = new ProdutoDAO();

	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) {
		return produtoDAO.buscarProdutosAtivosPorNomeOuEan(nomeOuEan);
	}

	public ArrayList<Produto> buscarTodosProdutos() {
		return produtoDAO.buscarTodosProdutos();
	}

	public int consultarEstoque(int id) {
		return produtoDAO.consultarEstoque(id);
	}

	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado) {
		return produtoDAO.atualizarEstoque(quantidadeDigitada, produtoSelecionado);
	}

	public boolean criarProduto(Produto produtoNovo) {
		return produtoDAO.criarProduto(produtoNovo);
	}

	public ArrayList<Produto> consultarComFiltros(ProdutoSeletor seletor) {
		return produtoDAO.consultarComFiltos(seletor);
	}

	public boolean editarProduto(Produto produtoNovo) {
		return produtoDAO.editarProduto(produtoNovo);
	}

	public boolean removerProduto(Produto produtoSelecionado) {
		return produtoDAO.removerProduto(produtoSelecionado);
	}

	public ArrayList<Produto> buscarTodosProdutosAtivados() {
		return produtoDAO.buscarTodosProdutosAtivados();
	}
}
