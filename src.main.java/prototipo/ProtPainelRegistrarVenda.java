package prototipo;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class ProtPainelRegistrarVenda extends JPanel {
	private JTextField textField;
	private JTextField txtFiltrar;
	private JTextField textField_1;
	private JTextField txtR;

	/**
	 * Create the panel.
	 */
	public ProtPainelRegistrarVenda() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Adicionar produtos:");
		lblNewLabel.setBounds(45, 31, 115, 25);
		add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"  Selecione o produto"}));
		comboBox.setBounds(45, 97, 460, 22);
		add(comboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Quantidade:");
		lblNewLabel_3.setBounds(45, 212, 60, 14);
		add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(115, 209, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JTextPane txtpnProduto = new JTextPane();
		txtpnProduto.setText("Produtos adicionados:\r\n\r\n#01\r\nNome do produto 1 - R$ 2,00\r\n213432534 (CÓDIGO EAN)\r\nQuantidade: 12 und\r\nTotal: R$ 24,00\r\n\r\n#02\r\nNome do produto 2 - R$ 5,00\r\n2341234123 (CÓDIGO EAN)\r\nQuantidade: 1 und\r\nTotal: R$ 5,00\r\n\r\n\r\n");
		txtpnProduto.setBounds(45, 289, 334, 219);
		add(txtpnProduto);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.setBounds(211, 208, 89, 23);
		add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Valor total:");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(419, 421, 86, 22);
		add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("Confirmar");
		btnNewButton_1.setBounds(418, 470, 89, 23);
		add(btnNewButton_1);
		
		txtFiltrar = new JTextField();
		txtFiltrar.setText("  Digite um trecho do nome do produto");
		txtFiltrar.setColumns(10);
		txtFiltrar.setBounds(45, 66, 334, 20);
		add(txtFiltrar);
		
		JButton btnNewButton_2 = new JButton("Buscar");
		btnNewButton_2.setBounds(389, 65, 116, 23);
		add(btnNewButton_2);
		
		JTextPane txtpnProdutoSelecionadoNome = new JTextPane();
		txtpnProdutoSelecionadoNome.setEditable(false);
		txtpnProdutoSelecionadoNome.setText("Produto selecionado:\r\nNome completo do produto exemplo\r\nEAN 654230345\r\nR$ 16,25\r\nQuantidade disponível: 35 und\r\n");
		txtpnProdutoSelecionadoNome.setBounds(45, 128, 442, 73);
		txtpnProdutoSelecionadoNome.setBackground(null);
		add(txtpnProdutoSelecionadoNome);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(45, 279, 496, 14);
		add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(45, 53, 496, 14);
		add(separator_1);
		
		JLabel lblResumoDaCompra = new JLabel("Resumo da compra:");
		lblResumoDaCompra.setBounds(45, 259, 115, 25);
		add(lblResumoDaCompra);
		
		JLabel lblNewLabel_1 = new JLabel("Remover produto:");
		lblNewLabel_1.setBounds(418, 303, 88, 25);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setText("#");
		textField_1.setBounds(417, 324, 90, 20);
		add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_3 = new JButton("Remover");
		btnNewButton_3.setBounds(418, 349, 89, 23);
		add(btnNewButton_3);
		
		txtR = new JTextField();
		txtR.setHorizontalAlignment(SwingConstants.CENTER);
		txtR.setText("R$ 29,00");
		txtR.setEditable(false);
		txtR.setBounds(419, 442, 86, 20);
		add(txtR);
		txtR.setColumns(10);

	}
}
