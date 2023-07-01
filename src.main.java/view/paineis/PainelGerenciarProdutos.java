package view.paineis;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

import controller.ProdutoController;
import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;
import model.vo.Produto;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JSeparator;

public class PainelGerenciarProdutos extends JPanel {
	
	private JTextField tfQuantidade;
	private JLabel lblQuantidade;
	private JComboBox comboBox;
	private JLabel lbSelecionarProduto;
	private JLabel lbSelecionarQuantidade;
	private JButton btSubtrair;
	private JLabel lbQuantidadeAtual;
    ProdutoController produtoController = new ProdutoController();
	private ArrayList<Produto> produtoscombo;

	/**
	 * Create the panel.
	 */
	public PainelGerenciarProdutos() {
		setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("31px"),
				ColumnSpec.decode("96px"),
				FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("89px"),
				ColumnSpec.decode("69px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("197px"),
				ColumnSpec.decode("31dlu:grow"),},
			new RowSpec[] {
				RowSpec.decode("top:14dlu"),
				RowSpec.decode("14px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("22px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("14px"),
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				RowSpec.decode("37px"),
				RowSpec.decode("23px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		lblQuantidade = new JLabel("Numero");
		add(lblQuantidade, "7, 8, fill, center");
		
		lbSelecionarProduto = new JLabel("Selecionar Produto:");
		add(lbSelecionarProduto, "2, 2, fill, fill");
		
		lbSelecionarQuantidade = new JLabel("Selecionar Quantidade:");
		add(lbSelecionarQuantidade, "2, 6, 3, 1, left, fill");
		
		tfQuantidade = new JTextField();
		add(tfQuantidade, "2, 8, 3, 1, left, fill");
		tfQuantidade.setColumns(10);
		
		lbQuantidadeAtual = new JLabel("Quantidade Atual:");
		add(lbQuantidadeAtual, "7, 6, left, fill");
		
		
		produtoscombo = produtoController.buscarTodosProdutos();
		// busca os produtos no banco e armazena em um ArrayList
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(produtoscombo.toArray()));
		add(comboBox, "2, 4, 6, 1, fill, fill");
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				// Ação de seleção de item:
				// Quando um item é selecionado, o estoque dele é buscado no banco e passado para o label "Quantidade"
				int quantidadeEstoque = 0;
				quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
				lblQuantidade.setText(String.valueOf(quantidadeEstoque));
			}
		});

		/*
		 * Botão adicionar:
		 * 
		 * Passa a quantidade digitada para uma variável validando se é um número maior que 0
		 * Passa o produto selecionado no comboBox para uma variável "produtoSelecionado"
		 * Atualiza o estoque no banco
		 * Atualiza o label "Quantidade"
		 * Atualiza o comboBox
		 */
		JButton btAdicionar = new JButton("Adicionar");
		add(btAdicionar, "2, 10, fill, fill");
		btAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantidadeDigitada = 0;
				
				try {
					quantidadeDigitada = Integer.parseInt(tfQuantidade.getText());
					if(quantidadeDigitada <= 0) {
						throw new CampoInvalidoException("Quantidade inválida");
					}
					Produto produtoSelecionado = (Produto) comboBox.getSelectedItem();
					boolean atualizacao = produtoController.atualizarEstoque(quantidadeDigitada, produtoSelecionado);
					if(atualizacao) {
						JOptionPane.showMessageDialog(btAdicionar, "Produto atualizado com sucesso");
						int quantidadeEstoque = 0;
						quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
						lblQuantidade.setText(String.valueOf(quantidadeEstoque));

					} else {
						JOptionPane.showMessageDialog(btAdicionar, "Erro ao atualizar estoque.", "Aviso", 1);
					}
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
				}catch (EstoqueInsuficienteException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
				}
				
			}
		});
		
		/*
		 * Botão subtrair:
		 * 
		 * Passa a quantidade digitada para uma variável validando se é um número maior que 0
		 * Transforma esse número em negativo
		 * Passa o produto selecionado no comboBox para uma variável "produtoSelecionado"
		 * Atualiza o estoque no banco
		 * No controller é validado se a quantidade digitada não é maior que a atual
		 * Atualiza o label "Quantidade"
		 * Atualiza o comboBox
		 */
		btSubtrair = new JButton("Subtrair");
		add(btSubtrair, "4, 10, fill, fill");
		btSubtrair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantidadeDigitada = 0;
				
				try {
					quantidadeDigitada = Integer.parseInt(tfQuantidade.getText());
					if(quantidadeDigitada <= 0) {
						throw new CampoInvalidoException("Quantidade inválida");
					}
					quantidadeDigitada *= -1;
					Produto produtoSelecionado = (Produto) comboBox.getSelectedItem();
					boolean atualizacao = produtoController.atualizarEstoque(quantidadeDigitada,produtoSelecionado);
					if(atualizacao) {
						JOptionPane.showMessageDialog(btAdicionar, "Produto atualizado com sucesso");
						int quantidadeEstoque = 0;
						quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
						lblQuantidade.setText(String.valueOf(quantidadeEstoque));
					}
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
				}catch (EstoqueInsuficienteException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
				}
				
				
			}
		});
		
	}
}
