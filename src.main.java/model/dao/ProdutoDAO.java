package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.seletor.ProdutoSeletor;
import model.vo.Produto;

public class ProdutoDAO {

	/**
	 * Consulta no banco na tabela PRODUTOS com o trecho do nome ou EAN passado como parâmetro;
	 * Retorna um ArrayList dos Produtos encontrados;
	 */
	public ArrayList<Produto> buscarProdutosAtivosPorNomeOuEan(String nomeOuEan) {
		nomeOuEan = nomeOuEan.replace("'", "''");
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from produto where ativo = TRUE and (nome like '%" + nomeOuEan + "%' or ean like '%"
				+ nomeOuEan + "%') ORDER BY NOME ASC ";

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

	/**
	 * Consulta no banco todos os registros da tabela PRODUTO;
	 * Retorna um ArrayList dos Produtos encontrados;
	 */
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

	/**
	 * Consulta no banco o registro da tabela PRODUTO com o ID_PRODUTO passado como parâmetro;
	 * Retorna um objeto Produto do produto encontrado;
	 */
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

	/**
	 * Consulta no banco o estoque da tabela PRODUTO com o ID_PRODUTO passado como parâmetro;
	 * Retorna um int do estoque encontrado;
	 */
	public int consultarEstoque(int id) {
		int estoque = 0;
		String query = "select estoque from produto where id_produto =" + id;
		Connection conn = Banco.getConnection();

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			ResultSet resultado = stmt.executeQuery();
			while (resultado.next()) {
				estoque = resultado.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return estoque;
	}

	/**
	 * Atualiza no banco o estoque da tabela PRODUTO para o produto e quantidade passados como parâmetro;
	 * Retorna um boolean do resultado da atualização;
	 */
	public boolean atualizarEstoque(int quantidadeDigitada, Produto produtoSelecionado) {
		boolean retorno = false;

		String query = "update produto set estoque = estoque + " + quantidadeDigitada + " where id_produto = "
				+ produtoSelecionado.getId();

		Connection conn = Banco.getConnection();

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			if (stmt.executeUpdate(query) == 1) {
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

	/**
	 * Cadastra no banco na tabela PRODUTO o produto passado como parâmetro;
	 * Retorna um boolean do resultado da inserção;
	 */
	public boolean criarProduto(Produto produtoNovo) {
		boolean retorno = false;

		produtoNovo.setDescricao(produtoNovo.getDescricao().replace("'", "''"));
		produtoNovo.setNome(produtoNovo.getNome().replace("'", "''"));
		produtoNovo.setEan(produtoNovo.getEan().replace("'", "''"));
		
		String query = "INSERT INTO PRODUTO (NOME, DESCRICAO, EAN, VALOR, ESTOQUE) VALUES ('" + produtoNovo.getNome()
				+ "'," + " '" + produtoNovo.getDescricao() + "', '" + produtoNovo.getEan() + "', "
				+ produtoNovo.getValor() + ", 0);";
		Connection conn = Banco.getConnection();

		PreparedStatement stmt = Banco.getPreparedStatement(conn, query);
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (Exception e) {
			System.out.println("Erro ao cadastrar os produtos. \n Causa:" + e.getMessage());
		} finally {
			Banco.closePreparedStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	/**
	 * Consulta no banco os registros da tabela PRODUTO com os atributos passados como parâmetro;
	 * Retorna um ArrayList dos Produtos encontrados;
	 */
	public ArrayList<Produto> consultarComFiltos(ProdutoSeletor seletor) {
		
		ArrayList<Produto> produtos = new ArrayList<Produto>();
		Connection conn = Banco.getConnection();
		String query = " SELECT * FROM produto ";

		if (seletor.temFiltro()) {
			query = preencherFiltros(query, seletor);
		}
		query += "  GROUP BY ID_PRODUTO ORDER BY 1 DESC ";
		if (seletor.temPaginacao()) {
			query += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset();
		}

		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			resultado = pstmt.executeQuery();
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
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar vendas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return produtos;
	}

	/**
	 * Preenche a cláusura where com os filtros do seletor;
	 */
	private String preencherFiltros(String query, ProdutoSeletor seletor) {
		
		seletor.setNome(seletor.getNome().replace("'", "''"));
		seletor.setEan(seletor.getEan().replace("'", "''"));
		
		boolean primeiro = true;
		if (seletor.getEan() != null && seletor.getEan().trim().length() > 0) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " ean = '" + seletor.getEan() + "' ";
			primeiro = false;
		}
		if (seletor.getNome() != null && seletor.getNome().trim().length() > 0) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " nome like '%" + seletor.getNome() + "%' ";
			primeiro = false;
		}
		if (seletor.getValorMaximo() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " VALOR <= " + seletor.getValorMaximo();
			primeiro = false;
		}
		if (seletor.getValorMinimo() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " VALOR >= " + seletor.getValorMinimo();
			primeiro = false;
		}

		return query;
	}

	/**
	 * Conta no banco os registros da tabela PRODUTO com os atributos passados como parâmetro;
	 * Retorna um int da quantidade de registros encontrados;
	 */
	public int contarTotalRegistrosComFiltros(ProdutoSeletor seletor) {
		int quantidade = 0;
		Connection conn = Banco.getConnection();
		String query = " SELECT COUNT(*) FROM produto ";
		if (seletor.temFiltro()) {
			query = preencherFiltros(query, seletor);
		}
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			resultado = pstmt.executeQuery();
			if (resultado.next()) {
				quantidade = resultado.getInt(1);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar vendas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return quantidade;

	}

	/**
	 * Atualiza no banco o atributo ativo da tabela PRODUTO para false para o ID_PRODUTO passado como parâmetro;
	 * Retorna um boolean do resultado da atualização;
	 */
	public boolean removerProduto(Produto produtoSelecionado) {
		String query = "UPDATE produto set ativo = false where ID_PRODUTO = " + produtoSelecionado.getId();
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);

		boolean resultado = false;
		try {
			resultado = (pstmt.executeUpdate() > 0);

		} catch (SQLException e) {
			System.out.println("Erro ao excluir produto.");
			System.out.println("Erro: " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Atualiza no banco o produto passado como parâmetro;
	 * Retorna um boolean do resultado da atualização;
	 */
	public boolean editarProduto(Produto p) {
		
		p.setDescricao(p.getDescricao().replace("'", "''"));
		p.setNome(p.getNome().replace("'", "''"));
		p.setEan(p.getEan().replace("'", "''"));
		
		Connection conn = Banco.getConnection();
		String query = "UPDATE PRODUTO SET " + " NOME = ?," + " DESCRICAO = ?," + " EAN = ?," + " VALOR = ?"
				+ " WHERE ID_PRODUTO = ?";
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);

		boolean resultado = false;
		try {
			pstmt.setString(1, p.getNome());
			pstmt.setString(2, p.getDescricao());
			pstmt.setString(3, p.getEan());
			pstmt.setDouble(4, p.getValor());
			pstmt.setInt(5, p.getId());
			resultado = (pstmt.executeUpdate() > 0);
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar produto.");
			System.out.println("Erro: " + e.getMessage());
		}
		return resultado;
	}

	/**
	 * Consulta no banco os registros da tabela PRODUTOS com o atributo ATIVO true; 
	 * Retorna um ArrayList dos Produtos encontrados;
	 */
	public ArrayList<Produto> buscarTodosProdutosAtivados() {
		ArrayList<Produto> produtos = new ArrayList<>();
		Connection conn = Banco.getConnection();
		String query = " select * from produto where ativo = true";

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

}
