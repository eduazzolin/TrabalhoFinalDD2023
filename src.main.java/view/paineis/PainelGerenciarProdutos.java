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

	// TODO: Colocar algo sobre os podutos desativados
	
	/**
	 * Create the panel.
	 */
	public PainelGerenciarProdutos() {
		setLayout(null);
		
		lblQuantidade = new JLabel("Numero");
		lblQuantidade.setBounds(334, 149, 125, 14);
		add(lblQuantidade);
		
		lbSelecionarProduto = new JLabel("Selecionar Produto:");
		lbSelecionarProduto.setBounds(77, 63, 96, 14);
		add(lbSelecionarProduto);
		
		lbSelecionarQuantidade = new JLabel("Selecionar Quantidade:");
		lbSelecionarQuantidade.setBounds(77, 121, 119, 14);
		add(lbSelecionarQuantidade);
		
		tfQuantidade = new JTextField();
		tfQuantidade.setBounds(77, 146, 133, 20);
		add(tfQuantidade);
		tfQuantidade.setColumns(10);
		
		lbQuantidadeAtual = new JLabel("Quantidade Atual:");
		lbQuantidadeAtual.setBounds(334, 121, 125, 14);
		add(lbQuantidadeAtual);
		
		
		produtoscombo = produtoController.buscarTodosProdutos(); // busca os produtos no banco e armazena em um ArrayList
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(produtoscombo.toArray()));
		comboBox.setBounds(77, 88, 454, 22);
		add(comboBox);
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
		btAdicionar.setBounds(77, 203, 89, 23);
		add(btAdicionar);
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
		btSubtrair.setBounds(176, 203, 89, 23);
		add(btSubtrair);
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
