package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CadastroProdutoView extends JFrame{
    private JTextField tfNome, tfPreco, tfUnidade, tfEstoque, tfMinimo, tfMaximo, tfCategoria;
    private JButton btnSalvar;

    public CadastroProdutoView() {
        setTitle("Cadastrar  Produto");
        setSize(400, 300);
        setLayout(new GridLayout(8, 21));

        add(new JLabel("Nome:"));
        tfNome = new JTextField();
        add(tfNome);

        add(new JLabel("Preço Unitário: "));
        tfPreco = new JTextField();
        add(tfPreco);

        add(new JLabel("Unidade: "));
        tfUnidade = new JTextField();
        add(tfUnidade);

        add(new JLabel("Qtd Estoque: "));
        tfEstoque = new JTextField();
        add(tfEstoque);

        add(new JLabel("Qtd Mínima: "));
        tfMinimo = new JTextField();
        add(tfMinimo);

        add(new JLabel("Qtd Máxima: "));
        tfMaximo = new JTextField();
        add(tfMaximo);

        add(new JLabel("ID Categoria: "));
        tfCategoria = new JTextField();
        add(tfCategoria);

        btnSalvar = new JButton("Salvar");
        add(btnSalvar);

        btnSalvar.addActionListener((ActionEvent e) -> {
            Produto p = new Produto();
            p.setNome(tfNome.getText());
            p.setPrecoUnitario(Double.parseDouble(tfPreco.getText()));
            p.setUnidade(tfUnidade.getText());
            p.setQuantidadeEstoque(Integer.parseInt(tfEstoque.getText()));
            p.setQuantidadeMinima(Integer.parseInt(tfMinimo.getText()));
            p.setQuantidadeMaxima(Integer.parseInt(tfMaximo.getText()));
            //p.setCategoria(Integer.parseInt(tfCategoria.getText())); validar no set da model

            try {
                new ProdutoDAO().inserir(p);
                JOptionPane.showMessageDialog(CadastroProdutoView.this, "Produto salvo!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex){
                JOptionPane.showMessageDialog(CadastroProdutoView.this, "Erro ao salvar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
