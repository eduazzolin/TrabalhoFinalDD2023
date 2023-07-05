package view.paineis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ProdutoController;
import model.exception.CampoInvalidoException;
import model.vo.Produto;
import view.componentesExternos.JNumberFormatField;

public class PainelCadastrarProduto extends JPanel {
	private static final long serialVersionUID = 8488266604176894743L;
	
	// componentes visuais:
	private JNumberFormatField txtPreco;
	private JTextField txtProduto;
	private JTextField txtCodigo;
	private JTextArea txtDescricao;
	private JButton btnConfirmar;
	private JLabel lbCodigo;
	private JLabel lbPreco;
	private JLabel lbDescricao;
	private JLabel lbNomeProduto;
	private JLabel lbTitulo;
	private JSeparator separator;
	
	// atributos MVC: 
	private ProdutoController produtoController = new ProdutoController();
	protected Produto produtoNovo = new Produto();
	
	
	/*
	 * Este produto passado por parâmetro vem da classe PainelConsultarEstoque quando a pessoa clica em editar
	 * ou pode ser um produto vazio de quando a pessoa abre este painel pelo menu.
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
				RowSpec.decode("14dlu"),}));
		
		lbTitulo = new JLabel("Cadastrar produto");
		lbTitulo.setVerticalAlignment(SwingConstants.BOTTOM);
		lbTitulo.setHorizontalAlignment(SwingConstants.LEFT);
		add(lbTitulo, "2, 1, 4, 1");
		
		lbNomeProduto = new JLabel("Nome do Produto:");
		add(lbNomeProduto, "2, 3, fill, center");

		lbDescricao = new JLabel("Descrição:");
		add(lbDescricao, "2, 9, fill, top");
		
		txtPreco = new JNumberFormatField(2);
		txtPreco.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCodigo.getText().equals("")) {
					txtCodigo.setText(" --EAN-- ");
				}
				if (txtProduto.getText().equals("")) {
					txtProduto.setText(" --Produto-- ");
			}
			}
		});
		txtPreco.setHorizontalAlignment(SwingConstants.LEFT);
		add(txtPreco, "5, 5, fill, fill");
		
		lbPreco = new JLabel("Preço: R$");
		add(lbPreco, "2, 5, fill, center");
		
		lbCodigo = new JLabel("Codigo do Produto:");
		add(lbCodigo, "2, 7, fill, center");
		
		separator = new JSeparator();
		add(separator, "2, 2, 4, 1");
		
		txtDescricao = new JTextArea();
		txtDescricao.setLineWrap(true);
		txtDescricao.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCodigo.getText().equals("")) {
					txtCodigo.setText(" --EAN-- ");
				}
				if (txtProduto.getText().equals("")) {
					txtProduto.setText(" --Produto-- ");
			}
			}
		});
		add(txtDescricao, "5, 9, fill, fill");
		
		txtProduto = new JTextField(" --Produto-- ");
		add(txtProduto, "5, 3, fill, fill");
		txtProduto.setColumns(10);
		txtProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtProduto.getText().equals(" --Produto-- ")) {
					txtProduto.setText("");
				}
				if (txtCodigo.getText().equals("")) {
					txtCodigo.setText(" --EAN-- ");
				}
			}
		});
		
		txtCodigo = new JTextField(" --EAN-- ");
		txtCodigo.setColumns(10);
		add(txtCodigo, "5, 7, fill, fill");
		txtCodigo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtCodigo.getText().equals(" --EAN-- ")) {
					txtCodigo.setText("");
				}
				if (txtProduto.getText().equals("")) {
					txtProduto.setText(" --Produto-- ");
			}
			}
		});
		
		btnConfirmar = new JButton("Confirmar");
		add(btnConfirmar, "5, 12, right, fill");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					acaoBotaoConfirmar();
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
				}
			}

		});
		
		preencherCamposQuandoForEdicao(produto);

	}
	
	
	/**
	 * Confere se o produto do construtor tem id ou não
	 * Se tiver, significa que é edição então os campos 
	 * da tela são preenchidos.
	 */
	private void preencherCamposQuandoForEdicao(Produto p) {
		if (p.getId() > 0) {
			txtProduto.setText(p.getNome());
			txtPreco.setText(String.format("R$ %.2f", p.getValor()));
			txtCodigo.setText(p.getEan());
			txtDescricao.setText(p.getDescricao());
		}
	}

	/**
	 * Confirma o cadastro ou a edição:
	 * 
	 * Valida se os campos estão preenchidos e se estão no tamanho máximo do banco;
	 * Preenche um objeto Produto com os campos digitados;
	 * Verifica se tem ID preenchido, se tiver, edita, caso contrário, cadastra;
	 * Exibe mensagem do resultado e limpa os campos.
	 */
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
								 throw new CampoInvalidoException("Tamanho máximo do nome atingido (255 caracteres)");
							 }
							  if(txtPreco.getValue().toString().length() > 13) {
								 throw new CampoInvalidoException("Tamanho máximo do preço atingido (12 caracteres)");
						    	}
						    	 if(txtCodigo.getText().length() > 16) {
									 throw new CampoInvalidoException("Tamanho máximo do codigo atingido (16 caracteres)");
								 }
										 if(txtDescricao.getText().length() > 255) {
											 throw new CampoInvalidoException("Tamanho máximo da descrição atingido (255 caracteres)");
										 }
							 
							 
							 produtoNovo.setNome(txtProduto.getText().trim());
							 produtoNovo.setDescricao(txtDescricao.getText().trim());
							 produtoNovo.setValor(txtPreco.getValue().doubleValue());
							 produtoNovo.setEan(txtCodigo.getText().trim());
							
								if(produtoNovo.getId() == 0) {
									if(produtoController.criarProduto(produtoNovo)) {
										JOptionPane.showMessageDialog(null, "Produto Cadastrado com sucesos!", "Cadastrar", 1);
									} else {
										JOptionPane.showMessageDialog(null, "Erro ao Cadastrar produto!", "Erro", 1);
									}
								} else {
									if(produtoController.editarProduto(produtoNovo)) {
										JOptionPane.showMessageDialog(null, "Produto atualizado com sucesos!", "Atualização", 1);
									} else {
										JOptionPane.showMessageDialog(null, "Erro ao atualizar produto!", "Erro", 1);
									}
								}
								txtProduto.setText("");
								txtPreco.setText("");
								txtCodigo.setText("");
								txtDescricao.setText("");
								produtoNovo = new Produto();
	}
}
