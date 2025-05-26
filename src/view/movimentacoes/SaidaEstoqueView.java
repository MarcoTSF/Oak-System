package view.movimentacoes;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import model.Movimentacao;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class SaidaEstoqueView extends JFrame {
    private JTextField campoProdutoId;
    private JTextField campoQuantidade;
    private JTextField campoData;
    private JTextField campoHora;
    private JTextArea campoObservacao;
    private JButton botaoSalvar, botaoCancelar;

    public SaidaEstoqueView() {
        setTitle("Saída de Estoque");
        setSize(450, 400);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridBagLayout());
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        campoProdutoId = new JTextField();
        campoQuantidade = new JTextField();
        campoData = new JTextField();
        campoHora = new JTextField();
        campoObservacao = new JTextArea(3, 20);
        campoObservacao.setLineWrap(true);
        campoObservacao.setWrapStyleWord(true);
        JScrollPane scrollObservacao = new JScrollPane(campoObservacao);

        String[] labels = {
                "ID Produto:", "Quantidade:", "Data (YYYY-MM-DD):",
                "Hora (HH:mm):", "Observação:"
        };
        JTextField[] fields = {
                campoProdutoId, campoQuantidade, campoData, campoHora
        };

        for (int i = 0; i < fields.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weightx = 0.3;
            painelCampos.add(new JLabel(labels[i]), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            painelCampos.add(fields[i], gbc);
        }

        // Observação
        gbc.gridx = 0;
        gbc.gridy = fields.length;
        gbc.weightx = 0.3;
        painelCampos.add(new JLabel(labels[4]), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        painelCampos.add(scrollObservacao, gbc);

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(this::salvarMovimentacao);
        botaoCancelar.addActionListener(e -> dispose());
    }

    private void salvarMovimentacao(ActionEvent e) {
        try {
            int produtoId = Integer.parseInt(campoProdutoId.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            String dataStr = campoData.getText();
            String horaStr = campoHora.getText();
            String observacao = campoObservacao.getText();

            ProdutoDAO produtoDAO = new ProdutoDAO();
            Produto produto = produtoDAO.consultar(produtoId);
            if (produto == null) {
                JOptionPane.showMessageDialog(this, "Produto não encontrado!");
                return;
            }

            LocalDateTime dataHora;
            if (horaStr == null || horaStr.isEmpty()) {
                dataHora = LocalDateTime.parse(dataStr + "T00:00:00");
            } else {
                dataHora = LocalDateTime.parse(dataStr + "T" + horaStr + ":00");
            }

            if (produto.getQuantidadeEstoque() < quantidade) {
                JOptionPane.showMessageDialog(this, "Estoque insuficiente para essa saída.");
                return;
            }

            Movimentacao m = new Movimentacao();
            m.setProduto(produto);
            m.setQuantidade(quantidade);
            m.setTipo("Saída");
            m.setDataHora(dataHora);
            m.setObservacao(observacao);

            MovimentacaoDAO dao = new MovimentacaoDAO();
            dao.inserir(m);

            JOptionPane.showMessageDialog(this, "Saída registrada com sucesso!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Informe números válidos para Produto e Quantidade.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao registrar saída: " + ex.getMessage());
        }
    }
}