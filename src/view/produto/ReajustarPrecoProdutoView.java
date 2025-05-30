package view.produto;

import dao.ProdutoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReajustarPrecoProdutoView extends JFrame {

    private JTextField tfPrecoUnitario;
    private JButton btnAjustar, btnCancelar;

    public ReajustarPrecoProdutoView() {
        setTitle("Reajustar Preços");
        setSize(300, 130);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.add(new JLabel("Novo Valor Unitário:"));
        tfPrecoUnitario = new JTextField();
        inputPanel.add(tfPrecoUnitario);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAjustar = new JButton("Reajustar");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnAjustar);
        buttonPanel.add(btnCancelar);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnAjustar.addActionListener((ActionEvent e) -> {
            try {
                double novoValor = Double.parseDouble(tfPrecoUnitario.getText());
                new ProdutoDAO().reajustarPrecos(novoValor);
                JOptionPane.showMessageDialog(this, "Produto reajustado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao reajustar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}