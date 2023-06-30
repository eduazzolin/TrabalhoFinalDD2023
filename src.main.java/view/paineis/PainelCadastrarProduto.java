package view.paineis;

import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ProdutoController;
import model.exception.CampoInvalidoException;
import model.vo.Produto;
import view.componentesExternos.JNumberFormatField;

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
	private JTextField txtCodigo;
	private ProdutoController produtoController = new ProdutoController();
	private JTextArea txtDescricao;
	private JButton btnConfirmar;
	private Component lbCodigo;
	private Component lbPreco;
	private JLabel lbDescricao;
	private JLabel lbNomeProduto;
	private JSeparator separator;
	private JLabel lbTitulo;
	
	protected Produto produtoNovo = new Produto();
	private JNumberFormatField txtPreco;
	
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
		produtoNovo = produto;
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
		
		lbTitulo = new JLabel("Cadastrar produto");
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
		
		lbNomeProduto = new JLabel("Nome do Produto:");
		add(lbNomeProduto, "2, 3, fill, center");
		

		lbDescricao = new JLabel("Descrição:");
		add(lbDescricao, "2, 9, fill, top");
		
		txtPreco = new JNumberFormatField(2);
		txtPreco.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtPreco, "5, 5, fill, fill");
		
		lbPreco = new JLabel("Preço:");
		add(lbPreco, "2, 5, fill, center");
		
		lbCodigo = new JLabel("Codigo do Produto:");
		add(lbCodigo, "2, 7, fill, center");
		
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
		
		preencherCamposQuandoForEdicao(produto);

	}
	
	
	/**
	 * Confere se o produto do construtor tem id ou não
	 * Se tiver, significa que é edição então os campos 
	 * da tela são preenchidos.
	 * @param produto 
	 */
	private void preencherCamposQuandoForEdicao(Produto p) {
		if (p.getId() > 0) {
			txtProduto.setText(p.getNome());
			txtPreco.setText(String.format("R$ %.2f", p.getValor()));
			txtCodigo.setText(p.getEan());
			txtDescricao.setText(p.getDescricao());
		}
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
							 if(txtProduto.getText().length() > 255) {
								 throw new CampoInvalidoException("Tamanho maximo do nome atingido (255 caracteres)");
							 }
							  if(txtPreco.getText().length() > 11) {
								 throw new CampoInvalidoException("Tamanho maximo do preço atingido (12 caracteres)");
						    	}
						    	 if(txtCodigo.getText().length() > 16) {
									 throw new CampoInvalidoException("Tamanho maximo do codigo atingido (16 caracteres)");
								 }
										 if(txtDescricao.getText().length() > 255) {
											 throw new CampoInvalidoException("Tamanho maximo da descrição atingido (255 caracteres)");
										 }
							 
							 
							 produtoNovo.setNome(txtProduto.getText());
							 produtoNovo.setDescricao(txtDescricao.getText());
							 produtoNovo.setValor(txtPreco.getValue().doubleValue());
							 produtoNovo.setEan(txtCodigo.getText());
							
								if(produtoNovo.getId() == 0) {
									produtoNovo = produtoController.criarProduto(produtoNovo);
								} else {
									if(produtoController.editarProduto(produtoNovo)) {
										JOptionPane.showMessageDialog(null, "Produto atualizado com sucesos!", "Atualização", 1);
									} else {
										JOptionPane.showMessageDialog(null, "Erro ao atualizar produto!", "Erro", 1);
									}
								}
							 
	}
}
