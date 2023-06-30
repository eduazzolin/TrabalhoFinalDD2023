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
import view.paineis.PainelCadastrarProduto;
import view.paineis.PainelConsultarProduto;
import view.paineis.PainelConsultarVenda;
import view.paineis.PainelGerenciarProdutos;
import view.paineis.PainelRegistrarVenda;

public class TelaPrincipal extends JFrame {

	// componentes visuais
	private JMenuBar menuBar;
	private JMenu mnVendas;
	private JMenuItem mntmRegistrarVendas;
	private JMenuItem mntmConsultarVendas;
	private JMenu mnProdutosEstoque;
	private JMenuItem mntmCadastrarProduto;
	private JMenuItem mntmModificarEstoque;
	private JMenuItem mntmConsultarProdutos;
	
	// painéis
	private PainelConsultarVenda painelConsultarVenda;
	protected PainelRegistrarVenda painelRegistrarVenda;
	protected PainelCadastrarProduto painelCadastrarProduto;
	protected PainelGerenciarProdutos painelGerenciarProdutos;
	private PainelConsultarProduto painelConsultarProduto;


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
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TelaPrincipal() {
		
		setTitle("Sistema gerenciador de farmácia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 887, 782);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnVendas = new JMenu("Vendas");
		menuBar.add(mnVendas);

		mntmRegistrarVendas = new JMenuItem("Registrar venda");
		mnVendas.add(mntmRegistrarVendas);
		mntmRegistrarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuRegistrarVenda();
			}
		});

		mntmConsultarVendas = new JMenuItem("Consultar vendas");
		mnVendas.add(mntmConsultarVendas);
		mntmConsultarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuConsultarVenda();
			}
		});

		mnProdutosEstoque = new JMenu("Produtos e Estoque");
		menuBar.add(mnProdutosEstoque);

		mntmCadastrarProduto = new JMenuItem("Cadastrar produtos");
		mnProdutosEstoque.add(mntmCadastrarProduto);
		mntmCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuCadastrarProdutos();
			}
		});

		mntmModificarEstoque = new JMenuItem("Gerenciar estoque");
		mnProdutosEstoque.add(mntmModificarEstoque);
		mntmModificarEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuGerenciarEstoque();
			}
		});

		mntmConsultarProdutos = new JMenuItem("Consultar produtos");
		mnProdutosEstoque.add(mntmConsultarProdutos);
		mntmConsultarProdutos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuConsultarProdutos();
			}
		});
	}

	/**
	 * Instancia o PainelRegistrarVenda e define como contentPane;
	 */
	private void acaoMenuRegistrarVenda() {
		painelRegistrarVenda = new PainelRegistrarVenda();
		setContentPane(painelRegistrarVenda);
		revalidate();
	}

	/**
	 * Instancia o PainelConsultarVenda e define como contentPane;
	 */
	private void acaoMenuConsultarVenda() {
		painelConsultarVenda = new PainelConsultarVenda();
		setContentPane(painelConsultarVenda);
		revalidate();
	}

	/**
	 * Instancia o PainelCadastrarProduto com um objeto novo de Produto como parâmetro
	 * E define o painel como contentPane;
	 */
	private void acaoMenuCadastrarProdutos() {
		Produto novoProduto = new Produto();
		painelCadastrarProduto = new PainelCadastrarProduto(novoProduto);
		setContentPane(painelCadastrarProduto);
		revalidate();
	}
	
	/**
	 * Instancia o PainelGerenciarProdutos e define como contentPane;
	 */
	private void acaoMenuGerenciarEstoque() {
		painelGerenciarProdutos = new PainelGerenciarProdutos();
		setContentPane(painelGerenciarProdutos);
		revalidate();
	}

	/**
	 * Instancia o PainelConsultarProduto e define como contentPane;
	 * Adiciona um action listener no botão "editar" do painel:
	 * Quando pressionado deve instanciar um PainelCadastrarProduto
	 * Com o produto selecionado na tabela como parâmetro
	 */
	private void acaoMenuConsultarProdutos() {
		painelConsultarProduto = new PainelConsultarProduto();
		painelConsultarProduto.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Produto produtoSelecionado = painelConsultarProduto.getProdutoSelecionado();
				painelCadastrarProduto = new PainelCadastrarProduto(produtoSelecionado);
				painelCadastrarProduto.setVisible(true);
				setContentPane(painelCadastrarProduto);
				revalidate();
			}
		});
		setContentPane(painelConsultarProduto);
		revalidate();
	}
}
