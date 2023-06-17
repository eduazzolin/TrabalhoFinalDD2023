package view.paineis;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;

import controller.ItemVendaController;
import controller.ProdutoController;
import controller.VendaController;
import model.exception.CampoInvalidoException;
import model.vo.Produto;

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
	private JFormattedTextField ftfQuantidade;
	private MaskFormatter maskQuantidade;
	private ArrayList<Produto> produtosAdicionados = new ArrayList<>();
	
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
				ColumnSpec.decode("max(58dlu;default):grow(2)"),
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
				RowSpec.decode("default:grow(4)"),
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
		tpDescricaoProdutoSelecionado.setText(""
				+ "Produto Selecionado:\n"
				+ "Nome:\n"
				+ "Descrição:\n"
				+ "EAN:\n"
				+ "Valor unitário: R$\n"
				+ "Quantidade disponível:");
		add(tpDescricaoProdutoSelecionado, "4, 10, 11, 1, left, fill");
		tpDescricaoProdutoSelecionado.setBackground(null);
		
		NumberFormat numberFormat = NumberFormat.getInstance();
		NumberFormatter formatterNumerosInteirosPositivos = new NumberFormatter(numberFormat);
		formatterNumerosInteirosPositivos.setValueClass(Integer.class);
		formatterNumerosInteirosPositivos.setMinimum(1);
		// a quantidade máxima é determinada pelo estoque no listener do combobox
		formatterNumerosInteirosPositivos.setAllowsInvalid(false);
		
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
		
		
		
		
		
		cbSelecionarProduto = new JComboBox();
		cbSelecionarProduto.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (!cbSelecionarProduto.getSelectedItem().equals(VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS)) {
					tpDescricaoProdutoSelecionado.setText("Produto Selecionado:\n" + ((Produto) cbSelecionarProduto.getSelectedItem()).toStringDescricaoCompleta());
					formatterNumerosInteirosPositivos.setMaximum(((Produto) cbSelecionarProduto.getSelectedItem()).getEstoque());
//					ftfQuantidade.setText("1");
				}
				
			}
		});
		cbSelecionarProduto.setModel(new DefaultComboBoxModel(new String[] {VALOR_PADRAO_COMBOBOX_SELECAO_PRODUTOS}));
		add(cbSelecionarProduto, "4, 8, 11, 1, fill, default");
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		add(btnBuscar, "12, 6, 3, 1");
		
		

		
		btAdicionar = new JButton("Adicionar");
		btAdicionar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// validando quantidade digitada:
				if (Integer.parseInt(ftfQuantidade.getText()) > ((Produto) cbSelecionarProduto.getSelectedItem()).getEstoque()) {
					JOptionPane.showMessageDialog(btAdicionar, "Estoque insuficiente.", "Aviso", 1);
				} else {
					produtosAdicionados.add((Produto)cbSelecionarProduto.getSelectedItem());
					// TODO: Parei aqui
				}
				
				
				
			}
		});
		
	
		ftfQuantidade = new JFormattedTextField(formatterNumerosInteirosPositivos);
		add(ftfQuantidade, "6, 12, fill, default");
		add(btAdicionar, "8, 12");
		
		lbQuantidade = new JLabel("Quantidade:");
		add(lbQuantidade, "4, 12, right, default");
		
		
		lbResumoDaCompra = new JLabel("Resumo da compra:");
		add(lbResumoDaCompra, "4, 16, 3, 1");
		
		separator_1 = new JSeparator();
		add(separator_1, "4, 17, 11, 1, default, top");
		
		tpProdutosAdicionados = new JTextPane();
		add(tpProdutosAdicionados, "4, 18, 7, 17, fill, fill");
		
		lbRemoverProduto = new JLabel("Remover Produto:");
		add(lbRemoverProduto, "14, 20, center, default");
		
		tfRemoverProduto = new JTextField();
		add(tfRemoverProduto, "14, 22, center, default");
		tfRemoverProduto.setColumns(10);
		
		btnRemover = new JButton("Remover");
		add(btnRemover, "14, 24, center, default");
		
		lbValorTotal = new JLabel("Valor total:");
		add(lbValorTotal, "14, 28, center, default");
		
		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		add(tfValorTotal, "14, 30, center, default");
		tfValorTotal.setColumns(10);
		tfValorTotal.setBackground(null);
		
		btnConfirmar = new JButton("Confirmar");
		add(btnConfirmar, "14, 32, center, default");

	}

}
