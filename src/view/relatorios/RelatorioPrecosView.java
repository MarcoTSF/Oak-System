package view.relatorios;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RelatorioPrecosView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public RelatorioPrecosView() {
        setTitle("Relatório de Preços");
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        carregarDados();
    }

    private void inicializarComponentes() {
        JPanel painel = new JPanel(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("Nome");
        modelo.addColumn("Unidade");
        modelo.addColumn("Preço Unitário");
        modelo.addColumn("Quantidade Estoque");
        modelo.addColumn("Categoria");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        JPanel painelInferior = new JPanel();
        painelInferior.add(btnFechar);

        painel.add(scroll, BorderLayout.CENTER);
        painel.add(painelInferior, BorderLayout.SOUTH);

        add(painel);
    }

    private void carregarDados() {
        ProdutoDAO dao = new ProdutoDAO();
        List<Produto> produtos = dao.listarComPrecos();

        modelo.setRowCount(0);

        for (Produto p : produtos) {
            modelo.addRow(new Object[]{
                    p.getNome(),
                    p.getUnidade(),
                    String.format("R$ %.2f", p.getPrecoUnitario()),
                    p.getQuantidadeEstoque(),
                    p.getCategoria().getNome()
            });
        }
    }
}