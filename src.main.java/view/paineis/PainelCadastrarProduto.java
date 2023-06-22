package view.paineis;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class PainelCadastrarProduto extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Create the panel.
	 */
	public PainelCadastrarProduto() {
		setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(139, 70, 287, 20);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome do Produto:");
		lblNewLabel.setBounds(21, 73, 95, 14);
		add(lblNewLabel);
		

		JLabel lblNewLabel_1 = new JLabel("Descrição:");
		lblNewLabel_1.setBounds(21, 239, 95, 14);
		add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(139, 236, 287, 85);
		add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(139, 118, 287, 20);
		add(textField_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("Preço:");
		lblNewLabel_1_1.setBounds(21, 121, 95, 14);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Codigo do Produto:");
		lblNewLabel_1_1_1.setBounds(21, 173, 95, 14);
		add(lblNewLabel_1_1_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(139, 170, 287, 20);
		add(textField_3);
		
		JButton btnNewButton = new JButton("Confirmar");
		btnNewButton.setBounds(390, 353, 89, 23);
		add(btnNewButton);

	}
}
