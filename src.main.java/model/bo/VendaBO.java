package model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.dao.ProdutoDAO;
import model.dao.VendaDAO;
import model.exception.EstoqueInsuficienteException;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

public class VendaBO {

	private VendaDAO vendaDAO = new VendaDAO();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	/**
	 * Validações:
	 * 1. Se todos os protudos estão em estoque;
	 * -> Cria um hashMap agrupando os produtos adicionados pelo id e somando as quantidades; 
	 * -> Percorre o hashMap consultando no estoque se cada produto tem estoque para cada quantidade;
	 */
	public Venda cadastrarVenda(Venda venda) throws EstoqueInsuficienteException {
		
		HashMap<Integer, Integer> produtosEQuantidadesAgrupados = new HashMap<Integer, Integer>();
		for (ItemVenda iv : venda.getListaItemVenda()) {
			int id = iv.getProduto().getId();
			int qtd = iv.getQtde();
			if (produtosEQuantidadesAgrupados.get(id) == null) {
				produtosEQuantidadesAgrupados.put(id, qtd);
			} else {
				int qtdExistente = produtosEQuantidadesAgrupados.get(id);
				produtosEQuantidadesAgrupados.replace(id, qtdExistente + qtd);
			}
		}
		Iterator<Integer> itr = produtosEQuantidadesAgrupados.keySet().iterator();
		while (itr.hasNext()) {
			int id = itr.next();
			int qtdNaVenda = produtosEQuantidadesAgrupados.get(id);
			int qtdNoEstoque = produtoDAO.consultarEstoque(id);
			if (qtdNoEstoque < qtdNaVenda) {
				for (ItemVenda iv : venda.getListaItemVenda()) {
					if (iv.getProduto().getId() == id) {
						throw new EstoqueInsuficienteException(
								"Erro: Estoque insuficiente:\n" + iv.getProduto().getNome());
					}
				}
			}
		}

		return vendaDAO.cadastrarVenda(venda);
	}

	public ArrayList<Venda> consultarComFiltros(VendaSeletor seletor) {
		return vendaDAO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		return vendaDAO.contarTotalRegistrosComFiltros(seletor);
	}

	public boolean removerVenda(Venda v) {
		return vendaDAO.removerVenda(v);
	}

	public ArrayList<Venda> consultarComFiltrosSemPaginacao(VendaSeletor seletor) {
		return vendaDAO.consultarComFiltrosSemPaginacao(seletor);
	}

}
