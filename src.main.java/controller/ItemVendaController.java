package controller;

import java.util.ArrayList;

import model.dao.ItemVendaDAO;
import model.vo.ItemVenda;

public class ItemVendaController {
	
	ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
	
	public void cadastrarMultiplosItemVenda(ArrayList<ItemVenda> listaItemVenda, int idVenda) {
		for (ItemVenda iv : listaItemVenda) {
			iv.setIdVenda(idVenda);
			iv = itemVendaDAO.cadastrarItemVenda(iv);
		}
	}

}
