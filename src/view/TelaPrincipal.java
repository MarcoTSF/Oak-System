package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// IMPORT DAS TELAS DE PRODUTOS
import view.produto.CadastroProdutoView;
import view.produto.ConsultarProdutoView;
import view.produto.ExcluirProdutoView;
import view.produto.ReajustarPrecoProdutoMassaView;
import view.produto.ReajustarPrecoProdutoUnitarioView;

// IMPORT DAS TELAS DE CATEGORIAS
import view.categoria.CadastrarCategoriaView;
import view.categoria.ConsultarCategoriasView;
import view.categoria.ExcluirCategoriaView;

// IMPORT DAS TELAS DE MOVIMENTAÇÕES
import view.movimentacoes.EntradaEstoqueView;
import view.movimentacoes.SaidaEstoqueView;
import view.movimentacoes.HistoricoEstoqueView;

// IMPORT DAS TELAS DE RELATORIOS
import view.relatorios.RelatorioAbaixoMinimoView;
import view.relatorios.RelatorioAcimaMaximoView;
import view.relatorios.RelatorioPorCategoriaView;
import view.relatorios.RelatorioPrecosView;
import view.relatorios.RelatorioBalancoView;

/**
 * Classe principal da aplicação que exibe a tela inicial do sistema.
 * Contém o menu principal com acesso a todas as funcionalidades.
 */
public class TelaPrincipal extends JFrame {
    
    // Componentes da interface
    private JMenuBar menuBar;
    private JMenu menuProdutos, menuCategorias, menuMovimentacoes, menuRelatorios;
    private JMenuItem miCadastrarProduto, miConsultarProdutos, miExcluirProduto, miReajustarPrecosMassa, miReajustarPrecosUnitario;
    private JMenuItem miCadastrarCategoria, miConsultarCategorias, miExcluirCategoria;
    private JMenuItem miEntradaEstoque, miSaidaEstoque, miHistoricoEstoque;
    private JMenuItem miRelatorioPrecos, miRelatorioBalanco, miRelatorioAbaixoMinimo;
    private JMenuItem miRelatorioAcimaMaximo, miRelatorioPorCategoria;
    private JPanel painelPrincipal;
    private JLabel lblStatus;
    
    /**
     * Construtor da classe TelaPrincipal.
     * Inicializa a interface gráfica.
     */
    public TelaPrincipal() {
        // Configurações básicas da janela
        setTitle("Sistema de Controle de Estoque");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializa componentes
        inicializarComponentes();
        
        // Configura layout
        configurarLayout();
        
        // Configura eventos
        configurarEventos();
        
        // Exibe a janela
        setVisible(true);
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        // Barra de menu
        menuBar = new JMenuBar();
        
        // Menu Produtos
        menuProdutos = new JMenu("Produtos");
        miCadastrarProduto = new JMenuItem("Cadastrar");
        miConsultarProdutos = new JMenuItem("Consultar");
        miExcluirProduto = new JMenuItem("Excluir");
        miReajustarPrecosMassa = new JMenuItem("Reajustar Preços em Massa");
        miReajustarPrecosUnitario = new JMenuItem("Reajustar Preço Unitário");
        menuProdutos.add(miCadastrarProduto);
        menuProdutos.add(miConsultarProdutos);
        menuProdutos.add(miExcluirProduto);
        menuProdutos.add(miReajustarPrecosMassa);
        menuProdutos.add(miReajustarPrecosUnitario);
        
        // Menu Categorias
        menuCategorias = new JMenu("Categorias");
        miCadastrarCategoria = new JMenuItem("Cadastrar");
        miConsultarCategorias = new JMenuItem("Consultar");
        miExcluirCategoria = new JMenuItem("Excluir");
        menuCategorias.add(miCadastrarCategoria);
        menuCategorias.add(miConsultarCategorias);
        menuCategorias.add(miExcluirCategoria);
        
        // Menu Movimentações
        menuMovimentacoes = new JMenu("Movimentações");
        miEntradaEstoque = new JMenuItem("Entrada");
        miSaidaEstoque = new JMenuItem("Saída");
        miHistoricoEstoque = new JMenuItem("Histórico de Movimentações");
        menuMovimentacoes.add(miEntradaEstoque);
        menuMovimentacoes.add(miSaidaEstoque);
        menuMovimentacoes.add(miHistoricoEstoque);
        
        // Menu Relatórios
        menuRelatorios = new JMenu("Relatórios");
        miRelatorioPrecos = new JMenuItem("Lista de Preços");
        miRelatorioBalanco = new JMenuItem("Balanço Físico/Financeiro");
        miRelatorioAbaixoMinimo = new JMenuItem("Produtos Abaixo do Mínimo");
        miRelatorioAcimaMaximo = new JMenuItem("Produtos Acima do Máximo");
        miRelatorioPorCategoria = new JMenuItem("Produtos por Categoria");
        menuRelatorios.add(miRelatorioPrecos);
        menuRelatorios.add(miRelatorioBalanco);
        menuRelatorios.addSeparator();
        menuRelatorios.add(miRelatorioAbaixoMinimo);
        menuRelatorios.add(miRelatorioAcimaMaximo);
        menuRelatorios.add(miRelatorioPorCategoria);
        
        // Adiciona menus à barra
        menuBar.add(menuProdutos);
        menuBar.add(menuCategorias);
        menuBar.add(menuMovimentacoes);
        menuBar.add(menuRelatorios);
        
        // Painel principal
        painelPrincipal = new JPanel();
    }
    
    /**
     * Configura o layout da interface.
     */
    private void configurarLayout() {
        // Define a barra de menu
        setJMenuBar(menuBar);
        
        // Configura o painel principal
        painelPrincipal.setLayout(new BorderLayout());
        
        // Adiciona imagem de fundo
        JLabel lblLogo = new JLabel();
        lblLogo.setHorizontalAlignment(JLabel.CENTER);
        // Aqui seria adicionada uma imagem de logo
        // lblLogo.setIcon(new ImageIcon("caminho/para/logo.png"));
        
        // Adiciona componentes ao painel principal
        painelPrincipal.add(lblLogo, BorderLayout.CENTER);
        
        // Adiciona o painel principal à janela
        add(painelPrincipal);
    }
    
    /**
     * Configura os eventos dos componentes.
     */
    private void configurarEventos() {
        // Eventos do menu Produtos
        miCadastrarProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastroProdutoView().setVisible(true);
            }
        });

        miConsultarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultarProdutoView().setVisible(true);
            }
        });

        miExcluirProduto.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { new ExcluirProdutoView().setVisible(true); }
        });

        miReajustarPrecosMassa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReajustarPrecoProdutoMassaView().setVisible(true);
            }
        });

        miReajustarPrecosUnitario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReajustarPrecoProdutoUnitarioView().setVisible(true);
            }
        });
        
        // Eventos do menu Categorias
        miCadastrarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CadastrarCategoriaView().setVisible(true);
            }
        });

        miConsultarCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ConsultarCategoriasView().setVisible(true);
            }
        });

        miExcluirCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ExcluirCategoriaView().setVisible(true);
            }
        });
        
        // Eventos do menu Movimentações
        miEntradaEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EntradaEstoqueView().setVisible(true);
            }
        });

        miSaidaEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SaidaEstoqueView().setVisible(true);
            }
        });

        miHistoricoEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HistoricoEstoqueView().setVisible(true);
            }
        });

        // Eventos do menu Relatórios
        miRelatorioPrecos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatorioPrecosView().setVisible(true);
            }
        });

        miRelatorioBalanco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatorioBalancoView().setVisible(true);
            }
        });

        miRelatorioAbaixoMinimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatorioAbaixoMinimoView().setVisible(true);
            }
        });

        miRelatorioAcimaMaximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatorioAcimaMaximoView().setVisible(true);
            }
        });

        miRelatorioPorCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RelatorioPorCategoriaView().setVisible(true);
            }
        });
    }
}
