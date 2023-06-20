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

public class DialogVerProdutos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable tbVerProdutos;
	private String[] nomesColunas = {  "EAN", "Produto", "Qtde", "Valor Unitário", "Valor Total" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		setBounds(100, 100, 596, 379);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 562, 291);
		contentPanel.add(scrollPane);
		
		tbVerProdutos = new JTable();
		tbVerProdutos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		scrollPane.setViewportView(tbVerProdutos);
		{
			JPanel btVoltar = new JPanel();
			btVoltar.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(btVoltar, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Voltar");
				cancelButton.setActionCommand("Cancel");
				btVoltar.add(cancelButton);
			}
		}
	}
	
	public void atualizarTabela(ArrayList<ItemVenda> listaItemVenda) {
		this.limparTabela();
		DefaultTableModel model = (DefaultTableModel) tbVerProdutos.getModel();

		for (ItemVenda iv : listaItemVenda) {
			Object[] novaLinhaDaTabela = new Object[5];
			novaLinhaDaTabela[0] = iv.getProduto().getEan();
			novaLinhaDaTabela[1] = iv.getProduto().getNome();
			novaLinhaDaTabela[2] = iv.getQtde();
			novaLinhaDaTabela[3] = String.format("R$ %.2f", iv.getValorUnitario());
			novaLinhaDaTabela[4] = iv.getQtde()*iv.getProduto().getValor();
			model.addRow(novaLinhaDaTabela);
		}
	}

	private void limparTabela() {
		tbVerProdutos.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		
	}
	
	
}
