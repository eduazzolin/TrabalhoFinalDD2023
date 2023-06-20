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
import model.seletor.VendaSeletor;
import model.vo.ItemVenda;
import model.vo.Venda;
import view.componentesExternos.JNumberFormatField;
import view.dialogs.DialogVerProdutos;
import javax.swing.ListSelectionModel;

public class PainelConsultarVenda extends JPanel {
	private JTextField tfEan;
	private DatePicker dtInicial;
	private JTextField textField_1;
	private JTextField textField_2;
	private DatePicker dtFinal;
	private JTable table;
	private JLabel lbFiltrarConsulta;
	private JLabel lbContem;
	private JLabel lbValorMinimo;
	private JNumberFormatField ftfValorMinimo;
	private JLabel lbValorMaximo;
	private JNumberFormatField ftfValorMaximo;
	private JLabel lbDataInicial;
	private JLabel lbDataFinal;
	private JButton btnConsultar;
	private JLabel lbResultados;
	private JSeparator separator2;
	private JButton btnVerProdutos;
	private JButton btnExportar;
	private JSeparator separator;
	protected VendaSeletor seletor;
	private VendaController vendaController = new VendaController();
	private ItemVendaController itemVendaController = new ItemVendaController();
	private ProdutoController produtoController = new ProdutoController();
	private Double valorMaximo;
	private Double valorMinimo;
	private JButton btnVoltar;
	private JButton btnAvancar;
	private JLabel lbPaginas;
	private final int TAMANHO_PAGINA = 15;
	private int paginaAtual = 1;
	private int totalPaginas = 0;
	private String[] nomesColunas = {  "Código", "Data", "Quantidade de itens", "Valor total" };
	protected ArrayList<Venda> vendas;
	protected Venda vendaSelecionada;
	
	
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
				ColumnSpec.decode("left:max(25dlu;default):grow"),
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
				RowSpec.decode("max(23dlu;default):grow(2)"),}));
		
		lbFiltrarConsulta = new JLabel("Filtrar consulta:");
		add(lbFiltrarConsulta, "4, 4, left, bottom");
		
		separator = new JSeparator();
		add(separator, "4, 6, 15, 1, default, top");
		
		lbContem = new JLabel("Contêm:");
		add(lbContem, "4, 7, right, default");
		
		tfEan = new JTextField();
		add(tfEan, "6, 7, 13, 1, fill, default");
		tfEan.setColumns(10);
		
		lbValorMinimo = new JLabel("Valor mínimo:");
		add(lbValorMinimo, "4, 9, right, default");
		
		ftfValorMinimo = new JNumberFormatField(2);
		add(ftfValorMinimo, "6, 9, 5, 1, fill, default");
		
		lbValorMaximo = new JLabel("Valor máximo:");
		add(lbValorMaximo, "12, 9, right, default");
		
		ftfValorMaximo = new JNumberFormatField(2);
		add(ftfValorMaximo, "14, 9, 5, 1, fill, default");
		
		lbDataInicial = new JLabel("Data inicial:");
		add(lbDataInicial, "4, 11, right, default");

		// Configurações da parte de DATAS do componente
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		dtInicial = new DatePicker();
		dtInicial.setBounds(160, 90, 515, 30);
		add(dtInicial, "6, 11, 5, 1, fill, default");
		
		lbDataFinal = new JLabel("Data final:");
		add(lbDataFinal, "12, 11, right, default");
		
		dtFinal = new DatePicker();
		dtFinal.setBounds(160, 90, 515, 30);
		add(dtFinal, "14, 11, 5, 1, fill, default");
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					paginaAtual = 1;
					totalPaginas = 0;
					buscarClientesComFiltros();
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(btnConsultar, e1.getMessage(), "Campo inválido", 1);
				}
			}
		});
		btnConsultar.setMaximumSize(new Dimension(50, 21));
		add(btnConsultar, "16, 13, 3, 1");
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 17");
		
		separator2 = new JSeparator();
		add(separator2, "4, 19, 15, 1, default, top");
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indiceSelecionado = table.getSelectedRow();
				if (indiceSelecionado > 0) {
					btnVerProdutos.setEnabled(true);
					vendaSelecionada = vendas.get(indiceSelecionado - 1);
				} else {
					btnVerProdutos.setEnabled(false);
				}
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		add(table, "4, 21, 15, 3, fill, fill");
		
		btnVoltar = new JButton("<< Voltar");
		btnVoltar.setEnabled(false);
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVoltar();
			}
		});
		add(btnVoltar, "4, 25");
		
		lbPaginas = new JLabel("");
		add(lbPaginas, "6, 25");
		
		btnAvancar = new JButton("Avançar >>");
		btnAvancar.setEnabled(false);
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoAvancar();
			}
		});
		add(btnAvancar, "8, 25");
		
		btnVerProdutos = new JButton("Ver produtos");
		btnVerProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogVerProdutos verProdutos = new DialogVerProdutos();
				verProdutos.setLocationRelativeTo(null);
				verProdutos.setVisible(true);
				verProdutos.atualizarTabela(vendaSelecionada.getListaItemVenda());
			}
		});
		btnVerProdutos.setEnabled(false);
		add(btnVerProdutos, "16, 25");
		
		btnExportar = new JButton("Exportar");
		add(btnExportar, "18, 25");
		
	}

	protected void acaoBotaoVoltar() {
		paginaAtual--;
		try {
			buscarClientesComFiltros();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();

		
	}
	
	

	private void ativarOuDesativarBotoesVoltarAvancar() {
		btnVoltar.setEnabled(paginaAtual > 1);
		btnAvancar.setEnabled(paginaAtual < totalPaginas);
	}

	protected void acaoBotaoAvancar() {
		paginaAtual++;
		try {
			buscarClientesComFiltros();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
		
	}

	private void buscarClientesComFiltros() throws CampoInvalidoException {
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
		vendas = vendaController.consultarComFiltros(seletor);
		atualizarTabela();
		atualizarQuantidadePaginas();
		ativarOuDesativarBotoesVoltarAvancar();
	}

	private void limparTabela() {
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
	}
	
	private void atualizarTabela() {
		this.limparTabela();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		for (Venda v : this.vendas) {
			int quantidadeProdutos = 0;
			double valorTotal = 0;
			Object[] novaLinhaDaTabela = new Object[4];
			novaLinhaDaTabela[0] = v.getId();
			novaLinhaDaTabela[1] = v.getDataVenda().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
			for (ItemVenda iv : v.getListaItemVenda()) {
				quantidadeProdutos += iv.getQtde();
				valorTotal += (iv.getValorUnitario() * quantidadeProdutos);
			}
			novaLinhaDaTabela[2] = quantidadeProdutos;
			novaLinhaDaTabela[3] = String.format("R$ %.2f", valorTotal);

			model.addRow(novaLinhaDaTabela);
		}
	}
	
	private void atualizarQuantidadePaginas() {
		//Cálculo do total de páginas (poderia ser feito no backend)
		int totalRegistros = vendaController.contarTotalRegistrosComFiltros(seletor);
		
		//QUOCIENTE da divisão inteira
		totalPaginas = totalRegistros / TAMANHO_PAGINA;
		
		//RESTO da divisão inteira
		if(totalRegistros % TAMANHO_PAGINA > 0) { 
			totalPaginas++;
		}
		
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
	}
	
}
