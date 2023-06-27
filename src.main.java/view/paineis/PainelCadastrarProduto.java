package view.paineis;

import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ProdutoController;
import model.exception.CampoInvalidoException;
import model.vo.Produto;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class PainelCadastrarProduto extends JPanel {
	private JTextField txtProduto;
	private JTextField txtDescricao;
	private JTextField txtPreco;
	private JTextField txtCodigo;
	
	protected Produto produtoNovo;
	
	/**
	 * Create the panel.
	 * @param produto 
	 */
	
	/*
	 * esse produto passado por parametro pode ser o que vem da classe PainelConsultarEstoque quando a pessoa clica em editar
	 * ou pode ser um produto vazio de quando a pessoa abre este painel pelo menu.
	 * para saber se vai ser cadastro ou edição, é só ver se ele veio com id.
	 * se ele tiver id tem que atualizar, se ele não tiver daí é para cadastrar
	 * se for pra atualizar da para preencher já os campos com os atributos dele.
	 */
	public PainelCadastrarProduto(Produto produto) {
		setLayout(null);
		
		txtProduto = new JTextField();
		txtProduto.setBounds(139, 70, 287, 20);
		add(txtProduto);
		txtProduto.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nome do Produto:");
		lblNewLabel.setBounds(21, 73, 95, 14);
		add(lblNewLabel);
		

		JLabel lblNewLabel_1 = new JLabel("Descrição:");
		lblNewLabel_1.setBounds(21, 239, 95, 14);
		add(lblNewLabel_1);
		
		txtDescricao = new JTextField();
		txtDescricao.setColumns(10);
		txtDescricao.setBounds(139, 236, 287, 85);
		add(txtDescricao);
		
		txtPreco = new JTextField();
		txtPreco.setColumns(10);
		txtPreco.setBounds(139, 118, 287, 20);
		add(txtPreco);
		
		JLabel lblNewLabel_1_1 = new JLabel("Preço:");
		lblNewLabel_1_1.setBounds(21, 121, 95, 14);
		add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Codigo do Produto:");
		lblNewLabel_1_1_1.setBounds(21, 173, 95, 14);
		add(lblNewLabel_1_1_1);
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(139, 170, 287, 20);
		add(txtCodigo);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					acaoBotaoConfirmar();
				} catch (CampoInvalidoException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		btnConfirmar.setBounds(390, 353, 89, 23);
		add(btnConfirmar);

	}
	private void acaoBotaoConfirmar() throws CampoInvalidoException {
		
			 if(txtProduto.getText().isEmpty()) {
				 throw new CampoInvalidoException("Nome do produto não informado");
			    }
			    	if(txtPreco.getText().isEmpty()) {
						 throw new CampoInvalidoException("Preço não informado");
			    	}
			    	 if(txtCodigo.getText().isEmpty()) {
						 throw new CampoInvalidoException("Código não informado");
					 }
							 if(txtDescricao.getText().isEmpty()) {
								 throw new CampoInvalidoException("Descrição não informada");
							 }
							 
							 
							 produtoNovo.setNome(txtProduto.getText());
							 produtoNovo.setDescricao(txtDescricao.getText());
							 produtoNovo.setValor(Double.parseDouble(txtPreco.getText()));
							 produtoNovo.setEan(txtCodigo.getText());
							
							produtoNovo = ProdutoController.criarProduto(produtoNovo);
							 
	}
}
