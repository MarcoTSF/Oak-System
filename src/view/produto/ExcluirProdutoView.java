package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class ExcluirProdutoView extends JFrame {

    private JComboBox<Produto> cbProdutos;
    private JButton btnExcluir, btnCancelar;

    public ExcluirProdutoView() {
        setTitle("Excluir Produto");
        setSize(400, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cbProdutos = new JComboBox<>();
        carregarProdutos();

        JPanel formPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        formPanel.add(new JLabel("Selecione o produto para excluir:"));
        formPanel.add(cbProdutos);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExcluir = new JButton("Excluir");
        btnCancelar = new JButton("Cancelar");

        buttonPanel.add(btnExcluir);
        buttonPanel.add(btnCancelar);

        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        btnExcluir.addActionListener((ActionEvent e) -> {
            Produto selecionado = (Produto) cbProdutos.getSelectedItem();
            if (selecionado != null) {
                int confirmacao = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja excluir o produto \"" + selecionado.getNome() + "\"?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION);
                if (confirmacao == JOptionPane.YES_OPTION) {
                    boolean sucesso = new ProdutoDAO().excluir(selecionado.getId());
                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Produto excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao excluir o produto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void carregarProdutos() {
        try {
            List<Produto> produtos = new ProdutoDAO().listarTodos();
            for (Produto p : produtos) {
                cbProdutos.addItem(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}