package view;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import model.vo.Produto;
import view.paineis.PainelRegistrarVenda;
import view.paineis.PainelCadastrarProduto;
import view.paineis.PainelConsultarEstoque;
import view.paineis.PainelConsultarVenda;
import view.paineis.PainelGerenciarProdutos;

public class TelaPrincipal extends JFrame {

	private JMenuBar menuBar;
	private JMenu mnVendas;
	private JMenuItem mntmRegistrarVendas;
	private JMenuItem mntmConsultarVendas;
	private JMenu mnProdutosEstoque;
	private JMenuItem mntmCadastrarProduto;
	private JMenuItem mntmModificarEstoque;
	private JMenuItem mntmConsultarProdutos;
	private PainelConsultarVenda painelConsultarVenda;
	protected PainelRegistrarVenda painelRegistrarVenda;
	protected PainelCadastrarProduto painelCadastrarProduto;
	protected PainelGerenciarProdutos painelGerenciarProdutos;
	protected PainelConsultarEstoque painelConsultarEstoque;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		setTitle("Sistema gerenciador de farmácia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 848, 782);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnVendas = new JMenu("Vendas");
		menuBar.add(mnVendas);

		mntmRegistrarVendas = new JMenuItem("Registrar");
		mntmRegistrarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelRegistrarVenda = new PainelRegistrarVenda();
				setContentPane(painelRegistrarVenda);
				revalidate();
			}
		});
		mnVendas.add(mntmRegistrarVendas);

		mntmConsultarVendas = new JMenuItem("Consultar");
		mntmConsultarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				painelConsultarVenda = new PainelConsultarVenda();
				setContentPane(painelConsultarVenda);
				revalidate();
			}
		});
		mnVendas.add(mntmConsultarVendas);

		mnProdutosEstoque = new JMenu("Produtos e Estoque");
		menuBar.add(mnProdutosEstoque);

		mntmCadastrarProduto = new JMenuItem("Cadastrar produtos");
		mntmCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Produto novoProduto = new Produto();
				painelCadastrarProduto = new PainelCadastrarProduto(novoProduto);
				setContentPane(painelCadastrarProduto);
				revalidate();

			}
		});
		mnProdutosEstoque.add(mntmCadastrarProduto);

		mntmModificarEstoque = new JMenuItem("Gerenciar estoque");
		mntmModificarEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				painelGerenciarProdutos = new PainelGerenciarProdutos();
				setContentPane(painelGerenciarProdutos);
				revalidate();
				
			}
		});
		mnProdutosEstoque.add(mntmModificarEstoque);

		mntmConsultarProdutos = new JMenuItem("Consultar produtos");
		mntmConsultarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				painelConsultarEstoque = new PainelConsultarEstoque();
				painelConsultarEstoque.getBtnEditar().addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						Produto produtoSelecionado = painelConsultarEstoque.getProdutoSelecionado();
						painelCadastrarProduto = new PainelCadastrarProduto(produtoSelecionado);
						painelCadastrarProduto.setVisible(true);
						setContentPane(painelCadastrarProduto);
						revalidate();
					}
				});
				setContentPane(painelConsultarEstoque);
				revalidate();
				
			}
		});
		mnProdutosEstoque.add(mntmConsultarProdutos);
	}
}
