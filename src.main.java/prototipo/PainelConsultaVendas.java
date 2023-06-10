package prototipo;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.table.DefaultTableModel;

public class PainelConsultaVendas extends JPanel {
	private JTable table;
	private JTextField txtSkuDoProduto;
	private DatePicker dtNascimentoFinal;
	private JTextField textField_1;
	private JTextField textField;
	/**
	 * Create the panel.
	 */
	public PainelConsultaVendas() {
		setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(57, 247, 493, 306);
		add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"#", "Data", "Quantidade de itens", "Valor total"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Contêm: ");
		lblNewLabel.setBounds(57, 72, 64, 21);
		add(lblNewLabel);
		
		txtSkuDoProduto = new JTextField();
		txtSkuDoProduto.setText("  SKU do produto");
		txtSkuDoProduto.setBounds(126, 72, 420, 20);
		add(txtSkuDoProduto);
		txtSkuDoProduto.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Data inicial:");
		lblNewLabel_2.setBounds(57, 142, 81, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Valor mínimo: ");
		lblNewLabel_1_1.setBounds(57, 107, 107, 14);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Valor máximo: ");
		lblNewLabel_1_1_1.setBounds(309, 107, 93, 14);
		add(lblNewLabel_1_1_1);
		
		DatePicker dtNascimentoInicial_1_1 = new DatePicker();
		dtNascimentoInicial_1_1.setBounds(126, 139, 177, 21);
		add(dtNascimentoInicial_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Data final:");
		lblNewLabel_2_1.setBounds(315, 144, 81, 14);
		add(lblNewLabel_2_1);
		
		DatePicker dtNascimentoInicial_1_1_1 = new DatePicker();
		dtNascimentoInicial_1_1_1.setBounds(369, 140, 177, 21);
		add(dtNascimentoInicial_1_1_1);
		
		textField_1 = new JTextField();
		textField_1.setText("R$ ");
		textField_1.setColumns(10);
		textField_1.setBounds(127, 104, 166, 20);
		add(textField_1);
		
		textField = new JTextField();
		textField.setText("R$ ");
		textField.setColumns(10);
		textField.setBounds(386, 104, 160, 20);
		add(textField);
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(244, 177, 120, 23);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Exportar relatório");
		btnNewButton_1.setBounds(403, 565, 147, 23);
		add(btnNewButton_1);
		
		JLabel lblFiltrarResultados = new JLabel("Filtrar consulta:");
		lblFiltrarResultados.setBounds(57, 36, 115, 25);
		add(lblFiltrarResultados);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(57, 58, 496, 14);
		add(separator_1);
		
		JLabel lblResultados = new JLabel("Resultados:");
		lblResultados.setBounds(57, 211, 115, 25);
		add(lblResultados);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(57, 231, 496, 14);
		add(separator);
		
		JButton btnNewButton_2 = new JButton("Ver produtos");
		btnNewButton_2.setBounds(287, 566, 107, 21);
		add(btnNewButton_2);

	}
}
