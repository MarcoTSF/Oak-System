package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroProdutoView extends JFrame {

    private JTextField tfNome, tfPreco, tfEstoque, tfMinimo, tfMaximo, tfCategoria;
    private JComboBox<String> cbUnidade;
    private JButton btnSalvar, btnCancelar;

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
        cbUnidade = new JComboBox<>(new String[]{
                "Lata", "Pacote", "Garrafa", "Caixa", "Saco", "Envelope", "Pote"
        });
        tfEstoque = new JTextField();
        tfMinimo = new JTextField();
        tfMaximo = new JTextField();
        tfCategoria = new JTextField();

        formPanel.add(new JLabel("Nome:"));
        formPanel.add(tfNome);

        formPanel.add(new JLabel("Preço Unitário:"));
        formPanel.add(tfPreco);

        formPanel.add(new JLabel("Unidade:"));
        formPanel.add(cbUnidade);

        formPanel.add(new JLabel("Qtd Estoque:"));
        formPanel.add(tfEstoque);

        formPanel.add(new JLabel("Qtd Mínima:"));
        formPanel.add(tfMinimo);

        formPanel.add(new JLabel("Qtd Máxima:"));
        formPanel.add(tfMaximo);

        formPanel.add(new JLabel("ID Categoria:"));
        formPanel.add(tfCategoria);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnSalvar);
        buttonPanel.add(btnCancelar);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnSalvar.addActionListener((ActionEvent e) -> {
            try {
                Produto p = new Produto();
                p.setNome(tfNome.getText());
                p.setPrecoUnitario(Double.parseDouble(tfPreco.getText()));
                p.setUnidade((String) cbUnidade.getSelectedItem());
                p.setQuantidadeEstoque(Integer.parseInt(tfEstoque.getText()));
                p.setQuantidadeMinima(Integer.parseInt(tfMinimo.getText()));
                p.setQuantidadeMaxima(Integer.parseInt(tfMaximo.getText()));
                //p.setCategoria(Integer.parseInt(tfCategoria.getText()));

                new ProdutoDAO().inserir(p);
                JOptionPane.showMessageDialog(this, "Produto salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}