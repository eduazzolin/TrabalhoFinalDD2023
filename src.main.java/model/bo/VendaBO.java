package model.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import model.dao.ProdutoDAO;
import model.dao.VendaDAO;
import model.exception.EstoqueInsuficienteException;
import model.exception.VendaInvalidaException;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

public class VendaBO {

	private VendaDAO vendaDAO = new VendaDAO();
	private ProdutoDAO produtoDAO = new ProdutoDAO();

	public Venda cadastrarVenda(Venda venda) throws VendaInvalidaException, EstoqueInsuficienteException {
		// Verificando se todos os produtos tem estoque:
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
			int qtdNoEstoque = produtoDAO.ConstularEstoque(id);
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
		// tudo ok! passa adiante!
		return vendaDAO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!
		return vendaDAO.contarTotalRegistrosComFiltros(seletor);
	}

	public boolean removerVenda(Venda v) {
		// tudo ok! passa adiante!
		return vendaDAO.removerVenda(v);
	}

	public ArrayList<Venda> buscarVendasSemPaginacaoComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!   
		return vendaDAO.buscarVendasSemPaginacaoComFiltros(seletor);
	}

}
