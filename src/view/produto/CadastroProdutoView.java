package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroProdutoView extends JFrame {

    private JTextField tfNome, tfPreco, tfUnidade, tfEstoque, tfMinimo, tfMaximo, tfCategoria;
    private JButton btnSalvar;

    public CadastroProdutoView() {
        setTitle("Cadastrar Produto");
        setSize(400, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        tfNome = new JTextField();
        tfPreco = new JTextField();
        tfUnidade = new JTextField();
        tfEstoque = new JTextField();
        tfMinimo = new JTextField();
        tfMaximo = new JTextField();
        tfCategoria = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome);

        formPanel.add(new JLabel("Preço Unitário:"));
        formPanel.add(tfPreco);

        formPanel.add(new JLabel("Unidade:"));
        formPanel.add(tfUnidade);

        formPanel.add(new JLabel("Qtd Estoque:"));
        formPanel.add(tfEstoque);

        formPanel.add(new JLabel("Qtd Mínima:"));
        formPanel.add(tfMinimo);

        formPanel.add(new JLabel("Qtd Máxima:"));
        formPanel.add(tfMaximo);

        formPanel.add(new JLabel("ID Categoria:"));
        formPanel.add(tfCategoria);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnSalvar = new JButton("Salvar");
        buttonPanel.add(btnSalvar);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnSalvar.addActionListener((ActionEvent e) -> {
            Produto p = new Produto();
            p.setNome(tfNome.getText());
            p.setPrecoUnitario(Double.parseDouble(tfPreco.getText()));
            p.setUnidade(tfUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(tfEstoque.getText()));
            p.setQuantidadeMinima(Integer.parseInt(tfMinimo.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(tfMaximo.getText()));
            // p.setCategoria(Integer.parseInt(tfCategoria.getText())); // Validar no set da model

            try {
                new ProdutoDAO().inserir(p);
                JOptionPane.showMessageDialog(this, "Produto salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}