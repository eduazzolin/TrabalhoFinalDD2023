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
import java.awt.Component;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

public class PainelCadastrarProduto extends JPanel {
	private JTextField txtProduto;
	private JTextField txtPreco;
	private JTextField txtCodigo;
	
	protected Produto produtoNovo = new Produto();
	private ProdutoController produtoController = new ProdutoController();
	private JTextArea txtDescricao;
	private JButton btnConfirmar;
	private Component lblNewLabel_1_1_1;
	private Component lblNewLabel_1_1;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private JLabel lbTitulo;
	
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
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("31px"),
				ColumnSpec.decode("95px"),
				ColumnSpec.decode("23px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("340px:grow"),
				ColumnSpec.decode("31dlu"),},
			new RowSpec[] {
				RowSpec.decode("bottom:max(31dlu;default)"),
				RowSpec.decode("top:max(10dlu;default)"),
				RowSpec.decode("20px"),
				RowSpec.decode("28px"),
				RowSpec.decode("20px"),
				RowSpec.decode("32px"),
				RowSpec.decode("20px"),
				RowSpec.decode("46px"),
				RowSpec.decode("max(138px;default):grow"),
				RowSpec.decode("32px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("25px"),
				RowSpec.decode("max(27dlu;default)"),}));
		
		lbTitulo = new JLabel((produto.getId() == 0 ? "Cadastrar produto" : "Editar produto"));
		lbTitulo.setVerticalAlignment(SwingConstants.BOTTOM);
		lbTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		add(lbTitulo, "2, 1, 4, 1");
		
		separator = new JSeparator();
		add(separator, "2, 2, 4, 1");
		
		txtDescricao = new JTextArea();
		txtDescricao.setLineWrap(true);
		add(txtDescricao, "5, 9, fill, fill");
		
		txtProduto = new JTextField();
		add(txtProduto, "5, 3, fill, fill");
		txtProduto.setColumns(10);
		
		lblNewLabel = new JLabel("Nome do Produto:");
		add(lblNewLabel, "2, 3, fill, center");
		

		lblNewLabel_1 = new JLabel("Descrição:");
		add(lblNewLabel_1, "2, 9, fill, top");
		
		txtPreco = new JTextField();
		txtPreco.setColumns(10);
		add(txtPreco, "5, 5, fill, fill");
		
		lblNewLabel_1_1 = new JLabel("Preço:");
		add(lblNewLabel_1_1, "2, 5, fill, center");
		
		lblNewLabel_1_1_1 = new JLabel("Codigo do Produto:");
		add(lblNewLabel_1_1_1, "2, 7, fill, center");
		
		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		add(txtCodigo, "5, 7, fill, fill");
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					acaoBotaoConfirmar();
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		

		add(btnConfirmar, "5, 12, right, fill");

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
							
							produtoNovo = produtoController.criarProduto(produtoNovo);
							 
	}
}
