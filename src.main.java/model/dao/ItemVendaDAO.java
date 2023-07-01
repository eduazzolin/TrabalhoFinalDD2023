package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.bo.ItemVendaBO;
import model.vo.ItemVenda;
import model.vo.Produto;

public class ItemVendaDAO {

	ProdutoDAO produtoDAO = new ProdutoDAO();

	public ItemVenda cadastrarItemVenda(ItemVenda iv) {
		String query = " INSERT INTO ITEM_VENDA (ID_VENDA, ID_PRODUTO, QTDE_PRODUTO, VALOR_UNITARIO) VALUES (?, ?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;

		try {
			pstmt.setInt(1, iv.getIdVenda());
			pstmt.setInt(2, iv.getProduto().getId());
			pstmt.setInt(3, iv.getQtde());
			pstmt.setDouble(4, iv.getValorUnitario());
			pstmt.execute();
			resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				if (produtoDAO.atualizarEstoque(iv.getQtde() * -1, iv.getProduto())) {
					iv.setId(resultado.getInt(1));
				}
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar Item_Venda");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return iv;
	}

	public boolean removerItemVendaPorIdVenda(int id) {
		String query = "DELETE FROM ITEM_VENDA WHERE ID_VENDA = " + id;
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);

		boolean resultado = false;
		try {
			resultado = (pstmt.executeUpdate() > 0);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir item_venda.");
			System.out.println("Erro: " + e.getMessage());
		}
		return resultado;
	}

	public ArrayList<ItemVenda> consultarPorIdVenda(int idVenda) {
		ArrayList<ItemVenda> itemVendas = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from item_venda where id_venda = " + idVenda;

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				ItemVenda ivBuscado = new ItemVenda();
				ivBuscado.setId(resultado.getInt("ID_ITEM_VENDA"));
				ivBuscado.setIdVenda(idVenda);
				ivBuscado.setProduto(produtoDAO.buscarProdutoPorId(resultado.getInt("ID_PRODUTO")));
				ivBuscado.setQtde(resultado.getInt("QTDE_PRODUTO"));
				ivBuscado.setValorUnitario(resultado.getDouble("VALOR_UNITARIO"));
				itemVendas.add(ivBuscado);
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar item_venda. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return itemVendas;
	}

}
