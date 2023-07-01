package model.gerador;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.vo.ItemVenda;
import model.vo.Produto;
import model.vo.Venda;

public class GeradorPlanilha {

	public static final String MENSAGEM_SUCESSO = "Planilha gerada com sucesso!";

	public String gerarPlanilhaSomenteVendas(ArrayList<Venda> vendas, String destinoArquivo) {
		XSSFWorkbook arquivoExcel = new XSSFWorkbook();
		XSSFSheet abaPlanilha = arquivoExcel.createSheet("Vendas");

		XSSFRow linhaCabecalho = abaPlanilha.createRow(0);
		linhaCabecalho.createCell(0).setCellValue("Código");
		linhaCabecalho.createCell(1).setCellValue("Data");
		linhaCabecalho.createCell(2).setCellValue("Quantidade de itens");
		linhaCabecalho.createCell(3).setCellValue("Valor total");

		// configurações de largura das colunas
		abaPlanilha.autoSizeColumn(0);
		abaPlanilha.setColumnWidth(1, 18 * 256);
		abaPlanilha.autoSizeColumn(2);
		abaPlanilha.setColumnWidth(3, 21 * 256);

		int contadorLinhas = 1;
		for (Venda v : vendas) {
			XSSFRow novaLinha = abaPlanilha.createRow(contadorLinhas);
			novaLinha.createCell(0).setCellValue(v.getId());
			novaLinha.createCell(1)
					.setCellValue(v.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			novaLinha.createCell(2).setCellValue(v.getQtdeItens());
			novaLinha.createCell(3).setCellValue(String.format("R$ %.2f", v.getValorTotal()));
			contadorLinhas++;
		}

		return salvarNoDisco(arquivoExcel, destinoArquivo);
	}

	public String gerarPlanilhaVendasComProdutos(ArrayList<Venda> vendas, String destinoArquivo) {
		XSSFWorkbook arquivoExcel = new XSSFWorkbook();
		XSSFSheet abaPlanilha = arquivoExcel.createSheet("Vendas");

		XSSFRow linhaCabecalho = abaPlanilha.createRow(0);
		linhaCabecalho.createCell(0).setCellValue("Código Venda");
		linhaCabecalho.createCell(1).setCellValue("Data");
		linhaCabecalho.createCell(2).setCellValue("Quantidade de itens");
		linhaCabecalho.createCell(3).setCellValue("Valor total");
		linhaCabecalho.createCell(4).setCellValue("Código Produto");
		linhaCabecalho.createCell(5).setCellValue("Nome");
		linhaCabecalho.createCell(6).setCellValue("EAN");
		linhaCabecalho.createCell(7).setCellValue("Quantidade");
		linhaCabecalho.createCell(8).setCellValue("Valor Unitário");

		// configurações de largura das colunas
		abaPlanilha.autoSizeColumn(0);
		abaPlanilha.setColumnWidth(1, 18 * 256);
		abaPlanilha.autoSizeColumn(2);
		abaPlanilha.setColumnWidth(3, 21 * 256);
		abaPlanilha.autoSizeColumn(4);
		abaPlanilha.setColumnWidth(5, 50 * 256);
		abaPlanilha.setColumnWidth(6, 18 * 256);
		abaPlanilha.autoSizeColumn(7);
		abaPlanilha.setColumnWidth(8, 21 * 256);

		int contadorLinhas = 1;
		for (Venda v : vendas) {
			for (ItemVenda iv : v.getListaItemVenda()) {
				XSSFRow novaLinha = abaPlanilha.createRow(contadorLinhas);
				novaLinha.createCell(0).setCellValue(v.getId());
				novaLinha.createCell(1)
						.setCellValue(v.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
				novaLinha.createCell(2).setCellValue(v.getQtdeItens());
				novaLinha.createCell(3).setCellValue(String.format("R$ %.2f", v.getValorTotal()));
				novaLinha.createCell(4).setCellValue(iv.getProduto().getId());
				novaLinha.createCell(5).setCellValue(iv.getProduto().getNome());
				novaLinha.createCell(6).setCellValue(iv.getProduto().getEan());
				novaLinha.createCell(7).setCellValue(iv.getQtde());
				novaLinha.createCell(8).setCellValue(String.format("R$ %.2f", iv.getValorUnitario()));
				contadorLinhas++;
			}
		}

		return salvarNoDisco(arquivoExcel, destinoArquivo);
	}

	private String salvarNoDisco(XSSFWorkbook arquivoExcel, String destinoArquivo) {
		String mensagem = "";
		FileOutputStream saida = null;
		String extensao = ".xlsx";

		try {
			saida = new FileOutputStream(new File(destinoArquivo + extensao));
			arquivoExcel.write(saida);
			mensagem = MENSAGEM_SUCESSO;
		} catch (FileNotFoundException e) {
			mensagem = "Erro ao tentar salvar planilha (sem acesso): " + destinoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} catch (IOException e) {
			mensagem = "Erro de I/O ao tentar salvar planilha em: " + destinoArquivo + extensao;
			System.out.println("Causa: " + e.getMessage());
		} finally {
			if (saida != null) {
				try {
					saida.close();
					arquivoExcel.close();
				} catch (IOException e) {
					mensagem = "Erro ao tentar salvar planilha em: " + destinoArquivo + extensao;
					System.out.println("Causa: " + e.getMessage());
				}
			}
		}

		return mensagem;
	}

	public String gerarPlanilhaProdutos(ArrayList<Produto> produtos, String destinoArquivo) {
		XSSFWorkbook arquivoExcel = new XSSFWorkbook();
		XSSFSheet abaPlanilha = arquivoExcel.createSheet("Produtos");

		XSSFRow linhaCabecalho = abaPlanilha.createRow(0);
		linhaCabecalho.createCell(0).setCellValue("ID");
		linhaCabecalho.createCell(1).setCellValue("NOME");
		linhaCabecalho.createCell(2).setCellValue("DESCRIÇÃO");
		linhaCabecalho.createCell(3).setCellValue("EAN");
		linhaCabecalho.createCell(4).setCellValue("VALOR");
		linhaCabecalho.createCell(5).setCellValue("ESTOQUE");
		linhaCabecalho.createCell(6).setCellValue("STATUS");

		// configurações de largura das colunas
		abaPlanilha.setColumnWidth(0, 6 * 256);
		abaPlanilha.setColumnWidth(1, 45 * 256);
		abaPlanilha.setColumnWidth(2, 45 * 256);
		abaPlanilha.setColumnWidth(3, 16 * 256);
		abaPlanilha.setColumnWidth(4, 18 * 256);
		abaPlanilha.setColumnWidth(5, 18 * 256);
		abaPlanilha.setColumnWidth(6, 10 * 256);

		int contadorLinhas = 1;
		for (Produto p : produtos) {
			XSSFRow novaLinha = abaPlanilha.createRow(contadorLinhas);
			novaLinha.createCell(0).setCellValue(p.getId());
			novaLinha.createCell(1).setCellValue(p.getNome());
			novaLinha.createCell(2).setCellValue(p.getDescricao());
			novaLinha.createCell(3).setCellValue(p.getEan());
			novaLinha.createCell(4).setCellValue(String.format("R$ %.2f", p.getValor()));
			novaLinha.createCell(5).setCellValue(p.getEstoque());
			novaLinha.createCell(6).setCellValue((p.isAtivo() ? "Ativo" : "Desativado"));
			contadorLinhas++;
		}

		return salvarNoDisco(arquivoExcel, destinoArquivo);
	}

}
