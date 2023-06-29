package view.paineis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ItemVendaController;
import controller.ProdutoController;
import controller.VendaController;
import model.exception.CampoInvalidoException;
import model.exception.VendaInvalidaException;
import model.seletor.VendaSeletor;
import model.vo.Venda;
import view.componentesExternos.JNumberFormatField;
import view.dialogs.DialogVerProdutos;

public class PainelConsultarVenda extends JPanel {
	
	// componentes visuais:
	private JTable table;
	private JTextField tfEan;
	private DatePicker dtInicial;
	private DatePicker dtFinal;
	private JLabel lbFiltrarConsulta;
	private JLabel lbEan;
	private JLabel lbValorMinimo;
	private JLabel lbValorMaximo;
	private JLabel lbDataInicial;
	private JLabel lbDataFinal;
	private JLabel lbResultados;
	private JLabel lbPaginas;
	private JNumberFormatField ftfValorMinimo;
	private JNumberFormatField ftfValorMaximo;
	private JButton btnConsultar;
	private JButton btnVerProdutos;
	private JButton btnExportar;
	private JButton btnVoltar;
	private JButton btnAvancar;
	private JButton btnRemover;
	private JSeparator separator;
	private JSeparator separator2;
	
	// classes mvc:
	protected VendaSeletor seletor;
	private VendaController vendaController = new VendaController();
	private ProdutoController produtoController = new ProdutoController();
	
	// atributos simples:
	private Double valorMaximo;
	private Double valorMinimo;
	private String[] nomesColunas = {  "Código", "Data", "Quantidade de itens", "Valor total" };
	protected ArrayList<Venda> vendas;
	protected Venda vendaSelecionada;
	
	// atributos da paginação
	private final int TAMANHO_PAGINA = 15;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	
	
	/**
	 * Create the panel.
	 */
	public PainelConsultarVenda() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(35dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(36dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(25dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(35dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(15dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(21dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(223dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(23dlu;default):grow"),}));
		
		
		// Configurações da parte de DATAS do componente
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		// declaração dos componentes visuais:
		lbFiltrarConsulta = new JLabel("Filtrar consulta:");
		add(lbFiltrarConsulta, "4, 4, left, bottom");
		
		separator = new JSeparator();
		add(separator, "4, 6, 17, 1, default, top");
		
		lbEan = new JLabel("EAN:");
		add(lbEan, "4, 7, right, default");
		
		tfEan = new JTextField();
		add(tfEan, "6, 7, 15, 1, fill, default");
		tfEan.setColumns(10);
		
		lbValorMinimo = new JLabel("Valor mínimo:");
		add(lbValorMinimo, "4, 9, right, default");
		
		ftfValorMinimo = new JNumberFormatField(2);
		add(ftfValorMinimo, "6, 9, 5, 1, fill, default");
		
		lbValorMaximo = new JLabel("Valor máximo:");
		add(lbValorMaximo, "12, 9, right, default");
		
		ftfValorMaximo = new JNumberFormatField(2);
		add(ftfValorMaximo, "14, 9, 7, 1, fill, default");
		
		lbDataInicial = new JLabel("Data inicial:");
		add(lbDataInicial, "4, 11, right, default");

		lbPaginas = new JLabel("");
		lbPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbPaginas, "6, 25");
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 17");
		
		separator2 = new JSeparator();
		add(separator2, "4, 19, 17, 1, default, top");
		
		dtInicial = new DatePicker();
		dtInicial.setBounds(160, 90, 515, 30);
		add(dtInicial, "6, 11, 5, 1, fill, default");
		
		lbDataFinal = new JLabel("Data final:");
		add(lbDataFinal, "12, 11, right, default");
		
		dtFinal = new DatePicker();
		dtFinal.setBounds(160, 90, 515, 30);
		add(dtFinal, "14, 11, 7, 1, fill, default");
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setMaximumSize(new Dimension(50, 21));
		add(btnConsultar, "18, 13, 3, 1");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoConsultar();
			}
		});
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		add(table, "4, 21, 17, 3, fill, fill");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoCliqueTabela();
			}
		});
		limparTabela();
		
		btnVoltar = new JButton("<< Voltar");
		btnVoltar.setEnabled(false);
		add(btnVoltar, "4, 25");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVoltar();
			}
		});
		
		btnAvancar = new JButton("Avançar >>");
		btnAvancar.setEnabled(false);
		add(btnAvancar, "8, 25");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoAvancar();
			}
		});
		
		btnVerProdutos = new JButton("Ver produtos");
		btnVerProdutos.setEnabled(false);
		add(btnVerProdutos, "18, 25");
		btnVerProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVerProdutos();
			}
		});
		
		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		add(btnRemover, "16, 25");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoRemover();
			}
		});
		
		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		add(btnExportar, "20, 25");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoExportar();
			}
		});
		
	}

	/**
	 * Busca os elementos no banco de dados com os filtros:
	 * 
	 * Valida se os campos "máximos" são maiores que os "mínimos".
	 * Busca no banco com os filtros;
	 * Atualiza a tabela;
	 * Atualiza a paginação;
	 */
	protected void acaoBotaoConsultar() {
		try {
			paginaAtual = 1;
			totalPaginas = 0;
			buscarVendasComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btnConsultar, e1.getMessage(), "Campo inválido", 1);
		}
	}

	/**
	 * Remove do banco de dados a venda selecionada e seus respectivos ITEM_VENDAs:
	 * 
	 * Valida se a seleção está funcionando;
	 * Remove do banco de dados;
	 * Atualiza a tabela e os botões "remover" e "ver produtos".
	 */
	protected void acaoBotaoRemover() {
		try {
			if(vendaController.removerVenda(vendaSelecionada)) {
				JOptionPane.showMessageDialog(btnRemover, "Venda removida com sucesso!", "Sucesso", 1);
				acaoBotaoConsultar();
				btnRemover.setEnabled(false);
				btnVerProdutos.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(btnRemover, "Erro ao remover venda", "Erro", 1);
			}
		} catch (VendaInvalidaException e) {
			JOptionPane.showMessageDialog(btnRemover, e.getMessage(), "Erro", 1);
		}
		
	}

	/**
	 * Abre o dialog "DialogVerProdutos" que consiste em uma tabela que mostra os produtos incluídos na venda:
	 * 
	 * Abre o dialog "DialogVerProdutos" passando o ArrayList "listaItemVenda" do objeto vendaSelecionada;
	 */
	protected void acaoBotaoVerProdutos() {
		DialogVerProdutos verProdutos = new DialogVerProdutos();
		verProdutos.setLocationRelativeTo(null);
		verProdutos.atualizarTabelaELabel(vendaSelecionada);
		verProdutos.setVisible(true);
		
	}

	/**
	 * Retorna à página anterior:
	 * 
	 * Diminui 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoVoltar() {
		paginaAtual--;
		try {
			buscarVendasComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
	}
	
	/**
	 * Avança para a página seguinte:
	 * 
	 * Aumenta 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoAvancar() {
		paginaAtual++;
		try {
			buscarVendasComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
		
	}
	
	/**
	 * Ativa ou desativa os botões de navegação a partir da validação das páginas.
	 */
	private void ativarOuDesativarBotoesVoltarAvancar() {
		btnVoltar.setEnabled(paginaAtual > 1);
		btnAvancar.setEnabled(paginaAtual < totalPaginas);
	}



	/**
	 * Busca vendas no banco com os filtros e atribui o resultado à tabela:
	 * 
	 * Cria um objeto seletor a partir dos filtros inseridos e atributos de paginação;
	 * Valida os valores máximos dos filtros para confirmar se eles não são menores que os mínimos;
	 * Se os campos de valor estiverem com 0 (valor padrão) atribui "null";
	 * Busca no banco com os filtros e atribui o resultado ao ArrayList "vendas";
	 * Atualiza a tabela, a paginação e os botões de navegação e exportar;
	 * @throws CampoInvalidoException;
	 */
	private void buscarVendasComFiltrosEAtualizarTabela() throws CampoInvalidoException {
		// criação do seletor e validação:
		seletor = new VendaSeletor();
		seletor.setLimite(TAMANHO_PAGINA);
		seletor.setPagina(paginaAtual);
		preencherSeletor(seletor);
		
		// busca no banco e atualização:
		vendas = vendaController.consultarComFiltros(seletor);
		atualizarTabela();
		atualizarQuantidadePaginas();
		ativarOuDesativarBotoesVoltarAvancar();
		btnExportar.setEnabled(vendas != null && vendas.size()>0);
	}

	private void preencherSeletor(VendaSeletor s) throws CampoInvalidoException {
		valorMinimo = ftfValorMinimo.getValue().doubleValue();
		valorMaximo = ftfValorMaximo.getValue().doubleValue();
		if (dtFinal.getDate() != null && dtInicial.getDate() != null && dtFinal.getDate().isBefore(dtInicial.getDate())) {
			throw new CampoInvalidoException("Data final não pode ser anterior à data inicial.");
		}
		if (valorMinimo > valorMaximo) {
			throw new CampoInvalidoException("Valor mínimo não pode ser maior que o valor máximo.");
		}
		s.setDataFinal(dtFinal.getDate());
		s.setDataInicial(dtInicial.getDate());
		s.setEan(tfEan.getText());
		s.setValorMaximo(valorMaximo > 0 ? valorMaximo : null);
		s.setValorMinimo(valorMinimo > 0 ? valorMinimo : null);
	}

	/**
	 * Deixa a tabela somente com o cabeçalho padrão;
	 */
	private void limparTabela() {
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas }, nomesColunas));
		table.setRowHeight(20);
	}
	
	/**
	 * Atualiza a tabela baseada no ArrayList "vendas":
	 * 
	 * Limpa a tabela deixando somente o cabeçalho padrão;
	 * Pega a referência do model da tabela e adiciona nele uma linha para cada Venda no ArrayList vendas;
	 */
	private void atualizarTabela() {
		this.limparTabela();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Venda v : this.vendas) {
			Object[] novaLinhaDaTabela = new Object[4];
			novaLinhaDaTabela[0] = v.getId();
			novaLinhaDaTabela[1] = v.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			novaLinhaDaTabela[2] = v.getQtdeItens();
			novaLinhaDaTabela[3] = String.format("R$ %.2f", v.getValorTotal());
			model.addRow(novaLinhaDaTabela);
		}
	}
	
	/**
	 * Calcula e atualiza os atributos e labels de paginação:
	 * 
	 * Busca no banco quantas linhas retornam com os filtros;
	 * Calcula o total de páginas dividindo o total de linhas pelo tamanho da página
	 * Caso o resultado seja um número decimal, o total é arredondado para cima;
	 * Atualiza o lbPaginas deixando sempre o total como no mínimo 1;
	 */
	private void atualizarQuantidadePaginas() {
		int totalRegistros = vendaController.contarTotalRegistrosComFiltros(seletor);
		totalPaginas = totalRegistros / TAMANHO_PAGINA;
		if(totalRegistros % TAMANHO_PAGINA > 0) { 
			totalPaginas++;
		}
		lbPaginas.setText(paginaAtual + " / " + (totalPaginas == 0 ? 1 : totalPaginas));
	}

	/**
	 * Atribui a venda da linha selecionada ao objeto "vendaSelecionada":
	 * 
	 * Valida se a seleção não está no cabeçalho (índice 0);
	 * Atribui a venda selecionada ao objeto "vendaSelecionada" através do índice da linha e do índice do array "vendas";
	 * Atualiza os botões "remover" e "VerProdutos";
	 */
	private void acaoCliqueTabela() {
		int indiceSelecionado = table.getSelectedRow();
		if (indiceSelecionado > 0) {
			btnVerProdutos.setEnabled(true);
			btnRemover.setEnabled(true);
			vendaSelecionada = vendas.get(indiceSelecionado - 1);
		} else {
			btnVerProdutos.setEnabled(false);
			btnRemover.setEnabled(false);
		}
	}

	/**
	 * Exporta os resultados para uma planilha excel .xlsx:
	 * 
	 * Exibe um JOptionPane perguntando o tipo do relatório;
	 * Exibe um JOptionPane perguntando onde quer salvar;
	 * Busca no banco os resultados com os filtros porém sem paginação
	 * Valida se o destino foi escolhido e se a consulta não está vazia;
	 * Exporta a planilha;
	 */
	private void acaoBotaoExportar() {
		int escolhaTipoRelatorio = JOptionPane.showOptionDialog(null, "Qual tipo de relatório você deseja gerar?", "Tipo de relatório", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,  
				new String[] {"Relatório somente com vendas", "Relatório com vendas e lista de produtos"}, null);
		if (escolhaTipoRelatorio >= 0) {
			JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
			janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");
			int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
			if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
				String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
				String resultado = "Erro ao gerar relatório.";
				try {
					VendaSeletor seletorParaExportar = new VendaSeletor();
					preencherSeletor(seletorParaExportar);
					ArrayList<Venda> vendasParaExportar = vendaController.consultarComFiltros(seletorParaExportar);
					if (escolhaTipoRelatorio == 0) {
						resultado = vendaController.gerarPlanilhaSomenteVendas(vendasParaExportar, caminhoEscolhido);
					} 
					if (escolhaTipoRelatorio == 1) {
						resultado = vendaController.gerarPlanilhaVendasComProdutos(vendasParaExportar, caminhoEscolhido);
					}
					JOptionPane.showMessageDialog(null, resultado);
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
				} catch (VendaInvalidaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	}
	
}
