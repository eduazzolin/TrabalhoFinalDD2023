package prototipo;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class RegistrarVenda extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public RegistrarVenda() {
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Adicionar produtos:");
		lblNewLabel.setBounds(45, 59, 115, 25);
		add(lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(115, 88, 250, 22);
		add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("Produto:");
		lblNewLabel_2.setBounds(55, 92, 46, 14);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Quantidade:");
		lblNewLabel_3.setBounds(39, 117, 60, 14);
		add(lblNewLabel_3);
		
		textField = new JTextField();
		textField.setBounds(115, 114, 86, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("R$ 00,00");
		lblNewLabel_1_1.setBounds(210, 117, 46, 14);
		add(lblNewLabel_1_1);
		
		JTextPane txtpnProduto = new JTextPane();
		txtpnProduto.setBounds(38, 199, 496, 134);
		add(txtpnProduto);
		
		JButton btnNewButton = new JButton("Adicionar");
		btnNewButton.setBounds(112, 145, 89, 23);
		add(btnNewButton);
		
		JLabel lblNewLabel_4 = new JLabel("Valor total: R$ 00,00");
		lblNewLabel_4.setBounds(426, 344, 108, 38);
		add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("Confirmar");
		btnNewButton_1.setBounds(445, 378, 89, 23);
		add(btnNewButton_1);

	}
}
