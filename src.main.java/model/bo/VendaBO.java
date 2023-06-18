package model.bo;

import model.exception.VendaInvalidaException;
import model.vo.ItemVenda;
import model.vo.Venda;
import model.dao.VendaDAO;

public class VendaBO {

	private VendaDAO vendaDAO = new VendaDAO();
	
	public Venda cadastrarVenda(Venda venda) throws VendaInvalidaException {
		// verificando se a venda não está vazia:
		if (venda.getListaItemVenda() == null && venda.getListaItemVenda().size() == 0) {
			throw new VendaInvalidaException("Venda vazia");
		}
		// TODO: Verificando se todos os produtos tem estoque:
//		for (ItemVenda iv : venda.getListaItemVenda()) {
//			if (iv.getQtde() > CONSULTARESTOQUENOBANCO()) {
//				
//			}
//		}
		return vendaDAO.cadastrarVenda(venda);
	}

}
