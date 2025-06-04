package view.produto;

import dao.ProdutoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class ReajustarPrecoProdutoMassaView extends JFrame {
    private JTextField tfPercentual;
    private JButton btnReajustar, btnCancelar;

    public ReajustarPrecoProdutoMassaView() {
        setTitle("Reajustar Preços em Massa");
        setSize(350, 140);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.add(new JLabel("Percentual de Reajuste (%):"));
        tfPercentual = new JTextField();
        tfPercentual.setToolTipText("Ex: 10 para aumentar 10% ou -5 para diminuir 5%");
        inputPanel.add(tfPercentual);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnReajustar = new JButton("Aplicar");
        btnCancelar   = new JButton("Cancelar");
        buttonPanel.add(btnReajustar);
        buttonPanel.add(btnCancelar);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnReajustar.addActionListener((ActionEvent e) -> {
            try {
                String texto = tfPercentual.getText().trim();
                if (texto.isEmpty()) {
                    throw new NumberFormatException("Campo vazio");
                }
                double percentual = Double.parseDouble(texto);
                int qtdAtualizados = new ProdutoDAO().reajustarPrecos(percentual);
                JOptionPane.showMessageDialog(
                        this,
                        String.format("Preços reajustados em %.2f%% para %d produtos.", percentual, qtdAtualizados),
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                );
                dispose();
            } catch (NumberFormatException exNum) {
                JOptionPane.showMessageDialog(
                        this,
                        "Informe um número válido para percentual (ex: 10 ou -5).",
                        "Erro de Formato",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                        this,
                        "Erro ao reajustar preços: " + ex.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
