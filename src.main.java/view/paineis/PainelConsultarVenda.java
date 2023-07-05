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

import controller.VendaController;
import model.exception.CampoInvalidoException;
import model.exception.VendaInvalidaException;
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;
import view.componentesExternos.JNumberFormatField;
import view.dialogs.DialogVerProdutos;

public class PainelConsultarVenda extends JPanel {
	private static final long serialVersionUID = -4902755455242384274L;
	
	// componentes visuais:
	private DatePicker dtInicial;
	private DatePicker dtFinal;
	private JNumberFormatField ftfValorMinimo;
	private JNumberFormatField ftfValorMaximo;
	private JTextField tfEan;
	private JLabel lbFiltrarConsulta;
	private JLabel lbEan;
	private JLabel lbValorMinimo;
	private JLabel lbValorMaximo;
	private JLabel lbDataInicial;
	private JLabel lbDataFinal;
	private JLabel lbResultados;
	private JLabel lbPaginas;
	private JLabel lbValorTotal;
	private JLabel lbQuantidadeItens;
	private JLabel lbQuantidadeVendas;
	private JButton btnConsultar;
	private JButton btnVerProdutos;
	private JButton btnExportar;
	private JButton btnVoltar;
	private JButton btnAvancar;
	private JButton btnRemover;
	private JSeparator separator;
	private JSeparator separator2;
	private JTable table;
	
	// classes mvc:
	protected VendaSeletor seletor;
	private VendaController vendaController = new VendaController();
	protected Venda vendaSelecionada;
	
	// atributos simples:
	private Double valorMaximo;
	private Double valorMinimo;
	protected ArrayList<Venda> vendas;
	private ArrayList<Venda> vendasTodasAsPaginas;
	private double valorTotal;
	private int quantidadeItens;
	
	// atributos da paginação
	private final int TAMANHO_PAGINA = 15;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	
	// atributos de valor padrão:
	private String[] nomesColunas = {  "Código", "Data", "Quantidade de itens", "Valor total" };
	private static final String VALOR_PADRAO_QUANTIDADE_VENDAS = "Quantidade de vendas: ";
	private static final String VALOR_PADRAO_QUANTIDADE_ITENS = "Quantidade de itens: ";
	private static final String VALOR_PADRAO_VALOR_TOTAL = "Valor total: ";
	

	public PainelConsultarVenda() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("31dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(36dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("83dlu:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(32dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(25dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("31dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
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
				RowSpec.decode("max(14dlu;default)"),}));
		
		
		// Configurações da parte de DATAS do componente
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		lbFiltrarConsulta = new JLabel("Filtrar consulta:");
		add(lbFiltrarConsulta, "4, 3, left, bottom");
		
		separator = new JSeparator();
		add(separator, "4, 5, 19, 1, default, top");
		
		lbEan = new JLabel("EAN:");
		add(lbEan, "4, 6, right, default");
		
		tfEan = new JTextField();
		add(tfEan, "6, 6, 17, 1, fill, default");
		tfEan.setColumns(10);
		
		lbValorMinimo = new JLabel("Valor mínimo: R$");
		add(lbValorMinimo, "4, 8, right, default");
		
		ftfValorMinimo = new JNumberFormatField(2);
		add(ftfValorMinimo, "6, 8, 5, 1, fill, default");
		
		lbValorMaximo = new JLabel("Valor máximo: R$");
		add(lbValorMaximo, "12, 8, 3, 1, right, default");
		
		ftfValorMaximo = new JNumberFormatField(2);
		add(ftfValorMaximo, "16, 8, 7, 1, fill, default");
		
		lbDataInicial = new JLabel("Data inicial:");
		add(lbDataInicial, "4, 10, right, default");
		
		lbValorTotal = new JLabel(VALOR_PADRAO_VALOR_TOTAL);
		add(lbValorTotal, "4, 20, 5, 1");
		
		lbQuantidadeVendas = new JLabel(VALOR_PADRAO_QUANTIDADE_VENDAS);
		add(lbQuantidadeVendas, "4, 22, 5, 1");
		
		lbQuantidadeItens = new JLabel(VALOR_PADRAO_QUANTIDADE_ITENS);
		add(lbQuantidadeItens, "4, 24, 5, 1");

		lbPaginas = new JLabel("");
		lbPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbPaginas, "6, 32");
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 16");
		
		separator2 = new JSeparator();
		add(separator2, "4, 18, 19, 1, default, top");
		
		dtInicial = new DatePicker();
		dtInicial.setBounds(160, 90, 515, 30);
		add(dtInicial, "6, 10, 5, 1, fill, default");
		
		lbDataFinal = new JLabel("Data final:");
		add(lbDataFinal, "12, 10, 3, 1, right, default");
		
		dtFinal = new DatePicker();
		dtFinal.setBounds(160, 90, 515, 30);
		add(dtFinal, "16, 10, 7, 1, fill, default");
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setMaximumSize(new Dimension(50, 21));
		add(btnConsultar, "20, 12, 3, 1");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoConsultar();
			}
		});
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		add(table, "4, 28, 19, 3, fill, fill");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoCliqueTabela();
			}
		});
		limparTabela();
		
		btnVoltar = new JButton("<< Voltar");
		btnVoltar.setEnabled(false);
		add(btnVoltar, "4, 32");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVoltar();
			}
		});
		
		btnAvancar = new JButton("Avançar >>");
		btnAvancar.setEnabled(false);
		add(btnAvancar, "8, 32");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoAvancar();
			}
		});
		
		btnVerProdutos = new JButton("Ver produtos");
		btnVerProdutos.setEnabled(false);
		add(btnVerProdutos, "20, 32");
		btnVerProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVerProdutos();
			}
		});
		
		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		add(btnRemover, "18, 32");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoRemover();
			}
		});
		
		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		add(btnExportar, "22, 32");
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
	 * (controller) Valida se a seleção está funcionando;
	 * Remove do banco de dados;
	 * Atualiza a tabela e os botões "remover" e "ver produtos".
	 */
	protected void acaoBotaoRemover() {
		int confirmacao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja remover venda?", "Confirmação", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,  
				new String[] {"Remover", "Cancelar"}, null);
		if(confirmacao == 0) {
			try {
				if(vendaController.removerVenda(vendaSelecionada)) {
					JOptionPane.showMessageDialog(null, "Venda removida com sucesso!", "Sucesso", 1);
					acaoBotaoConsultar();
					btnRemover.setEnabled(false);
					btnVerProdutos.setEnabled(false);
				} else {
					JOptionPane.showMessageDialog(null, "Erro ao remover venda", "Erro", 1);
				}
			} catch (VendaInvalidaException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", 1);
			}
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
	 * Busca no banco com os filtros porém sem paginação e atribui o resultado ao ArrayList "vendasTodasAsPaginas";
	 * "vendasTodasAsPaginas" será usado para relatórios e valores totais;
	 * Atualiza a tabela, a paginação e os botões de navegação e exportar;
	 */
	private void buscarVendasComFiltrosEAtualizarTabela() throws CampoInvalidoException {
		
		seletor = new VendaSeletor();
		preencherSeletor(seletor);
		
		// busca no banco com paginação:
		vendas = vendaController.consultarComFiltros(seletor);
		
		// busca no banco da lista sem paginação:
		vendasTodasAsPaginas = vendaController.consultarComFiltrosSemPaginacao(seletor);
		
		// atualizações
		atualizarTabela();
		atualizarLabelsTotais();
		atualizarQuantidadePaginas();
		ativarOuDesativarBotoesVoltarAvancar();
		btnExportar.setEnabled(vendas != null && vendas.size()>0);
	}

	/**
	 * Atualiza os labels de valor total:
	 * 
	 * Conta e soma os valores do ArrayList "vendasTodasAsPaginas";
	 * Atribui os valores aos labels correspondentes; 
	 */
	private void atualizarLabelsTotais() {
		valorTotal = 0;
		quantidadeItens = 0;
		for (Venda v : vendasTodasAsPaginas) {
			for (ItemVenda iv : v.getListaItemVenda()) {
				quantidadeItens += iv.getQtde();
				valorTotal += (iv.getValorUnitario() * iv.getQtde());
			}
		}
		lbQuantidadeVendas.setText(VALOR_PADRAO_QUANTIDADE_VENDAS +  vendasTodasAsPaginas.size());
		lbQuantidadeItens.setText(VALOR_PADRAO_QUANTIDADE_ITENS +  quantidadeItens);
		lbValorTotal.setText(VALOR_PADRAO_VALOR_TOTAL +  String.format("R$ %.2f", valorTotal));
		
	}

	/**
	 * Atribui os valores de paginação e dos campos ao seletor do parâmetro;
	 */
	private void preencherSeletor(VendaSeletor s) throws CampoInvalidoException {
		s.setLimite(TAMANHO_PAGINA);
		s.setPagina(paginaAtual);
		valorMinimo = ftfValorMinimo.getValue().doubleValue();
		valorMaximo = ftfValorMaximo.getValue().doubleValue();
		if (dtFinal.getDate() != null && dtInicial.getDate() != null && dtFinal.getDate().isBefore(dtInicial.getDate())) {
			throw new CampoInvalidoException("Data final não pode ser anterior à data inicial.");
		}
		if (valorMinimo > valorMaximo && valorMaximo != 0) {
			throw new CampoInvalidoException("Valor mínimo não pode ser maior que o valor máximo.");
		}
		s.setDataFinal((dtFinal.getDate() == null) ? null : dtFinal.getDate().plusDays(1));
		s.setDataInicial(dtInicial.getDate());
		s.setEan(tfEan.getText());
		s.setValorMaximo(valorMaximo > 0 ? valorMaximo : null);
		s.setValorMinimo(valorMinimo > 0 ? valorMinimo : null);
	}

	/**
	 * Deixa a tabela somente com o cabeçalho e tamanho de linhas padrão;
	 */
	private void limparTabela() {
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas }, nomesColunas));
		table.setRowHeight(20);
	}
	
	/**
	 * Atualiza a tabela baseada no ArrayList "vendas":
	 * 
	 * Limpa a tabela;
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
	 * (Controller) Valida se o destino foi escolhido e se a consulta não está vazia;
	 * Exporta a planilha;
	 */
	private void acaoBotaoExportar() {
		
		// escolhendo o tipo:
		int escolhaTipoRelatorio = JOptionPane.showOptionDialog(null, "Qual tipo de relatório você deseja gerar?", "Tipo de relatório", 
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,  
				new String[] {"Relatório somente com vendas", "Relatório com vendas e lista de produtos"}, null);
		
		if (escolhaTipoRelatorio >= 0) {
			
			// escolhendo o local de salvamento:
			JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
			janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");
			int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
			
			// gerando relatório:
			if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
				String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
				String resultado = "Erro ao gerar relatório.";
				try {
					if (escolhaTipoRelatorio == 0) {
						resultado = vendaController.gerarPlanilhaSomenteVendas(vendasTodasAsPaginas, caminhoEscolhido);
					} 
					if (escolhaTipoRelatorio == 1) {
						resultado = vendaController.gerarPlanilhaVendasComProdutos(vendasTodasAsPaginas, caminhoEscolhido);
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
