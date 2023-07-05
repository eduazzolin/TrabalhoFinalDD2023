package view.paineis;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import controller.ProdutoController;
import model.exception.CampoInvalidoException;
import model.exception.ProdutoInvalidoException;
import model.seletor.ProdutoSeletor;
import model.vo.Produto;
import model.vo.Venda;
import view.componentesExternos.JNumberFormatField;

public class PainelConsultarProduto extends JPanel {
	private static final long serialVersionUID = 6738959749427589665L;
	
	// componentes visuais:
	private JNumberFormatField ftfValorMinimo;
	private JNumberFormatField ftfValorMaximo;
	private JTextField txtProduto;
	private JTextField tfEan;
	private JLabel lbFiltrarConsulta;
	private JLabel lbEan;
	private JLabel lbValorMinimo;
	private JLabel lbValorMaximo;
	private JLabel lbNome;
	private JLabel lbResultados;
	private JLabel lbPaginas;
	private JButton btnConsultar;
	private JButton btnEditar;
	private JButton btnExportar;
	private JButton btnVoltar;
	private JButton btnAvancar;
	private JButton btnRemover;
	private JButton btnEstoque;
	private JSeparator separator;
	private JSeparator separator2;
	private JTable table;
	
	// classes mvc:
	protected Venda vendaSelecionada;
	protected ProdutoSeletor seletor;
	private Produto produtoSelecionado;
	private ProdutoController produtoController = new ProdutoController();
	
	// atributos simples:
	private Double valorMaximo;
	private Double valorMinimo;
	private ArrayList<Produto> produtos;
	
	// atributos da paginação
	private final int TAMANHO_PAGINA = 15;
	private int paginaAtual = 1;
	private int totalPaginas = 0;

	// atributos de valor padrão:
	private String[] nomesColunas = {  "ID", "NOME", "DESCRIÇÃO", "EAN" , "VALOR", "ESTOQUE","ATIVO"};
	

	public PainelConsultarProduto() {
		setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(36dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(72dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(71dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(25dlu;default):grow(50)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(42dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("left:max(25dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(50dlu;default)"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(31dlu;default)"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("14dlu"),
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(10dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(20dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(30dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(7dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(0dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(230dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(22dlu;default)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(23dlu;default)"),}));
		
		
		lbFiltrarConsulta = new JLabel("Filtrar consulta:");
		add(lbFiltrarConsulta, "4, 3, left, bottom");
		
		separator = new JSeparator();
		add(separator, "4, 5, 17, 1, default, top");
		
		lbEan = new JLabel("EAN:");
		add(lbEan, "4, 8, right, default");
		
		tfEan = new JTextField();
		add(tfEan, "6, 8, 15, 1, fill, default");
		tfEan.setColumns(10);
		
		lbValorMinimo = new JLabel("Valor mínimo: R$");
		add(lbValorMinimo, "4, 10, right, default");
		
		ftfValorMinimo = new JNumberFormatField(2);
		add(ftfValorMinimo, "6, 10, 6, 1, fill, default");
		
		lbValorMaximo = new JLabel("Valor máximo: R$");
		add(lbValorMaximo, "14, 10, right, default");
		
		ftfValorMaximo = new JNumberFormatField(2);
		add(ftfValorMaximo, "16, 10, 5, 1, fill, default");
		
		lbNome = new JLabel("Nome do Produto:");
		add(lbNome, "4, 7, right, default");
		
		txtProduto = new JTextField();
		add(txtProduto, "6, 7, 15, 1, fill, default");
		txtProduto.setColumns(10);

		lbPaginas = new JLabel("");
		lbPaginas.setHorizontalAlignment(SwingConstants.CENTER);
		add(lbPaginas, "6, 24");
		
		lbResultados = new JLabel("Resultados:");
		add(lbResultados, "4, 16");
		
		separator2 = new JSeparator();
		add(separator2, "4, 18, 17, 1, default, top");
		
		btnConsultar = new JButton("Consultar");
		btnConsultar.setMaximumSize(new Dimension(50, 21));
		add(btnConsultar, "18, 12, 3, 1");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoConsultar();
			}
		});
		
		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas, }, nomesColunas));
		add(table, "4, 20, 17, 3, fill, fill");
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				acaoCliqueTabela();
			}
		});
		limparTabela();
		
		btnEstoque = new JButton("Estoque");
		btnEstoque.setEnabled(false);
		add(btnEstoque, "14, 24");
		// o action listener do botão estoque está na tela principal
		// para poder mandar o objeto para o painel "PainelCadastrarProduto"
		
		
		btnEditar = new JButton("   Editar   ");
		btnEditar.setEnabled(false);
		add(btnEditar, "18, 24");
		// o action listener do botão editar está na tela principal
		// para poder mandar o objeto para o painel "PainelCadastrarProduto"
		
		btnVoltar = new JButton("<< Voltar");
		btnVoltar.setEnabled(false);
		add(btnVoltar, "4, 24");
		btnVoltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoVoltar();
			}
		});
		
		btnAvancar = new JButton("Avançar >>");
		btnAvancar.setEnabled(false);
		add(btnAvancar, "8, 24");
		btnAvancar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoAvancar();
			}
		});
		
		btnRemover = new JButton("Remover");
		btnRemover.setEnabled(false);
		add(btnRemover, "16, 24");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					acaoBotaoRemover();
				} catch (ProdutoInvalidoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnExportar = new JButton("Exportar");
		btnExportar.setEnabled(false);
		add(btnExportar, "20, 24");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acaoBotaoExportar();
			}
		});
		
	}

	/**
	 * Busca os elementos no banco de dados com os filtros:
	 * 
	 * Valida se os campos "máximos" são maiores que os "mínimos".
	 * Busca no banco com os filtros;
	 * Atualiza a tabela;
	 * Atualiza a paginação;
	 */
	protected void acaoBotaoConsultar() {
		try {
			paginaAtual = 1;
			totalPaginas = 0;
			buscarProdutosComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e1) {
			JOptionPane.showMessageDialog(btnConsultar, e1.getMessage(), "Campo inválido", 1);
		}
	}

	/**
	 * Desativa o produto selecionado no banco de dados:
	 * 
	 * Valida se a seleção está funcionando;
	 * Marca como false o atributo "ativo" no banco de dados;
	 * Atualiza a tabela e os botões.
	 */
	protected void acaoBotaoRemover() throws ProdutoInvalidoException {
		int confirmacao = JOptionPane.showOptionDialog(null, "Tem certeza que deseja remover produto?", "Confirmação", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,  
				new String[] {"Remover", "Cancelar"}, null);
		if(confirmacao == 0) {
			if(produtoController.removerProduto(produtoSelecionado)) {
				JOptionPane.showMessageDialog(btnRemover, "Produto removido com sucesso!", "Sucesso", 1);
				acaoBotaoConsultar();
				btnRemover.setEnabled(false);
				btnEditar.setEnabled(false);
				btnEstoque.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(btnRemover, "Erro ao remover venda", "Erro", 1);
			}
		}
	}

	/**
	 * Retorna à página anterior:
	 * 
	 * Diminui 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoVoltar() {
		paginaAtual--;
		try {
			buscarProdutosComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
	}
	
	/**
	 * Avança para a página seguinte:
	 * 
	 * Aumenta 1 no atributo paginaAtual e refaz a busca;
	 * Atualiza a tabela, o label de página e os botões "avançar" e "voltar";
	 */
	protected void acaoBotaoAvancar() {
		paginaAtual++;
		try {
			buscarProdutosComFiltrosEAtualizarTabela();
		} catch (CampoInvalidoException e) {
			JOptionPane.showMessageDialog(btnConsultar, e.getMessage(), "Campo inválido", 1);
		}
		lbPaginas.setText(paginaAtual + " / " + totalPaginas);
		ativarOuDesativarBotoesVoltarAvancar();
		
	}
	
	/**
	 * Ativa ou desativa os botões de navegação a partir da validação das páginas.
	 */
	private void ativarOuDesativarBotoesVoltarAvancar() {
		btnVoltar.setEnabled(paginaAtual > 1);
		btnAvancar.setEnabled(paginaAtual < totalPaginas);
	}

	/**
	 * Busca produtos no banco com os filtros e atribui o resultado à tabela:
	 * 
	 * Cria um objeto seletor a partir dos filtros inseridos e atributos de paginação;
	 * Valida os valores máximos dos filtros para confirmar se eles não são menores que os mínimos;
	 * Se os campos de valor estiverem com 0 (valor padrão) atribui "null";
	 * Busca no banco com os filtros e atribui o resultado ao ArrayList "produtos";
	 * Atualiza a tabela, a paginação e os botões de navegação e exportar;
	 * @throws CampoInvalidoException;
	 */
	private void buscarProdutosComFiltrosEAtualizarTabela() throws CampoInvalidoException {
		// criação do seletor e validação:
		seletor = new ProdutoSeletor();
		seletor.setLimite(TAMANHO_PAGINA);
		seletor.setPagina(paginaAtual);
		valorMinimo = ftfValorMinimo.getValue().doubleValue();
		valorMaximo = ftfValorMaximo.getValue().doubleValue();

		if (valorMinimo > valorMaximo && valorMaximo > 0) {
			throw new CampoInvalidoException("Valor mínimo não pode ser maior que o valor máximo.");
		}
		seletor.setNome(txtProduto.getText());
		seletor.setEan(tfEan.getText());
		seletor.setValorMaximo(valorMaximo > 0 ? valorMaximo : null);
		seletor.setValorMinimo(valorMinimo > 0 ? valorMinimo : null);
		
		// busca no banco e atualiza:
		produtos = produtoController.consultarComFiltros(seletor);
		atualizarTabela();
		atualizarQuantidadePaginas();
		ativarOuDesativarBotoesVoltarAvancar();
		btnExportar.setEnabled(produtos != null && produtos.size()>0);
	}

	/**
	 * Deixa a tabela somente com o cabeçalho e tamanhos padrão;
	 */
	private void limparTabela() {
		table.setModel(new DefaultTableModel(new Object[][] { nomesColunas }, nomesColunas));
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(1).setMaxWidth(900);
		table.getColumnModel().getColumn(2).setMaxWidth(900);
		table.getColumnModel().getColumn(3).setMaxWidth(200);
		table.getColumnModel().getColumn(4).setMaxWidth(100);
		table.getColumnModel().getColumn(5).setMaxWidth(100);
		table.getColumnModel().getColumn(6).setMaxWidth(100);
		table.setRowHeight(20);
	}
	
	/**
	 * Atualiza a tabela baseada no ArrayList "produtos":
	 * 
	 * Limpa a tabela;
	 * Pega a referência do model da tabela e adiciona nele uma linha para cada produto no ArrayList produtos;
	 */
	private void atualizarTabela() {
		this.limparTabela();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Produto p : this.produtos) {
			Object[] novaLinhaDaTabela = new Object[7];
			novaLinhaDaTabela[0] = p.getId();
			novaLinhaDaTabela[1] = p.getNome();
			novaLinhaDaTabela[2] = p.getDescricao();
			novaLinhaDaTabela[3] = p.getEan();
			novaLinhaDaTabela[4] = String.format("R$ %.2f", p.getValor());
			novaLinhaDaTabela[5] = p.getEstoque();
			novaLinhaDaTabela[6] = (p.isAtivo() ? "Ativo" : "Desativado");
			model.addRow(novaLinhaDaTabela);
		}
	}
	
	/**
	 * Calcula e atualiza os atributos e labels de paginação:
	 * 
	 * Busca no banco quantas linhas retornam com os filtros;
	 * Calcula o total de páginas dividindo o total de linhas pelo tamanho da página
	 * Caso o resultado seja um número decimal, o total é arredondado para cima;
	 * Atualiza o lbPaginas deixando sempre o total como no mínimo 1;
	 */
	private void atualizarQuantidadePaginas() {
		int totalRegistros = produtoController.contarTotalRegistrosComFiltros(seletor);
		totalPaginas = totalRegistros / TAMANHO_PAGINA;
		if(totalRegistros % TAMANHO_PAGINA > 0) { 
			totalPaginas++;
		}
		lbPaginas.setText(paginaAtual + " / " + (totalPaginas == 0 ? 1 : totalPaginas));
	}

	/**
	 * Atribui o produto da linha selecionada ao objeto "produtoSelecionado":
	 * 
	 * Valida se a seleção não está no cabeçalho (índice 0);
	 * Atribui o produto selecionado ao objeto "produtoSelecionado" através do índice da linha e do índice do array "produtos";
	 * Atualiza os botões "remover", "editar" e "estoque";
	 */
	private void acaoCliqueTabela() {
		int indiceSelecionado = table.getSelectedRow();
		if (indiceSelecionado > 0) {
			btnEditar.setEnabled(true);
			btnRemover.setEnabled(true);
			btnEstoque.setEnabled(true);
			produtoSelecionado = produtos.get(indiceSelecionado - 1);
		} else {
			btnEditar.setEnabled(false);
			btnRemover.setEnabled(false);
			btnEstoque.setEnabled(false);
		}
	}

	/**
	 * Exporta os resultados para uma planilha excel .xlsx:
	 * 
	 * Exibe um JOptionPane perguntando onde quer salvar;
	 * Busca no banco os resultados com os filtros porém sem paginação;
	 * (controller) Valida se o destino foi escolhido e se a consulta não está vazia;
	 * Exporta a planilha;
	 */
	private void acaoBotaoExportar() {
		JFileChooser janelaSelecaoDestinoArquivo = new JFileChooser();
		janelaSelecaoDestinoArquivo.setDialogTitle("Selecione um destino para a planilha...");
		int opcaoSelecionada = janelaSelecaoDestinoArquivo.showSaveDialog(null);
		if (opcaoSelecionada == JFileChooser.APPROVE_OPTION) {
			String caminhoEscolhido = janelaSelecaoDestinoArquivo.getSelectedFile().getAbsolutePath();
			String resultado = "Erro ao gerar relatório.";
			try {
				ProdutoSeletor seletorParaExportar = new ProdutoSeletor();
				valorMinimo = ftfValorMinimo.getValue().doubleValue();
				valorMaximo = ftfValorMaximo.getValue().doubleValue();

				if (valorMinimo > valorMaximo && valorMaximo > 0) {
					throw new CampoInvalidoException("Valor mínimo não pode ser maior que o valor máximo.");
				}
				seletorParaExportar.setNome(txtProduto.getText());
				seletorParaExportar.setEan(tfEan.getText());
				seletorParaExportar.setValorMaximo(valorMaximo > 0 ? valorMaximo : null);
				seletorParaExportar.setValorMinimo(valorMinimo > 0 ? valorMinimo : null);
				ArrayList<Produto> produtosParaExportar = produtoController.consultarComFiltros(seletorParaExportar);
				resultado = produtoController.gerarPlanilhaProdutos(produtosParaExportar, caminhoEscolhido);
				JOptionPane.showMessageDialog(null, resultado);
			} catch (CampoInvalidoException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
			} catch (ProdutoInvalidoException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	// Getters e setters:
	public JButton getBtnEditar() {
		return btnEditar;
	}

	public void setBtnEditar(JButton btnEditar) {
		this.btnEditar = btnEditar;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public JButton getBtnEstoque() {
		return btnEstoque;
	}

	public void setBtnEstoque(JButton btnEstoque) {
		this.btnEstoque = btnEstoque;
	}
	
}
