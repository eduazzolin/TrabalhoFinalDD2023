package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import controller.ItemVendaController;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;

public class VendaDAO {

	ItemVendaDAO itemVendaDAO = new ItemVendaDAO();
	
	/**
	 * Cadastra no banco na tabela VENDA a venda passada como parâmetro;
	 * Retorna um objeto Venda com o ID preenchido;
	 */
	public Venda cadastrarVenda(Venda venda) {

		String query = " INSERT INTO VENDA (DATA_VENDA, VALOR_TOTAL, QTDE_ITENS) VALUES (?, ?, ?) ";
		Connection conn = Banco.getConnection();
		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		
		double valorTotal = 0;
		int qtdeTotal = 0;
		for (ItemVenda iv : venda.getListaItemVenda()) {
				qtdeTotal += iv.getQtde();
				valorTotal += (iv.getValorUnitario() * iv.getQtde());
		}
		
		try {
			
			pstmt.setString(1, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			pstmt.setDouble(2, valorTotal);
			pstmt.setInt(3, qtdeTotal);
			pstmt.execute();
			resultado = pstmt.getGeneratedKeys();
			if (resultado.next()) {
				venda.setId(resultado.getInt(1));
				ItemVendaController itemVendaController = new ItemVendaController();
				itemVendaController.cadastrarMultiplosItemVenda(venda.getListaItemVenda(), venda.getId());
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

	/**
	 * Consulta no banco os registros da tabela VENDA com os atributos passados como parâmetro;
	 * Retorna um ArrayList das Vendas encontradas;
	 * Os joins foram em função do filtro de ean
	 */
	public ArrayList<Venda> consultarComFiltros(VendaSeletor seletor) {
		ArrayList<Venda> vendas = new ArrayList<Venda>();
		Connection conn = Banco.getConnection();
		String query = " SELECT VENDA.ID_VENDA, VENDA.DATA_VENDA, VENDA.VALOR_TOTAL, VENDA.QTDE_ITENS FROM VENDA "
				+ " LEFT JOIN ITEM_VENDA ON ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA "
				+ " LEFT JOIN PRODUTO ON ITEM_VENDA.ID_PRODUTO = PRODUTO.ID_PRODUTO ";
		
		if (seletor.temFiltro()) {
			query = preencherFiltros(query, seletor);
		}
		query += "  GROUP BY ID_VENDA ORDER BY 1 DESC ";
		if (seletor.temPaginacao()) {
			query += " LIMIT " + seletor.getLimite() + " OFFSET " + seletor.getOffset();
		}

		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			resultado = pstmt.executeQuery();
			while (resultado.next()) {
				Venda vendaBuscada = montarVendaPeloResultSet(resultado);
				vendas.add(vendaBuscada);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar vendas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vendas;

	}
	
	/**
	 * Conta no banco os registros da tabela VENDA com os atributos passados como parâmetro;
	 * Retorna um int da quantidade de registros encontrados;
	 */
	public int contarTotalRegistrosComFiltros(VendaSeletor seletor) {
		int total = 0;
		Connection conexao = Banco.getConnection();
		String query = " select count(DISTINCT(VENDA.ID_VENDA)) FROM VENDA "
				+ " LEFT JOIN ITEM_VENDA ON ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA "
				+ " LEFT JOIN PRODUTO ON ITEM_VENDA.ID_PRODUTO = PRODUTO.ID_PRODUTO ";
		
		if(seletor.temFiltro()) {
			query = preencherFiltros(query, seletor);
		}
		
		PreparedStatement pstmt = Banco.getPreparedStatement(conexao, query);
		try {
			ResultSet resultado = pstmt.executeQuery();
			
			if(resultado.next()) {
				total = resultado.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("Erro contar o total de vendas" 
					+ "\n Causa:" + e.getMessage());
		}finally {
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conexao);
		}
		
		return total;
	}

	/**
	 * Retorna um objeto Venda preenchido com o conteúdo de um ResultSet; 
	 */
	private Venda montarVendaPeloResultSet(ResultSet resultado) throws SQLException {
		Venda v = new Venda();
		v.setId(resultado.getInt("ID_VENDA"));
		v.setDataVenda(LocalDateTime.parse(resultado.getString("DATA_VENDA"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) );
		v.setQtdeItens(resultado.getInt("QTDE_ITENS"));
		v.setValorTotal(resultado.getDouble("VALOR_TOTAL"));
		v.setListaItemVenda(itemVendaDAO.consultarPorIdVenda(v.getId()));
		return v;
	}

	/**
	 * Preenche a cláusura where com os filtros do seletor;
	 */
	private String preencherFiltros(String query, VendaSeletor seletor) {
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
		if (seletor.getDataInicial() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " DATA_VENDA >= '" + seletor.getDataInicial() + "'";
			primeiro = false;
		}
		if (seletor.getDataFinal() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " DATA_VENDA <= '" + seletor.getDataFinal() + "'";
			primeiro = false;
		}
		if (seletor.getValorMaximo() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " VALOR_TOTAL <= " + seletor.getValorMaximo();
			primeiro = false;
		}
		if (seletor.getValorMinimo() != null) {
			if (primeiro) {
				query += " WHERE ";
			} else {
				query += " AND ";
			}
			query += " VALOR_TOTAL >= " + seletor.getValorMinimo();
			primeiro = false;
		}

		return query;
	}

	/**
	 * Remove do banco o registro da tabela VENDA da Venda passada como parâmetro;
	 * Retorna um boolean do resultado da remoção;
	 */
	public boolean removerVenda(Venda v) {
		Connection conn = Banco.getConnection();
		String query = "DELETE FROM VENDA WHERE ID_VENDA = " + v.getId();
		PreparedStatement pstmt = Banco.getPreparedStatement(conn, query);
		
		boolean resultado = false;
		try {
			if (itemVendaDAO.removerItemVendaPorIdVenda(v.getId())) {
				resultado = (pstmt.executeUpdate() > 0);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao excluir venda.");
			System.out.println("Erro: " + e.getMessage());
		}

		return resultado;
	}

	/**
	 * Consulta no banco os registros da tabela VENDA com os atributos passados como parâmetro;
	 * Ignora a paginação;
	 * Retorna um ArrayList das Vendas encontradas;
	 * Os joins foram em função do filtro de ean
	 */
	public ArrayList<Venda> consultarComFiltrosSemPaginacao(VendaSeletor seletor) {
		ArrayList<Venda> vendas = new ArrayList<Venda>();
		Connection conn = Banco.getConnection();
		String query = " SELECT VENDA.ID_VENDA, VENDA.DATA_VENDA, VENDA.VALOR_TOTAL, VENDA.QTDE_ITENS FROM VENDA "
				+ " LEFT JOIN ITEM_VENDA ON ITEM_VENDA.ID_VENDA = VENDA.ID_VENDA "
				+ " LEFT JOIN PRODUTO ON ITEM_VENDA.ID_PRODUTO = PRODUTO.ID_PRODUTO ";
		
		if (seletor.temFiltro()) {
			query = preencherFiltros(query, seletor);
		}
		query += "  GROUP BY ID_VENDA ORDER BY 1 DESC ";

		PreparedStatement pstmt = Banco.getPreparedStatementWithPk(conn, query);
		ResultSet resultado = null;
		try {
			resultado = pstmt.executeQuery();
			while (resultado.next()) {
				Venda vendaBuscada = montarVendaPeloResultSet(resultado);
				vendas.add(vendaBuscada);
			}
		} catch (SQLException erro) {
			System.out.println("Erro ao buscar vendas");
			System.out.println("Erro: " + erro.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closePreparedStatement(pstmt);
			Banco.closeConnection(conn);
		}
		return vendas;
	}

}
