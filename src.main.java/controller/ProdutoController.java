package controller;

import java.util.ArrayList;

import model.bo.ProdutoBO;
import model.vo.Produto;
import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;

public class ProdutoController {

	private ProdutoBO produtoBO = new ProdutoBO();
	
	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) throws CampoInvalidoException{
		
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

	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado) throws EstoqueInsuficienteException {
		
		if( produtoSelecionado.getEstoque() + quantidadeDigitada < 0) {
			throw new EstoqueInsuficienteException("Estoque insuficiente");
		}
			return produtoBO.atualizarEstoque(quantidadeDigitada , produtoSelecionado);
	}

	public Produto criarProduto(Produto produtoNovo) {
		
		return produtoBO.criarProduto(produtoNovo);
	} 
}