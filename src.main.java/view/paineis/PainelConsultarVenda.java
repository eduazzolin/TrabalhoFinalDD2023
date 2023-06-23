package view.paineis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
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
	private ItemVendaController itemVendaController = new ItemVendaController();
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
				RowSpec.decode("max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(179dlu;default)"),
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
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 17");
		
		separator2 = new JSeparator();
		add(separator2, "4, 19, 17, 1, default, top");
		
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
		
		btnVoltar = new JButton("<< Voltar");
		btnVoltar.setEnabled(false);
		add(btnVoltar, "4, 25");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVoltar();
			}
		});
		
		lbPaginas = new JLabel("");
		lbPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbPaginas, "6, 25");
		
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
		add(btnExportar, "20, 25");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
	}

	/**
	 * Busca os elementos no banco de dados com os filtros;
	 * Atualiza a tabela;
	 * Atualiza a paginação;
	 * Valida se os campos "máximos" são maiores que os "mínimos".
	 */
	protected void acaoBotaoConsultar() {
		try {
			paginaAtual = 1;
			totalPaginas = 0;
			buscarClientesComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btnConsultar, e1.getMessage(), "Campo inválido", 1);
		}
	}

	/**
	 * Remove do banco de dados a venda selecionada e seus respectivos ITEM_VENDAs;
	 * Valida se a seleção está funcionando;
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
	 * Abre o dialog "DialogVerProdutos" passando o ArrayList "listaItemVenda" do objeto vendaSelecionada;
	 * O dialog consiste em uma tabela que mostra os produtos incluídos na venda;
	 */
	protected void acaoBotaoVerProdutos() {
		DialogVerProdutos verProdutos = new DialogVerProdutos();
		verProdutos.setLocationRelativeTo(null);
		verProdutos.setVisible(true);
		verProdutos.atualizarTabela(vendaSelecionada.getListaItemVenda());
	}

	/**
	 * Diminui 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoVoltar() {
		paginaAtual--;
		try {
			buscarClientesComFiltrosEAtualizarTabela();
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
	 * Aumenta 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoAvancar() {
		paginaAtual++;
		try {
			buscarClientesComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
		
	}

	/**
	 * Cria um objeto seletor a partir dos filtros inseridos e atributos de paginação;
	 * Valida os valores máximos dos filtros para confirmar se eles não são menores que os mínimos;
	 * Se os campos de valor estiverem com 0 (valor padrão) atribui "null";
	 * Busca no banco com os filtros e atribui o resultado ao ArrayList "vendas";
	 * Atualiza a tabela, a paginação e os botões de navegação;
	 * @throws CampoInvalidoException;
	 */
	private void buscarClientesComFiltrosEAtualizarTabela() throws CampoInvalidoException {
		// criação do seletor e validação:
		seletor = new VendaSeletor();
		seletor.setLimite(TAMANHO_PAGINA);
		seletor.setPagina(paginaAtual);
		valorMinimo = ftfValorMinimo.getValue().doubleValue();
		valorMaximo = ftfValorMaximo.getValue().doubleValue();
		if (dtFinal.getDate() != null && dtInicial.getDate() != null && dtFinal.getDate().isBefore(dtInicial.getDate())) {
			throw new CampoInvalidoException("Data final não pode ser anterior à data inicial.");
		}
		if (valorMinimo > valorMaximo) {
			throw new CampoInvalidoException("Valor mínimo não pode ser maior que o valor máximo.");
		}
		seletor.setDataFinal(dtFinal.getDate());
		seletor.setDataInicial(dtInicial.getDate());
		seletor.setEan(tfEan.getText());
		seletor.setValorMaximo(valorMaximo > 0 ? valorMaximo : null);
		seletor.setValorMinimo(valorMinimo > 0 ? valorMinimo : null);
		
		// busca no banco e atualização:
		vendas = vendaController.consultarComFiltros(seletor);
		atualizarTabela();
		atualizarQuantidadePaginas();
		ativarOuDesativarBotoesVoltarAvancar();
	}

	/**
	 * Deixa a tabela somente com o cabeçalho padrão;
	 */
	private void limparTabela() {
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}
	
	/**
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
	 * Busca no banco quantas linhas retornam com os filtros;
	 * Calcula o total de páginas dividindo o total de linhas pelo tamanho da página
	 * Caso o resultado seja um número decimal, o total é arredondado para cima;
	 * Atualiza o lbPaginas deixando sempre o total como no mínimo 1;
	 */
	private void atualizarQuantidadePaginas() {
		int totalRegistros = vendaController.contarTotalRegistrosComFiltros(seletor);
		totalPaginas = (int) Math.ceil(totalRegistros / TAMANHO_PAGINA);
		lbPaginas.setText(paginaAtual + " / " + (totalPaginas == 0 ? 1 : totalPaginas));
	}

	/**
	 * Valida se a seleção não está no cabeçalho (índice 0);
	 * Atribui a venda selecionada ao objeto "vendaSelecionada" através do índice da linha e do índice do array "vendas"
	 * Atualiza os botões "remover" e "VerProdutos"
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
	
}
