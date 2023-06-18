package view.paineis;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
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
import model.vo.ItemVenda;
import model.vo.Produto;
import model.vo.Venda;

public class PainelRegistrarVenda extends JPanel {
	private JTextField tfTrecho;
	private JTextField tfRemoverProduto;
	private JTextField tfValorTotal;
	private JLabel lbAdicionarProdutos;
	private Component separator;
	private JButton btnBuscar;
	private JComboBox cbSelecionarProduto;
	private JTextPane tpDescricaoProdutoSelecionado;
	private JLabel lbQuantidade;
	private Component btAdicionar;
	private JLabel lbResumoDaCompra;
	private JSeparator separator_1;
	private JTextPane tpProdutosAdicionados;
	private JLabel lbRemoverProduto;
	private JButton btnRemover;
	private JLabel lbValorTotal;
	private JButton btnConfirmar;
	private ProdutoController produtoController = new ProdutoController();
	private VendaController vendaController = new VendaController();
	private ItemVendaController itemVendaController = new ItemVendaController();
	protected ArrayList<Produto> listaProdutosComboBox;
	private static final String VALOR_PADRAO_CAMPO_TRECHO = " Digite um trecho do nome do produto ou EAN";
	private static final String VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS = " Selecione o produto";
	private static final String VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO = ""
			+ "Produto Selecionado:\n"
			+ "Nome:\n"
			+ "Descrição:\n"
			+ "EAN:\n"
			+ "Valor unitário: R$\n"
			+ "Quantidade disponível:";
	private Venda venda = new Venda();
	private JScrollPane scrollPane;
	private JTextField tfQuantidade;
	
	/**
	 * Create the panel.
	 */
	public PainelRegistrarVenda() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(28dlu;default):grow"),
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
				ColumnSpec.decode("max(49dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("right:max(35dlu;default):grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				new RowSpec(RowSpec.TOP, Sizes.bounded(Sizes.PREFERRED, Sizes.constant("10dlu", false), Sizes.constant("30dlu", false)), 1),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				RowSpec.decode("max(8dlu;default)"),
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(74dlu;default)"),
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
				RowSpec.decode("max(17dlu;default):grow"),}));
		
		lbAdicionarProdutos = new JLabel("Adicionar produtos:");
		add(lbAdicionarProdutos, "4, 4, 3, 1");
		
		tpDescricaoProdutoSelecionado = new JTextPane();
		tpDescricaoProdutoSelecionado.setText(VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO);
		add(tpDescricaoProdutoSelecionado, "4, 10, 11, 1, left, fill");
		tpDescricaoProdutoSelecionado.setBackground(null);
		
		
		separator = new JSeparator();
		add(separator, "4, 5, 12, 1, default, top");
		
		tfTrecho = new JTextField();
		tfTrecho.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tfTrecho.getText().equals(VALOR_PADRAO_CAMPO_TRECHO)) {
					tfTrecho.setText("");
				}
			}
		});
		tfTrecho.setText(VALOR_PADRAO_CAMPO_TRECHO);
		add(tfTrecho, "4, 6, 7, 1, fill, default");
		tfTrecho.setColumns(10);
		
		
		btAdicionar = new JButton("Adicionar");
		btAdicionar.setEnabled(false);
		btAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoBotaoAdicionar();
			}
		});
		
		
		cbSelecionarProduto = new JComboBox();
		cbSelecionarProduto.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (cbSelecionarProduto.getSelectedItem() != null && !cbSelecionarProduto.getSelectedItem().equals(VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS)) {
					tpDescricaoProdutoSelecionado.setText("Produto Selecionado:\n" + ((Produto) cbSelecionarProduto.getSelectedItem()).toStringDescricaoCompleta());
					btAdicionar.setEnabled(true);
				} else {
					tpDescricaoProdutoSelecionado.setText(VALOR_PADRAO_DESCRICAO_PRODUTO_SELECIONADO);
					btAdicionar.setEnabled(false);
				}
				
			}
		});
		cbSelecionarProduto.setModel(new DefaultComboBoxModel(new String[] {VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS}));
		add(cbSelecionarProduto, "4, 8, 11, 1, fill, default");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoBuscar();
			}
		});
		add(btnBuscar, "12, 6, 3, 1");

		tfQuantidade = new JTextField();
		add(tfQuantidade, "6, 12, fill, default");
		tfQuantidade.setColumns(10);
		add(btAdicionar, "8, 12");
		
		lbQuantidade = new JLabel("Quantidade:");
		add(lbQuantidade, "4, 12, right, default");
		
		lbResumoDaCompra = new JLabel("Resumo da compra:");
		add(lbResumoDaCompra, "4, 16, 3, 1");
		
		separator_1 = new JSeparator();
		add(separator_1, "4, 17, 11, 1, default, top");
		
		scrollPane = new JScrollPane();
		add(scrollPane, "4, 18, 7, 17, fill, fill");
		
		tpProdutosAdicionados = new JTextPane();
		scrollPane.setViewportView(tpProdutosAdicionados);
		
		lbRemoverProduto = new JLabel("Remover Produto:");
		add(lbRemoverProduto, "14, 20, center, default");
		
		tfRemoverProduto = new JTextField();
		add(tfRemoverProduto, "14, 22, center, default");
		tfRemoverProduto.setColumns(10);
		
		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoRemover();
			}
		});
		add(btnRemover, "14, 24, center, default");
		
		lbValorTotal = new JLabel("Valor total:");
		add(lbValorTotal, "14, 28, center, default");
		
		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		add(tfValorTotal, "14, 30, center, default");
		tfValorTotal.setColumns(10);
		tfValorTotal.setBackground(null);
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Aqui a mágica acontece...
			}
		});
		btnConfirmar.setEnabled(false);
		add(btnConfirmar, "14, 32, center, default");

	}

	protected void acaoBotaoBuscar() {
		if (tfTrecho.getText().equals(VALOR_PADRAO_CAMPO_TRECHO)) {
			JOptionPane.showMessageDialog(btnBuscar, "Informe um trecho do nome ou EAN do produto.", "Campo inválido", 1);
		} else {
			try {
				cbSelecionarProduto.setFont(new Font("Consolas", Font.PLAIN, 10));
				listaProdutosComboBox = produtoController.buscarProdutosPorNomeOuEan(tfTrecho.getText());
				cbSelecionarProduto.setModel(new DefaultComboBoxModel(listaProdutosComboBox.toArray()));
			} catch (CampoInvalidoException e1) {
				JOptionPane.showMessageDialog(btnBuscar, "Informe um trecho do nome ou EAN do produto.", "Campo inválido", 1);
			}
		}		
	}

	protected void acaoBotaoAdicionar() {
		int quantidadeDigitada = 0;
		
		try {
			quantidadeDigitada = Integer.parseInt(tfQuantidade.getText());
			if (quantidadeDigitada > ((Produto) cbSelecionarProduto.getSelectedItem()).getEstoque()){
				throw new EstoqueInsuficienteException("");
			}
			if(quantidadeDigitada <= 0) {
				throw new CampoInvalidoException("Quantidade inválida");
			}
			Produto produtoSelecionado = (Produto) cbSelecionarProduto.getSelectedItem();
			ItemVenda itemVenda = new ItemVenda(produtoSelecionado, quantidadeDigitada, produtoSelecionado.getValor());
			venda.getListaItemVenda().add(itemVenda);
			atualizarTpProdutosAdicionadosEValorTotal();
			// TODO: ATUALIZAR ESTOQUE
			
		} catch (NumberFormatException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Informe um número válido.", "Aviso", 1);
		} catch (NullPointerException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "A venda está vazia.", "Aviso", 1);
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btAdicionar, e1.getMessage(), "Aviso", 1);
		} catch (EstoqueInsuficienteException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Estoque insuficiente", "Aviso", 1);
		} catch (ClassCastException e1) {
			JOptionPane.showMessageDialog(btAdicionar, "Selecione um produto válido.", "Aviso", 1);
			
		}
	}

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

}
