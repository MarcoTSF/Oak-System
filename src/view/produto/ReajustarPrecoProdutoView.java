package view.produto;

import dao.ProdutoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReajustarPrecoProdutoView extends JFrame {

    private JTextField tfIdProduto, tfValor;
    private JButton btnAjustar, btnCancelar;
    private JRadioButton rbPorcentagem, rbValorDireto;

    public ReajustarPrecoProdutoView() {
        setTitle("Reajustar Preço de Produto");
        setSize(400, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        inputPanel.add(new JLabel("ID do Produto:"));
        tfIdProduto = new JTextField();
        inputPanel.add(tfIdProduto);

        inputPanel.add(new JLabel("Novo Valor ou % de Aumento:"));
        tfValor = new JTextField();
        inputPanel.add(tfValor);

        rbPorcentagem = new JRadioButton("Reajustar por %");
        rbValorDireto = new JRadioButton("Definir valor fixo");
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbPorcentagem);
        bg.add(rbValorDireto);
        rbValorDireto.setSelected(true);

        inputPanel.add(rbPorcentagem);
        inputPanel.add(rbValorDireto);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAjustar = new JButton("Reajustar");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnAjustar);
        buttonPanel.add(btnCancelar);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnAjustar.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(tfIdProduto.getText().trim());
                double valor = Double.parseDouble(tfValor.getText().trim());

                ProdutoDAO dao = new ProdutoDAO();
                int linhasAfetadas;

                if (rbPorcentagem.isSelected()) {
                    linhasAfetadas = dao.reajustarPrecoPorcentagem(id, valor);
                } else {
                    linhasAfetadas = dao.reajustarPrecoDireto(id, valor);
                }

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(this, "Preço atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Produto não encontrado ou não atualizado.", "Atenção", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite valores numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao reajustar preço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}