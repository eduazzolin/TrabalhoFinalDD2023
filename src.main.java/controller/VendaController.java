package controller;

import model.exception.VendaInvalidaException;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

import java.util.ArrayList;

import model.bo.VendaBO;

public class VendaController {

	private VendaBO vendaBO = new VendaBO();
	
	public Venda cadastrarVenda(Venda venda) throws VendaInvalidaException {
		venda = vendaBO.cadastrarVenda(venda);
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
		// tudo ok! 
		return vendaBO.consultarComFiltros(seletor);
	}

}
