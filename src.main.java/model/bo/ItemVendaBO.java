package model.bo;

import java.util.ArrayList;

import model.vo.ItemVenda;
import model.dao.ItemVendaDAO;

public class ItemVendaBO {

	ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
	
	public void cadastrarMultiplosItemVenda(ArrayList<ItemVenda> listaItemVenda, int idVenda) {
		for (ItemVenda iv : listaItemVenda) {
			iv.setIdVenda(idVenda);
			iv = itemVendaDAO.cadastrarItemVenda(iv);
		}
	}

}
