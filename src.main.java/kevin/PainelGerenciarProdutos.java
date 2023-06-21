package kevin;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class PainelGerenciarProdutos extends JPanel {
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public PainelGerenciarProdutos() {
		setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(77, 88, 261, 22);
		add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Selecionar Produto:");
		lblNewLabel.setBounds(77, 63, 96, 14);
		add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Okay");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(498, 374, 89, 23);
		add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Selecionar Quantidade:");
		lblNewLabel_1.setBounds(77, 121, 119, 14);
		add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(77, 146, 133, 20);
		add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("Adicionar");
		btnNewButton_1.setBounds(77, 203, 89, 23);
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Subtrair");
		btnNewButton_2.setBounds(176, 203, 89, 23);
		add(btnNewButton_2);

	}
}
