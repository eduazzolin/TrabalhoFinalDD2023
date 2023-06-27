package view.paineis;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import model.vo.Produto;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PainelConsultarEstoque extends JPanel {

	private JButton btnEditar;
	private Produto produtoSelecionado;
	
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
		
		btnEditar = new JButton("Editar");
		btnEditar.setBounds(288, 140, 89, 23);
		add(btnEditar);
		
		JButton btnNewButton_3 = new JButton("Excluir");
		btnNewButton_3.setBounds(189, 140, 89, 23);
		add(btnNewButton_3);

	}

	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}
	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
