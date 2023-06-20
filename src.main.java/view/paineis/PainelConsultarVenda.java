package view.paineis;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import controller.ItemVendaController;
import controller.ProdutoController;
import controller.VendaController;
import model.seletor.VendaSeletor;
import model.vo.Venda;
import view.componentesExternos.JNumberFormatField;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import view.componentesExternos.JNumberFormatField;

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
				ColumnSpec.decode("max(50dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(48dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(57dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(77dlu;default):grow"),
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
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(226dlu;default):grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(23dlu;default)"),}));
		
		lbFiltrarConsulta = new JLabel("Filtrar consulta:");
		add(lbFiltrarConsulta, "4, 4, left, bottom");
		
		separator = new JSeparator();
		add(separator, "4, 6, 13, 1, default, top");
		
		lbContem = new JLabel("Contêm:");
		add(lbContem, "4, 7, right, default");
		
		tfEan = new JTextField();
		add(tfEan, "6, 7, 11, 1, fill, default");
		tfEan.setColumns(10);
		
		lbValorMinimo = new JLabel("Valor mínimo:");
		add(lbValorMinimo, "4, 9, right, default");
		
		ftfValorMinimo = new JNumberFormatField(2);
		add(ftfValorMinimo, "6, 9, 3, 1, fill, default");
		
		lbValorMaximo = new JLabel("Valor máximo:");
		add(lbValorMaximo, "10, 9, right, default");
		
		ftfValorMaximo = new JNumberFormatField(2);
		add(ftfValorMaximo, "12, 9, 5, 1, fill, default");
		
		lbDataInicial = new JLabel("Data inicial:");
		add(lbDataInicial, "4, 11, right, default");

		// Configurações da parte de DATAS do componente
		DatePickerSettings dateSettings = new DatePickerSettings();
		dateSettings.setAllowKeyboardEditing(false);
		
		dtInicial = new DatePicker();
		dtInicial.setBounds(160, 90, 515, 30);
		add(dtInicial, "6, 11, 3, 1, fill, default");
		
		lbDataFinal = new JLabel("Data final:");
		add(lbDataFinal, "10, 11, right, default");
		
		dtFinal = new DatePicker();
		dtFinal.setBounds(160, 90, 515, 30);
		add(dtFinal, "12, 11, 5, 1, fill, default");
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				seletor = new VendaSeletor();
				seletor.setDataFinal(dtFinal.getDate());
				seletor.setDataInicial(dtInicial.getDate());
				seletor.setEan(tfEan.getText());
				seletor.setValorMaximo(Double.parseDouble(ftfValorMaximo.getText()));
				seletor.setValorMinimo(Double.parseDouble(ftfValorMinimo.getText()));
				// TODO: PAREI AQUI
				ArrayList<Venda> listaVendas = vendaController.consultarComFiltros(seletor);
			}
		});
		btnConsultar.setMaximumSize(new Dimension(50, 21));
		add(btnConsultar, "14, 13, 3, 1");
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 15");
		
		separator2 = new JSeparator();
		add(separator2, "4, 17, 13, 1, default, top");
		
		table = new JTable();
		add(table, "4, 19, 13, 3, fill, fill");
		
		btnVerProdutos = new JButton("Ver produtos");
		add(btnVerProdutos, "14, 23");
		
		btnExportar = new JButton("Exportar");
		add(btnExportar, "16, 23");
		
	}

}
