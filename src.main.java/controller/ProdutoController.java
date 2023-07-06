package controller;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.dao.ProdutoDAO;
import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;
import model.exception.ProdutoInvalidoException;
import model.gerador.GeradorPlanilha;
import model.seletor.ProdutoSeletor;
import model.vo.Produto;

public class ProdutoController {

	private ProdutoBO produtoBO = new ProdutoBO();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	/**
	 * Validações:
	 * 1. Se o campo não está em branco;
	 */
	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) throws CampoInvalidoException {
		if (nomeOuEan.trim().length() == 0) {
			throw new CampoInvalidoException("Campo inválido");
		}
		
		return produtoBO.buscarProdutosAtivosPorNomeOuEan(nomeOuEan);
	}

	/**
	 * Validações:
	 * 1. Se o destino não está em branco;
	 * 2. Se a lista de produtos não está vazia;
	 */
	public String gerarPlanilhaProdutos(ArrayList<Produto> produtos, String destinoArquivo)
			throws CampoInvalidoException, ProdutoInvalidoException {
		if (destinoArquivo == null || destinoArquivo.trim().isEmpty()) {
			throw new CampoInvalidoException("Preencha todos os campos");
		}
		if (produtos == null) {
			throw new ProdutoInvalidoException("Erro: a consulta está vazia");
		}

		GeradorPlanilha gerador = new GeradorPlanilha();
		return gerador.gerarPlanilhaProdutos(produtos, destinoArquivo);
	}
	
	/**
	 * Validações:
	 * 1. Se há estoque suficiente para descontar;
	 */
	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado)
			throws EstoqueInsuficienteException {
		if (produtoSelecionado.getEstoque() + quantidadeDigitada < 0) {
			throw new EstoqueInsuficienteException("Quantidade inválida!");
		}
		return produtoBO.atualizarEstoque(quantidadeDigitada, produtoSelecionado);
	}
	
	public ArrayList<Produto> buscarTodosProdutos() {
		return produtoBO.buscarTodosProdutos();
	}

	public int consultarEstoque(int id) {
		return produtoBO.consultarEstoque(id);
	}

	public boolean criarProduto(Produto produtoNovo) {
		return produtoBO.criarProduto(produtoNovo);
	}

	public ArrayList<Produto> consultarComFiltros(ProdutoSeletor seletor) {
		return produtoBO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(ProdutoSeletor seletor) {
		return produtoDAO.contarTotalRegistrosComFiltros(seletor);
	}

	public boolean removerProduto(Produto produtoSelecionado) {
		return produtoBO.removerProduto(produtoSelecionado);
	}

	public boolean editarProduto(Produto produtoNovo) {
		return produtoBO.editarProduto(produtoNovo);
	}

	public ArrayList<Produto> buscarTodosProdutosAtivados() {
		return produtoBO.buscarTodosProdutosAtivados();
	}
}