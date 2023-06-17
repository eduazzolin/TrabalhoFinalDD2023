package view;

import javax.swing.JPanel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextPane;

public class PainelConsultarVenda extends JPanel {
	private JTextField tfTrecho;
	private JTextField tfQuantidade;
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
	private JTextPane textPane;
	private JLabel lbRemoverProduto;
	private JButton btnRemover;
	private JLabel lbValorTotal;
	private JButton btnConfirmar;

	/**
	 * Create the panel.
	 */
	public PainelConsultarVenda() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(42dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(51dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(58dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(34dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(49dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(36dlu;default)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(8dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(74dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(9dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		lbAdicionarProdutos = new JLabel("Adicionar produtos:");
		add(lbAdicionarProdutos, "4, 4, 3, 1");
		
		separator = new JSeparator();
		add(separator, "4, 6, 12, 1");
		
		tfTrecho = new JTextField();
		tfTrecho.setText(" Digite um trecho do nome do produto");
		add(tfTrecho, "4, 8, 7, 1, fill, default");
		tfTrecho.setColumns(10);
		
		btnBuscar = new JButton("Buscar");
		add(btnBuscar, "12, 8, 3, 1");
		
		cbSelecionarProduto = new JComboBox();
		cbSelecionarProduto.setModel(new DefaultComboBoxModel(new String[] {"Selecione o produto"}));
		add(cbSelecionarProduto, "4, 10, 11, 1, fill, default");
		
		tpDescricaoProdutoSelecionado = new JTextPane();
		add(tpDescricaoProdutoSelecionado, "4, 12, 11, 1, fill, fill");
		tpDescricaoProdutoSelecionado.setBackground(null);
		
		lbQuantidade = new JLabel("Quantidade:");
		add(lbQuantidade, "4, 14, left, default");
		
		tfQuantidade = new JTextField();
		add(tfQuantidade, "6, 14, fill, default");
		tfQuantidade.setColumns(10);
		
		btAdicionar = new JButton("Adicionar");
		add(btAdicionar, "8, 14");
		
		lbResumoDaCompra = new JLabel("Resumo da compra:");
		add(lbResumoDaCompra, "4, 18, 3, 1");
		
		separator_1 = new JSeparator();
		add(separator_1, "4, 20, 11, 1");
		
		textPane = new JTextPane();
		add(textPane, "4, 22, 7, 17, fill, fill");
		
		lbRemoverProduto = new JLabel("Remover Produto:");
		add(lbRemoverProduto, "14, 24");
		
		tfRemoverProduto = new JTextField();
		add(tfRemoverProduto, "14, 26, fill, default");
		tfRemoverProduto.setColumns(10);
		
		btnRemover = new JButton("Remover");
		add(btnRemover, "14, 28");
		
		lbValorTotal = new JLabel("Valor total:");
		add(lbValorTotal, "14, 32");
		
		tfValorTotal = new JTextField();
		tfValorTotal.setEditable(false);
		add(tfValorTotal, "14, 34, fill, default");
		tfValorTotal.setColumns(10);
		tfValorTotal.setBackground(null);
		
		btnConfirmar = new JButton("Confirmar");
		add(btnConfirmar, "14, 36");

	}

}
