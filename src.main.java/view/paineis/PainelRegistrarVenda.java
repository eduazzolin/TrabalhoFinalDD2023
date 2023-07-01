package view.paineis;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import controller.ItemVendaController;
import controller.ProdutoController;
import controller.VendaController;
import model.exception.CampoInvalidoException;
import model.exception.EstoqueInsuficienteException;
import model.exception.VendaInvalidaException;
import model.vo.ItemVenda;
import model.vo.Produto;
import model.vo.Venda;

public class PainelRegistrarVenda extends JPanel {
	
	// componentes visuais:
	private JTextField tfTrecho;
	private JTextField tfRemoverProduto;
	private JTextField tfValorTotal;
	private JTextField tfQuantidade;
	private JLabel lbAdicionarProdutos;
	private JLabel lbRemoverProduto;
	private JLabel lbValorTotal;
	private JLabel lbQuantidade;
	private JLabel lbResumoDaCompra;
	private JLabel lbHashTag;
	private JLabel lbEstoque;
	private JButton btAdicionar;
	private JButton btnBuscar;
	private JButton btnRemover;
	private JButton btnConfirmar;
	private JComboBox cbSelecionarProduto;
	private JTextPane tpDescricaoProdutoSelecionado;
	private JTextPane tpProdutosAdicionados;
	private JSeparator separator;
	private JSeparator separator_1;
	private JScrollPane scrollPane;
	
	// classes mvc:
	private ProdutoController produtoController = new ProdutoController();
	private VendaController vendaController = new VendaController();
	private ItemVendaController itemVendaController = new ItemVendaController();

	// atributos simples:
	private Venda venda = new Venda();
	protected Produto produtoSelecionado;
	protected ArrayList<Produto> listaProdutosComboBox;

	// constantes de valores padrão:
	private static final String VALOR_PADRAO_CAMPO_TRECHO = " Digite um trecho do nome do produto ou EAN";
	private static final String VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS = " Selecione o produto";
	private static final String VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO = ""
			+ "Produto Selecionado:\n"
			+ "Nome:\n"
			+ "Descrição:\n"
			+ "EAN:\n"
			+ "Valor unitário: R$\n";
	
	/**
	 * Create the panel.
	 */
	@SuppressWarnings("unchecked")
	public PainelRegistrarVenda() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("31dlu"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(42dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(58dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(12dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(54dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:31dlu"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("top:14dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("max(8dlu;default)"),
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(62dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(11dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(14dlu;default)"),
				RowSpec.decode("max(15dlu;default)"),
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(17dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(29dlu;default)"),}));
		
		// declaração dos componentes visuais:
		lbAdicionarProdutos = new JLabel("Adicionar produtos:");
		add(lbAdicionarProdutos, "4, 3, 3, 1");
		
		tpDescricaoProdutoSelecionado = new JTextPane();
		tpDescricaoProdutoSelecionado.setText(VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO);
		add(tpDescricaoProdutoSelecionado, "4, 9, 13, 1, left, fill");
		tpDescricaoProdutoSelecionado.setBackground(null);
		
		tfQuantidade = new JTextField();
		add(tfQuantidade, "6, 11, fill, default");
		tfQuantidade.setColumns(10);

		lbQuantidade = new JLabel("Quantidade:");
		add(lbQuantidade, "4, 11, right, default");
		
		lbEstoque = new JLabel("");
		lbEstoque.setForeground(new Color(128, 0, 0));
		add(lbEstoque, "10, 11");
		
		lbResumoDaCompra = new JLabel("Resumo da compra:");
		add(lbResumoDaCompra, "4, 15, 3, 1");
		
		separator_1 = new JSeparator();
		add(separator_1, "4, 16, 13, 1, default, top");
		
		scrollPane = new JScrollPane();
		add(scrollPane, "4, 17, 7, 17, fill, fill");
		
		tpProdutosAdicionados = new JTextPane();
		scrollPane.setViewportView(tpProdutosAdicionados);
		
		lbRemoverProduto = new JLabel("Remover Produto:");
		add(lbRemoverProduto, "16, 19, center, default");
		
		lbHashTag = new JLabel("#");
		lbHashTag.setHorizontalAlignment(SwingConstants.RIGHT);
		add(lbHashTag, "14, 21, right, default");
		
		tfRemoverProduto = new JTextField();
		add(tfRemoverProduto, "16, 21, center, default");
		tfRemoverProduto.setColumns(10);
		
		separator = new JSeparator();
		add(separator, "4, 4, 14, 1, default, top");
		
		lbValorTotal = new JLabel("Valor total:");
		add(lbValorTotal, "16, 27, center, default");
		
		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		add(tfValorTotal, "16, 29, center, default");
		tfValorTotal.setColumns(10);
		tfValorTotal.setBackground(null);
		
		cbSelecionarProduto = new JComboBox<Object>(new String[] {VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS});
		add(cbSelecionarProduto, "4, 7, 13, 1, fill, default");
		cbSelecionarProduto.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				acaoComboBoxSelecionarProduto();
			}
		});
		
		tfTrecho = new JTextField(); 
		tfTrecho.setText(VALOR_PADRAO_CAMPO_TRECHO);
		add(tfTrecho, "4, 5, 7, 1, fill, default");
		tfTrecho.setColumns(10);
		tfTrecho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoCliqueTrecho();
			}
		});
		
		btAdicionar = new JButton("Adicionar");
		btAdicionar.setEnabled(false);
		add(btAdicionar, "8, 11");
		btAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoBotaoAdicionar();
			}
		});
		
		btnBuscar = new JButton("Buscar");
		add(btnBuscar, "12, 5, 5, 1");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoBuscar();
			}
		});

		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		add(btnRemover, "16, 23, center, default");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoRemover();
			}
		});
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setEnabled(false);
		add(btnConfirmar, "16, 31, center, default");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoConfirmar();
			}
		});

	}


	/**
	 * Busca no banco os produtos ativos com os filtros informados e atribui os resultados ao comboBox:
	 * 
	 * Valida se o campo trecho está preenchido, comparando com o valor padrão e vendo se não está vazio (controller);
	 * Busca no banco os produtos com os filtros informados e subtrai dos seus estoques os produtos do carrinho;
	 * Atribui os resultados ao ArrayList "listaProdutosComboBox";
	 * Altera a fonte do comboBox para uma fonte de largura fixa para acomodar melhor a formatação do texto;
	 * Atribui o ArrayList "listaProdutosComboBox" ao comboBox;
	 * Se não retornar nada, exibe mensagem;
	 */
	protected void acaoBotaoBuscar() {
		if (tfTrecho.getText().equals(VALOR_PADRAO_CAMPO_TRECHO)) {
			JOptionPane.showMessageDialog(btnBuscar, "Informe um trecho do nome ou EAN do produto.", "Campo inválido", 1);
		} else {
			try {
				listaProdutosComboBox = produtoController.buscarProdutosAtivosPorNomeOuEan(tfTrecho.getText());
				if (listaProdutosComboBox.size() > 0) {
					// subtraindo do estoque dos produtos da lista, os itens do carrinho:
					for(Produto pCombo : listaProdutosComboBox) {
						for(ItemVenda iv : venda.getListaItemVenda()) {
							if (pCombo.getId() == iv.getProduto().getId()) {
								pCombo.setEstoque(pCombo.getEstoque()-iv.getQtde());
							}
						}
					}
					cbSelecionarProduto.setFont(new Font("Consolas", Font.PLAIN, 10));
					cbSelecionarProduto.setModel(new DefaultComboBoxModel(listaProdutosComboBox.toArray()));
				} else {
					JOptionPane.showMessageDialog(btnBuscar, "Nenhum produto encontrado", "Aviso", 1);
				}
			} catch (CampoInvalidoException e1) {
				JOptionPane.showMessageDialog(btnBuscar, "Informe um trecho do nome ou EAN do produto.", "Campo inválido", 1);
			}
		}
	}

	/**
	 * Adiciona o produto selecionado à venda:
	 * 
	 * Valida a quantidade digitada convertendo para Integer e capturando ParseException caso não seja número;
	 * Valida a quantidade digitada conferindo se é > 0;
	 * Valida a quantidade digitada conferindo se é <= estoque;
	 * Valida se a seleção do comboBox está correta (ClassCastException);
	 * Cria um objeto ItemVenda a partir do produtoSelecionado e da quantidade digitada;
	 * Adiciona o  objeto ItemVenda ao ArrayList "listaItemVenda" do objeto Venda "venda";
	 * Atualiza o textPane "produtosAdicionados" e o textField "valorTotal";
	 * Limpa o comboBox e o textField do trecho;
	 */
	protected void acaoBotaoAdicionar() {
		int quantidadeDigitada = 0;
		try {
			quantidadeDigitada = Integer.parseInt(tfQuantidade.getText());
			if (cbSelecionarProduto.getSelectedItem() == null) {
				throw new ClassCastException();
			}
			if (quantidadeDigitada > ((Produto) cbSelecionarProduto.getSelectedItem()).getEstoque()){
				throw new EstoqueInsuficienteException("");
			}
			if(quantidadeDigitada <= 0) {
				throw new CampoInvalidoException("Quantidade inválida");
			}
			ItemVenda itemVenda = new ItemVenda(produtoSelecionado, quantidadeDigitada, produtoSelecionado.getValor());
			venda.getListaItemVenda().add(itemVenda);
			atualizarTpProdutosAdicionadosEValorTotal();
			limparSelecao();
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
		} catch (EstoqueInsuficienteException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Estoque insuficiente", "Aviso", 1);
		} catch (ClassCastException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Selecione um produto válido.", "Aviso", 1);
			
		}
	}

	/**
	 * Atribui o valor padrão ao textField do trecho e ao comboBox;
	 */
	private void limparSelecao() {
		tfTrecho.setText(VALOR_PADRAO_CAMPO_TRECHO);
		cbSelecionarProduto.setModel(new DefaultComboBoxModel(new String[] {VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS}));
		cbSelecionarProduto.setFont(new Font("Tahoma", Font.PLAIN, 10));
		tfQuantidade.setText("");
		lbEstoque.setText("");
	}
	
	/**
	 * Reseta a página inteira:
	 * 
	 * Atribui o valor padrão ao textField do trecho e ao comboBox;
	 * Reseta o objeto Venda "venda";
	 * Atualiza o restante dos elementos baseado na venda vazia;
	 */
	protected void limparPagina() {
		limparSelecao();
		venda = new Venda();
		atualizarTpProdutosAdicionadosEValorTotal();
	}

	/**
	 * Remove o produto selecionado da venda:
	 * 
	 * Valida o textField remover convertendo para Integer e capturando ParseException caso não seja número;
	 * Valida o textField remover conferindo se está dentro da quantidade de itens;
	 * Valida se a venda não está vazia;
	 * Remove do ArrayList listaItemVenda do objeto venda o item com o índice referente ao valor digitado;
	 * Limpa e atualiza os campos relacionados;
	 */
	protected void acaoBotaoRemover() {
		int numeroRemover = 0;
		try {
			numeroRemover = Integer.parseInt(tfRemoverProduto.getText());
			if(numeroRemover > venda.getListaItemVenda().size() || numeroRemover < 1) {
				throw new CampoInvalidoException("");
			}
			venda.getListaItemVenda().remove(numeroRemover-1);
			atualizarTpProdutosAdicionadosEValorTotal();
			tfRemoverProduto.setText("");
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "A venda está vazia.", "Aviso", 1);
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Posição inválida", "Aviso", 1);
		}
	}

	/**
	 * Atualiza o textPane de produtos adicionados e o valor total:
	 * 
	 * Atualiza o textPane de produtos adicionados e o textField valorTotal baseado no ArrayList listaItemVenda do objeto venda;
	 * Ativa ou desativa os botões "remover" e "confirmar" baseado na quantidade de itens no ArrayList;
	 */
	protected void atualizarTpProdutosAdicionadosEValorTotal() {
		String textoDoTpProdutosAdicionados = "";
		double valorTotal = 0;
		for (int i = 0; i < venda.getListaItemVenda().size(); i++) {
			Produto p = venda.getListaItemVenda().get(i).getProduto();
			String resumoProduto = String.format(""
					+ "#%d \n"
					+ "%.20s - R$ %.2f\n"
					+ "EAN %s\n"
					+ "Quantidade: %d und\n"
					+ "Total: R$ %.2f\n", 
					i+1,
					p.getNome(), p.getValor(),
					p.getEan(), 
					venda.getListaItemVenda().get(i).getQtde(), 
					(venda.getListaItemVenda().get(i).getQtde() * p.getValor())
					);
			valorTotal += (venda.getListaItemVenda().get(i).getQtde() * p.getValor());
			textoDoTpProdutosAdicionados += resumoProduto + "\n";
		}
		tpProdutosAdicionados.setText(textoDoTpProdutosAdicionados);
		tfValorTotal.setText(String.format("R$ %.2f", valorTotal));
		if (venda.getListaItemVenda().size() == 0) {
			btnRemover.setEnabled(false);
			btnConfirmar.setEnabled(false);
		} else {
			btnRemover.setEnabled(true);
			btnConfirmar.setEnabled(true);
		}
	}

	/**
	 * Limpa o textField quando clicado;
	 */
	private void acaoCliqueTrecho() {
		if (tfTrecho.getText().equals(VALOR_PADRAO_CAMPO_TRECHO)) {
			tfTrecho.setText("");
		}
	}

	/**
	 * Detecta alterações na seleção do comboBox para atribuir o item ao objeto Produto "produtoSelecionado":
	 * 
	 * Valida se a seleção está em um objeto Produto;
	 * Atribui o produto selecionado ao objeto Produto "produtoSelecionado";
	 * Atualiza o textPane de descrição do produto;
	 * Atualiza o label de estoque;
	 */
	private void acaoComboBoxSelecionarProduto() {
		if (cbSelecionarProduto.getSelectedItem() != null && !cbSelecionarProduto.getSelectedItem().equals(VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS)) {
			produtoSelecionado = (Produto) cbSelecionarProduto.getSelectedItem();
			tpDescricaoProdutoSelecionado.setText("Produto Selecionado:\n" + produtoSelecionado.toStringDescricaoCompleta());
			if (produtoSelecionado.getEstoque() > 0){
				lbEstoque.setForeground(new Color(0, 128, 0));
				lbEstoque.setText("Qtde disponível: " + produtoSelecionado.getEstoque());
				btAdicionar.setEnabled(true);
			} else {
				lbEstoque.setForeground(new Color(128, 0, 0));
				lbEstoque.setText("FORA DE ESTOQUE");
				btAdicionar.setEnabled(false);
			}
		} else {
			tpDescricaoProdutoSelecionado.setText(VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO);
			btAdicionar.setEnabled(false);
		}
	}

	/**
	 * Cadastra a venda (e os itemVenda) no banco;
	 * 
	 * Valida se a venda não está vazia;
	 * Cadastra no banco;
	 * Limpa a tela;
	 */
	private void acaoBotaoConfirmar() {
		if (venda.getListaItemVenda() != null && venda.getListaItemVenda().size() > 0) {
			try {
				venda = vendaController.cadastrarVenda(venda);
				JOptionPane.showMessageDialog(btAdicionar, "Venda cadastrada com sucesso!", "Aviso", 1);
				limparPagina();
			} catch (VendaInvalidaException | EstoqueInsuficienteException e1) {
				JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
			}
		}
	}

}
