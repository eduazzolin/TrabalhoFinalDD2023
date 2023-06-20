package model.bo;

import model.exception.VendaInvalidaException;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

import java.util.ArrayList;

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

	public ArrayList<Venda> consultarComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!
		return vendaDAO.consultarComFiltros(seletor);
	}

	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		// tudo ok! passa adiante!
		return vendaDAO.contarTotalRegistrosComFiltros(seletor);
	}

}
