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

    ProdutoController produtoController = new ProdutoController();

	private ArrayList<Produto> produtoscombo;

	protected Object telaPrincipal;
    
	/**
	 * Create the panel.
	 */
	public PainelGerenciarProdutos() {
		setLayout(null);
		
		JLabel lblQuantidade = new JLabel("Numero");
		lblQuantidade.setBounds(334, 149, 125, 14);
		add(lblQuantidade);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				
				int quantidadeEstoque = 0;
				quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
				lblQuantidade.setText(String.valueOf(quantidadeEstoque));
				
			}
		});
		produtoscombo = produtoController.buscarTodosProdutos();
		comboBox.setModel(new DefaultComboBoxModel(produtoscombo.toArray()));
		comboBox.setBounds(77, 88, 454, 22);
		add(comboBox);
		
		JLabel lblNewLabel = new JLabel("Selecionar Produto:");
		lblNewLabel.setBounds(77, 63, 96, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Selecionar Quantidade:");
		lblNewLabel_1.setBounds(77, 121, 119, 14);
		add(lblNewLabel_1);
		
		tfQuantidade = new JTextField();
		tfQuantidade.setBounds(77, 146, 133, 20);
		add(tfQuantidade);
		tfQuantidade.setColumns(10);
		
		JButton btAdicionar = new JButton("Adicionar");
		btAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int quantidadeDigitada = 0;
				
				try {
					quantidadeDigitada = Integer.parseInt(tfQuantidade.getText());
					if(quantidadeDigitada <= 0) {
						throw new CampoInvalidoException("Quantidade inválida");
					}
					Produto produtoSelecionado = (Produto) comboBox.getSelectedItem();
			     	boolean qualquerNome = produtoController.atualizarEstoque(quantidadeDigitada,produtoSelecionado);
			     	if(qualquerNome) {
			     		JOptionPane.showMessageDialog(btAdicionar, "Produto atualizado com sucesso");
			     		int quantidadeEstoque = 0;
						quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
						lblQuantidade.setText(String.valueOf(quantidadeEstoque));
						produtoscombo = produtoController.buscarTodosProdutos();
						comboBox.setModel(new DefaultComboBoxModel(produtoscombo.toArray()));
			     	}
					// TODO: ATUALIZAR ESTOQUE
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
			    }catch (EstoqueInsuficienteException e1) {
				JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
		}
				
			}
		});
		btAdicionar.setBounds(77, 203, 89, 23);
		add(btAdicionar);
		
		JButton btSubtrair = new JButton("Subtrair");
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
			     	boolean qualquerNome = produtoController.atualizarEstoque(quantidadeDigitada,produtoSelecionado);
			     	if(qualquerNome) {
			     		JOptionPane.showMessageDialog(btAdicionar, "Produto atualizado com sucesso");
			     		int quantidadeEstoque = 0;
						quantidadeEstoque = produtoController.consultarEstoque(((Produto) comboBox.getSelectedItem()).getId());
						lblQuantidade.setText(String.valueOf(quantidadeEstoque));
						
			     	}
			     	
					// TODO: ATUALIZAR ESTOQUE
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
				} catch (CampoInvalidoException e1) {
					JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
			    }catch (EstoqueInsuficienteException e1) {
				JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
		}
				
				
			}
		});
		btSubtrair.setBounds(176, 203, 89, 23);
		add(btSubtrair);
		
		JLabel lblNewLabel_2 = new JLabel("Quantidade Atual:");
		lblNewLabel_2.setBounds(334, 121, 125, 14);
		add(lblNewLabel_2);

	}
}
