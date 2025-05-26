package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReajustarPrecoProdutoView extends JFrame {

    private JTextField tfPrecoUnitario;
    private JButton btnAjustar;

    public ReajustarPrecoProdutoView() {
        setTitle("Reajustar  Preços");
        setSize(400, 300);
        setLayout(new GridLayout(2, 2));

        add(new JLabel("Novo Valor Unitário:"));
        tfPrecoUnitario = new JTextField();
        add(tfPrecoUnitario);

        btnAjustar = new JButton("Reajustar");
        add(btnAjustar);

        btnAjustar.addActionListener((ActionEvent e) -> {
            try {
                double novoValor = Double.parseDouble(tfPrecoUnitario.getText());
                new ProdutoDAO().reajustarPrecos(novoValor);
                JOptionPane.showMessageDialog(ReajustarPrecoProdutoView.this, "Produto reajustado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(ReajustarPrecoProdutoView.this, "Erro ao reajustar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }
}
