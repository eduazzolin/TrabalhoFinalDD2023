package view.paineis;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class PainelConsultarEstoque extends JPanel {

	/**
	 * Create the panel.
	 */
	public PainelConsultarEstoque() {
		setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(89, 82, 310, 22);
		add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Selecionar Produto:");
		lblNewLabel_1.setBounds(89, 57, 111, 14);
		add(lblNewLabel_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(89, 186, 387, 196);
		add(textArea);
		
		JButton btnNewButton = new JButton("Consultar");
		btnNewButton.setBounds(387, 140, 89, 23);
		add(btnNewButton);
		
		JButton btnNewButton_2 = new JButton("Editar");
		btnNewButton_2.setBounds(288, 140, 89, 23);
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Excluir");
		btnNewButton_3.setBounds(189, 140, 89, 23);
		add(btnNewButton_3);

	}
}
