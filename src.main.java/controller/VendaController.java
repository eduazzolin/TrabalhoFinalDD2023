package controller;

import model.exception.VendaInvalidaException;
import model.vo.ItemVenda;
import model.vo.Venda;
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

}
