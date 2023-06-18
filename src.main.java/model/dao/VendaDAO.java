package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.vo.Venda;
import model.bo.ItemVendaBO;

public class VendaDAO {

	public Venda cadastrarVenda(Venda venda) {

		String query = " INSERT INTO VENDA (DATA_VENDA) VALUES (?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;

		try {
			pstmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			pstmt.execute();
			resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				venda.setId(resultado.getInt(1));
				ItemVendaBO itemVendaBO = new ItemVendaBO();
				itemVendaBO.cadastrarMultiplosItemVenda(venda.getListaItemVenda(), venda.getId());
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao cadastrar venda");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return venda;

	}

}
