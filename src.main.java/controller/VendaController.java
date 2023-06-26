package controller;

import model.exception.CampoInvalidoException;
import model.exception.VendaInvalidaException;
import model.gerador.GeradorPlanilha;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

import java.util.ArrayList;

import model.bo.VendaBO;

public class VendaController {

	private VendaBO vendaBO = new VendaBO();
	
	public Venda cadastrarVenda(Venda venda) throws VendaInvalidaException {
		// validações pré insert:
		if (venda.getListaItemVenda() == null || venda.getListaItemVenda().size()==0) {
			throw new VendaInvalidaException("Erro: A venda não pode estar vazia!");
		}
		for (ItemVenda iv : venda.getListaItemVenda()) {
			if (!iv.getProduto().isAtivo()) {
				throw new VendaInvalidaException("Erro: A venda possui produtos desativados!");
			}
		}
		
		// insert
		venda = vendaBO.cadastrarVenda(venda);
		
		// validações pós insert:
		if (venda.getId() == 0) {
			throw new VendaInvalidaException("Erro ao cadastrar venda!");
		}
		for (ItemVenda iv : venda.getListaItemVenda()) {
			if (iv.getId() == 0) {
				throw new VendaInvalidaException("Erro ao cadastrar venda!");
			}
		}
		return venda;
	}

	public ArrayList<Venda> consultarComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!
		return vendaBO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!
		return vendaBO.contarTotalRegistrosComFiltros(seletor);
	}

	public boolean removerVenda(Venda v) throws VendaInvalidaException {
		if (v == null || v.getId() == 0) {
			throw new VendaInvalidaException("Venda inválida, selecione novamente.");
		}
		return vendaBO.removerVenda(v);
	}

	public String gerarPlanilhaSomenteVendas(ArrayList<Venda> vendas, String destinoArquivo) throws CampoInvalidoException, VendaInvalidaException {
		if(destinoArquivo == null || destinoArquivo.trim().isEmpty()) {
			throw new CampoInvalidoException("Preencha todos os campos");
		}
		if(vendas == null) {
			throw new VendaInvalidaException("Erro: a venda está vazia");
		}
		
		GeradorPlanilha gerador = new GeradorPlanilha();
		return gerador.gerarPlanilhaSomenteVendas(vendas, destinoArquivo);
	}
	public String gerarPlanilhaVendasComProdutos(ArrayList<Venda> vendas, String destinoArquivo) throws CampoInvalidoException, VendaInvalidaException {
		if(destinoArquivo == null || destinoArquivo.trim().isEmpty()) {
			throw new CampoInvalidoException("Preencha todos os campos");
		}
		if(vendas == null) {
			throw new VendaInvalidaException("Erro: a venda está vazia");
		}
		
		GeradorPlanilha gerador = new GeradorPlanilha();
		return gerador.gerarPlanilhaVendasComProdutos(vendas, destinoArquivo);
	}

}
