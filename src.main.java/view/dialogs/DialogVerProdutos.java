package view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.vo.ItemVenda;
import model.vo.Produto;
import model.vo.Venda;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DialogVerProdutos extends JDialog {
	private String[] nomesColunas = { "EAN", "Produto", "Qtde", "Valor Unitário", "Valor Total", "Status" };
	private JTable tbVerProdutos;
	private JScrollPane scrollPane;
	private JButton cancelButton;
	private JLabel lblDescVenda;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			DialogVerProdutos dialog = new DialogVerProdutos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DialogVerProdutos() {
		setTitle("Produtos incluídos");
		setBounds(100, 100, 943, 460);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(366px;default):grow"),
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("20px"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(196px;default):grow"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("21px"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(11dlu;default)"),}));
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "3, 3, 3, 1, fill, fill");
		
		tbVerProdutos = new JTable();
		tbVerProdutos.setModel(new DefaultTableModel(new Object[][] {  }, nomesColunas));
		scrollPane.setViewportView(tbVerProdutos);
		
		lblDescVenda = new JLabel("");
		lblDescVenda.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblDescVenda, "3, 6");
		
		cancelButton = new JButton("Voltar");
		getContentPane().add(cancelButton, "4, 6, left, top");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	/*
	 * Limpa a tabela e preenche com o cabeçalho e com os produtos
	 * do ArrayList que for passado por parâmetro.
	 */
	public void atualizarTabelaELabel(Venda v) {
		tbVerProdutos.setModel(new DefaultTableModel(new Object[][] { }, nomesColunas));
		DefaultTableModel model = (DefaultTableModel) tbVerProdutos.getModel();
		for (ItemVenda iv : v.getListaItemVenda()) {
			Object[] novaLinhaDaTabela = new Object[6];
			novaLinhaDaTabela[0] = iv.getProduto().getEan();
			novaLinhaDaTabela[1] = iv.getProduto().getNome();
			novaLinhaDaTabela[2] = iv.getQtde();
			novaLinhaDaTabela[3] = String.format("R$ %.2f", iv.getValorUnitario());
			novaLinhaDaTabela[4] = String.format("R$ %.2f", iv.getQtde() * iv.getValorUnitario());
			novaLinhaDaTabela[5] = (iv.getProduto().isAtivo()) ? "Ativo" : "Desativado";
			model.addRow(novaLinhaDaTabela);
		}
		lblDescVenda.setText(v.toString());
	}

}
