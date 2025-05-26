package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// IMPORT DAS TELAS DE PRODUTOS
import view.produto.CadastroProdutoView;
import view.produto.ConsultarProdutoView;
import view.produto.ReajustarPrecoProdutoView;

/**
 * Classe principal da aplicação que exibe a tela inicial do sistema.
 * Contém o menu principal com acesso a todas as funcionalidades.
 */
public class TelaPrincipal extends JFrame {
    
    // Componentes da interface
    private JMenuBar menuBar;
    private JMenu menuProdutos, menuCategorias, menuMovimentacoes, menuRelatorios;
    private JMenuItem miCadastrarProduto, miConsultarProdutos, miReajustarPrecos;
    private JMenuItem miCadastrarCategoria, miConsultarCategorias;
    private JMenuItem miEntradaEstoque, miSaidaEstoque;
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
        miReajustarPrecos = new JMenuItem("Reajustar Preços");
        menuProdutos.add(miCadastrarProduto);
        menuProdutos.add(miConsultarProdutos);
        menuProdutos.addSeparator();
        menuProdutos.add(miReajustarPrecos);
        
        // Menu Categorias
        menuCategorias = new JMenu("Categorias");
        miCadastrarCategoria = new JMenuItem("Cadastrar");
        miConsultarCategorias = new JMenuItem("Consultar");
        menuCategorias.add(miCadastrarCategoria);
        menuCategorias.add(miConsultarCategorias);
        
        // Menu Movimentações
        menuMovimentacoes = new JMenu("Movimentações");
        miEntradaEstoque = new JMenuItem("Entrada");
        miSaidaEstoque = new JMenuItem("Saída");
        menuMovimentacoes.add(miEntradaEstoque);
        menuMovimentacoes.add(miSaidaEstoque);
        
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
        
        // Barra de status
        lblStatus = new JLabel("Sistema de Controle de Estoque - Pronto");
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
        painelPrincipal.add(lblStatus, BorderLayout.SOUTH);
        
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
                lblStatus.setText("Abrindo cadastro de produtos...");
                new CadastroProdutoView().setVisible(true);
            }
        });

        miConsultarProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblStatus.setText("Abrindo consulta de produtos...");
                new ConsultarProdutoView().setVisible(true);
            }
        });

        miReajustarPrecos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lblStatus.setText("Abrindo reajuste de preços...");
                new ReajustarPrecoProdutoView().setVisible(true);
            }
        });
        
        // Eventos do menu Categorias
        miCadastrarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria aberta a tela de cadastro de categorias
                lblStatus.setText("Abrindo cadastro de categorias...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de cadastro de categorias a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miConsultarCategorias.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria aberta a tela de consulta de categorias
                lblStatus.setText("Abrindo consulta de categorias...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de consulta de categorias a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Eventos do menu Movimentações
        miEntradaEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria aberta a tela de entrada de estoque
                lblStatus.setText("Abrindo entrada de estoque...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de entrada de estoque a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miSaidaEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria aberta a tela de saída de estoque
                lblStatus.setText("Abrindo saída de estoque...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de saída de estoque a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Eventos do menu Relatórios
        miRelatorioPrecos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria gerado o relatório de preços
                lblStatus.setText("Gerando relatório de preços...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de relatório de preços a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miRelatorioBalanco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria gerado o relatório de balanço
                lblStatus.setText("Gerando relatório de balanço...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de relatório de balanço a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miRelatorioAbaixoMinimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria gerado o relatório de produtos abaixo do mínimo
                lblStatus.setText("Gerando relatório de produtos abaixo do mínimo...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de relatório de produtos abaixo do mínimo a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miRelatorioAcimaMaximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria gerado o relatório de produtos acima do máximo
                lblStatus.setText("Gerando relatório de produtos acima do máximo...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de relatório de produtos acima do máximo a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        miRelatorioPorCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aqui seria gerado o relatório de produtos por categoria
                lblStatus.setText("Gerando relatório de produtos por categoria...");
                JOptionPane.showMessageDialog(TelaPrincipal.this, 
                        "Funcionalidade de relatório de produtos por categoria a ser implementada.", 
                        "Informação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }
}
