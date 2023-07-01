package controller;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.dao.ProdutoDAO;
import model.vo.Produto;
import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;
import model.exception.ProdutoInvalidoException;
import model.exception.VendaInvalidaException;
import model.gerador.GeradorPlanilha;
import model.seletor.ProdutoSeletor;

public class ProdutoController {

	private ProdutoBO produtoBO = new ProdutoBO();

	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) throws CampoInvalidoException {

		// verificar se o nome é válido:
		if (nomeOuEan.trim().length() == 0) {
			throw new CampoInvalidoException("Campo inválido");
		}
		return produtoBO.buscarProdutosAtivosPorNomeOuEan(nomeOuEan);
	}

	public ArrayList<Produto> buscarTodosProdutos() {

		return produtoBO.buscarTodosProdutos();
	}

	public int consultarEstoque(int id) {

		return produtoBO.consultarEstoque(id);
	}

	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado)
			throws EstoqueInsuficienteException {

		if (produtoSelecionado.getEstoque() + quantidadeDigitada < 0) {
			throw new EstoqueInsuficienteException("Estoque insuficiente");
		}
		return produtoBO.atualizarEstoque(quantidadeDigitada, produtoSelecionado);
	}

	public boolean criarProduto(Produto produtoNovo) {

		return produtoBO.criarProduto(produtoNovo);
	}

	public ArrayList<Produto> consultarComFiltros(ProdutoSeletor seletor) {

		return produtoBO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(ProdutoSeletor seletor) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		return produtoDAO.contarTotalRegistrosComFiltros(seletor);
	}

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