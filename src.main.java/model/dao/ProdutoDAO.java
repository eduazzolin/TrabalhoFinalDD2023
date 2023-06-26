package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.vo.Produto;

public class ProdutoDAO {

	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) {
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from produto where ativo = TRUE and (nome like '%" + nomeOuEan + "%' or ean like '%" + nomeOuEan + "%') ORDER BY NOME ASC ";

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
				produtoBuscado.setAtivo(resultado.getBoolean("ATIVO"));
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

	public ArrayList<Produto> buscarTodosProdutos() {
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from produto";

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
				produtoBuscado.setAtivo(resultado.getBoolean("ATIVO"));
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
	
	public Produto buscarProdutoPorId(int id) {
		Produto produtoBuscado = null;
		Connection conn = Banco.getConnection();
		String query = " select * from produto where id_produto = " + id;
		
		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				produtoBuscado = new Produto();
				produtoBuscado.setId(resultado.getInt("ID_PRODUTO"));
				produtoBuscado.setNome(resultado.getString("NOME"));
				produtoBuscado.setDescricao(resultado.getString("DESCRICAO"));
				produtoBuscado.setEan(resultado.getString("EAN"));
				produtoBuscado.setEstoque(resultado.getInt("ESTOQUE"));
				produtoBuscado.setValor(resultado.getDouble("VALOR"));
				produtoBuscado.setAtivo(resultado.getBoolean("ATIVO"));
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return produtoBuscado;
	}

	public int ConstularEstoque(int id) {
		int estoque = 0;
		String query = "select estoque from produto where id_produto =" + id;
		Connection conn = Banco.getConnection();

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
			estoque=resultado.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoque;
	}

	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado) {
		boolean retorno = false;
		
		String query = "update produto set estoque = estoque + " + quantidadeDigitada + " where id_produto = " + produtoSelecionado.getId();
		
		Connection conn = Banco.getConnection();

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			if(stmt.executeUpdate(query)== 1) {
				retorno = true;
			}
		} catch (Exception e) {
			System.out.println("Erro ao atualizar os produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

}
