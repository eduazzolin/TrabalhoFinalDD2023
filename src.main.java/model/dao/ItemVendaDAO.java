package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.bo.ItemVendaBO;
import model.vo.ItemVenda;

public class ItemVendaDAO {

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
				iv.setId(resultado.getInt(1));
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

}
