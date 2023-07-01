package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.vo.Produto;
import view.paineis.PainelCadastrarProduto;
import view.paineis.PainelConsultarProduto;
import view.paineis.PainelConsultarVenda;
import view.paineis.PainelGerenciarProdutos;
import view.paineis.PainelRegistrarVenda;

public class TelaPrincipal extends JFrame {
	private static final long serialVersionUID = -7862364356785145045L;

	// componentes visuais
	private JMenuBar menuBar;
	private JMenu mnVendas;
	private JMenuItem mntmRegistrarVendas;
	private JMenuItem mntmConsultarVendas;
	private JMenu mnProdutosEstoque;
	private JMenuItem mntmCadastrarProduto;
	private JMenuItem mntmModificarEstoque;
	private JMenuItem mntmConsultarProdutos;
	private JLabel planoDeFundo;
	
	// painéis
	private PainelConsultarVenda painelConsultarVenda;
	protected PainelRegistrarVenda painelRegistrarVenda;
	protected PainelCadastrarProduto painelCadastrarProduto;
	protected PainelGerenciarProdutos painelGerenciarProdutos;
	private PainelConsultarProduto painelConsultarProduto;
	
	// classes MVC:
	protected Produto produtoSelecionado;


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
		setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/view/img/icone.png")));
		getContentPane().setBackground(new Color(255, 255, 255));
		
		setTitle("Sistema gerenciador de farmácia");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1049, 782);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnVendas = new JMenu("Vendas");
		mnVendas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/Ativo 4.png")));
		menuBar.add(mnVendas);

		mntmRegistrarVendas = new JMenuItem("Registrar venda");
		mntmRegistrarVendas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/plus.png")));
		mnVendas.add(mntmRegistrarVendas);
		mntmRegistrarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuRegistrarVenda();
			}
		});

		mntmConsultarVendas = new JMenuItem("Consultar vendas");
		mntmConsultarVendas.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/search.png")));
		mnVendas.add(mntmConsultarVendas);
		mntmConsultarVendas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuConsultarVenda();
			}
		});

		mnProdutosEstoque = new JMenu("Produtos e Estoque");
		mnProdutosEstoque.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/Ativo 52.png")));
		menuBar.add(mnProdutosEstoque);

		mntmCadastrarProduto = new JMenuItem("Cadastrar produtos");
		mntmCadastrarProduto.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/plus.png")));
		mnProdutosEstoque.add(mntmCadastrarProduto);
		mntmCadastrarProduto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuCadastrarProdutos();
			}
		});

		mntmModificarEstoque = new JMenuItem("Gerenciar estoque");
		mntmModificarEstoque.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/ready-stock.png")));
		mnProdutosEstoque.add(mntmModificarEstoque);
		mntmModificarEstoque.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoMenuGerenciarEstoque();
			}
		});

		mntmConsultarProdutos = new JMenuItem("Consultar produtos");
		mntmConsultarProdutos.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/search.png")));
		mnProdutosEstoque.add(mntmConsultarProdutos);
		
		planoDeFundo = new JLabel("");
		planoDeFundo.setBackground(new Color(255, 255, 255));
		planoDeFundo.setHorizontalAlignment(SwingConstants.CENTER);
		planoDeFundo.setIcon(new ImageIcon(TelaPrincipal.class.getResource("/view/img/logo.jpeg")));
		getContentPane().add(planoDeFundo, BorderLayout.CENTER);
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
		painelGerenciarProdutos = new PainelGerenciarProdutos(produtoSelecionado);
		setContentPane(painelGerenciarProdutos);
		revalidate();
	}

	/**
	 * Instancia o PainelConsultarProduto e define como contentPane;
	 * Adiciona action listeners nos botões "editar" e "estoque" do painel:
	 * "editar" instancia um painel CadastrarProduto passando o produto selecionado como parâmetro;
	 * "estoque" instancia um painel GerenciarProdutos passando o produto selecionado como parâmetro;
	 */
	private void acaoMenuConsultarProdutos() {
		painelConsultarProduto = new PainelConsultarProduto();
		painelConsultarProduto.getBtnEditar().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				produtoSelecionado = painelConsultarProduto.getProdutoSelecionado();
				painelCadastrarProduto = new PainelCadastrarProduto(produtoSelecionado);
				painelCadastrarProduto.setVisible(true);
				setContentPane(painelCadastrarProduto);
				revalidate();
			}
		});
		painelConsultarProduto.getBtnEstoque().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Produto produtoSelecionado = painelConsultarProduto.getProdutoSelecionado();
				painelGerenciarProdutos = new PainelGerenciarProdutos(produtoSelecionado);
				painelGerenciarProdutos.setVisible(true);
				setContentPane(painelGerenciarProdutos);
				revalidate();
			}
		});
		setContentPane(painelConsultarProduto);
		revalidate();
	}
}
