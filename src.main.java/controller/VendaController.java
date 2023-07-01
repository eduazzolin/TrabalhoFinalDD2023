package controller;

import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;
import model.exception.VendaInvalidaException;
import model.gerador.GeradorPlanilha;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

import java.util.ArrayList;

import model.bo.VendaBO;

public class VendaController {

	private VendaBO vendaBO = new VendaBO();

	/**
	 * Validações:
	 * 1. Se a lista de ItemVenda não está vazia;
	 * 2. Se todos os produtos estão ativos;
	 * 3. (BO) Se todos os protudos estão em estoque;
	 */
	public Venda cadastrarVenda(Venda venda) throws VendaInvalidaException, EstoqueInsuficienteException {
		if (venda.getListaItemVenda() == null || venda.getListaItemVenda().size() == 0) {
			throw new VendaInvalidaException("Erro: A venda não pode estar vazia!");
		}
		for (ItemVenda iv : venda.getListaItemVenda()) {
			if (!iv.getProduto().isAtivo()) {
				throw new VendaInvalidaException("Erro: A venda possui produtos desativados!");
			}
		}
		return vendaBO.cadastrarVenda(venda);
	}

	/**
	 * Validações:
	 * 1. Se a venda passada por parâmetro está preenchida;
	 */
	public boolean removerVenda(Venda v) throws VendaInvalidaException {
		if (v == null || v.getId() == 0) {
			throw new VendaInvalidaException("Venda inválida, selecione novamente.");
		}
		return vendaBO.removerVenda(v);
	}

	/**
	 * Validações:
	 * 1. Se o destino não está em branco;
	 * 2. Se a lista de vendas não está vazia;
	 */
	public String gerarPlanilhaSomenteVendas(ArrayList<Venda> vendas, String destinoArquivo)
			throws CampoInvalidoException, VendaInvalidaException {
		if (destinoArquivo == null || destinoArquivo.trim().isEmpty()) {
			throw new CampoInvalidoException("Preencha todos os campos");
		}
		if (vendas == null || vendas.size() == 0) {
			throw new VendaInvalidaException("Erro: a venda está vazia");
		}

		GeradorPlanilha gerador = new GeradorPlanilha();
		return gerador.gerarPlanilhaSomenteVendas(vendas, destinoArquivo);
	}

	/**
	 * Validações:
	 * 1. Se o destino não está em branco;
	 * 2. Se a lista de vendas não está vazia;
	 */
	public String gerarPlanilhaVendasComProdutos(ArrayList<Venda> vendas, String destinoArquivo)
			throws CampoInvalidoException, VendaInvalidaException {
		if (destinoArquivo == null || destinoArquivo.trim().isEmpty()) {
			throw new CampoInvalidoException("Preencha todos os campos");
		}
		if (vendas == null || vendas.size() == 0) {
			throw new VendaInvalidaException("Erro: a consulta está vazia");
		}

		GeradorPlanilha gerador = new GeradorPlanilha();
		return gerador.gerarPlanilhaVendasComProdutos(vendas, destinoArquivo);
	}

	public ArrayList<Venda> consultarComFiltrosSemPaginacao(VendaSeletor seletor) {
		return vendaBO.consultarComFiltrosSemPaginacao(seletor);
	}

	public ArrayList<Venda> consultarComFiltros(VendaSeletor seletor) {
		return vendaBO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		return vendaBO.contarTotalRegistrosComFiltros(seletor);
	}
	
}
