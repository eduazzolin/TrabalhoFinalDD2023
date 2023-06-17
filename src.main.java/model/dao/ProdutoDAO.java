package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.vo.Produto;

public class ProdutoDAO {

	public ArrayList<Produto> buscarProdutosPorNomeOuEan(String nomeOuEan) {
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from produto where nome like '%" + nomeOuEan + "%' or ean like '%" + nomeOuEan + "%' ORDER BY NOME ASC ";

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				Produto produtoBuscado = new Produto();
				produtoBuscado.setId(resultado.getInt("ID_PRODUTO"));
				produtoBuscado.setNome(resultado.getString("NOME"));
				produtoBuscado.setDescricao(resultado.getString("DESCRICAO"));
				produtoBuscado.setEan(resultado.getString("EAN"));
				produtoBuscado.setEstoque(resultado.getInt("ESTOQUE"));
				produtoBuscado.setValor(resultado.getDouble("VALOR"));
				produtos.add(produtoBuscado);
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return produtos;
	}

}
