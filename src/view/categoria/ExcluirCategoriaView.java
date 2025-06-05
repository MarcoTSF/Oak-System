package view.categoria;

import dao.CategoriaDAO;
import model.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExcluirCategoriaView extends JFrame {
    private JTextField tfIdCategoria;
    private JButton btnExcluir, btnCancelar;

    public ExcluirCategoriaView() {
        setTitle("Excluir Categoria");
        setSize(300, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.add(new JLabel("ID da Categoria:"));
        tfIdCategoria = new JTextField();
        inputPanel.add(tfIdCategoria);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnCancelar);

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnExcluir.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(tfIdCategoria.getText());
                boolean sucesso = new CategoriaDAO().excluir(id);

                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Categoria excluída com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível excluir a categoria. Verifique se ela está vinculada a algum produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
