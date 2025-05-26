package view.movimentacoes;

import dao.MovimentacaoDAO;
import dao.ProdutoDAO;
import model.Movimentacao;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

public class EntradaEstoqueView extends JFrame {
    private JTextField campoProdutoId;
    private JTextField campoQuantidade;
    private JTextField campoData;
    private JTextField campoHora;
    private JTextArea campoObservacao;
    private JButton botaoSalvar, botaoCancelar;

    public EntradaEstoqueView() {
        setTitle("Entrada de Estoque");
        setSize(450, 350);
        setLocationRelativeTo(null); // Centraliza a janela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        painelCampos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        painelCampos.add(new JLabel("ID do Produto:"));
        campoProdutoId = new JTextField();
        painelCampos.add(campoProdutoId);

        painelCampos.add(new JLabel("Quantidade:"));
        campoQuantidade = new JTextField();
        painelCampos.add(campoQuantidade);

        painelCampos.add(new JLabel("Data (YYYY-MM-DD):"));
        campoData = new JTextField();
        painelCampos.add(campoData);

        painelCampos.add(new JLabel("Hora (HH:mm):"));
        campoHora = new JTextField();
        painelCampos.add(campoHora);

        painelCampos.add(new JLabel("Observação:"));
        campoObservacao = new JTextArea(3, 20);
        campoObservacao.setLineWrap(true);
        campoObservacao.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(campoObservacao);
        painelCampos.add(scroll);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");
        painelBotoes.add(botaoSalvar);
        painelBotoes.add(botaoCancelar);

        add(painelCampos, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int produtoId = Integer.parseInt(campoProdutoId.getText().trim());
                    int quantidade = Integer.parseInt(campoQuantidade.getText().trim());
                    String dataStr = campoData.getText().trim();
                    String horaStr = campoHora.getText().trim();
                    String observacao = campoObservacao.getText().trim();

                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    Produto produto = produtoDAO.consultar(produtoId);
                    if (produto == null) {
                        JOptionPane.showMessageDialog(null, "Produto não encontrado!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    LocalDateTime dataHora;
                    if (horaStr.isEmpty()) {
                        dataHora = LocalDateTime.parse(dataStr + "T00:00:00");
                    } else {
                        dataHora = LocalDateTime.parse(dataStr + "T" + horaStr + ":00");
                    }

                    Movimentacao m = new Movimentacao();
                    m.setProduto(produto);
                    m.setQuantidade(quantidade);
                    m.setTipo("Entrada");
                    m.setDataHora(dataHora);
                    m.setObservacao(observacao);

                    MovimentacaoDAO dao = new MovimentacaoDAO();
                    dao.inserir(m);

                    JOptionPane.showMessageDialog(null, "Entrada registrada com sucesso!");
                    dispose();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Informe números válidos para Produto e Quantidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao registrar entrada: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoCancelar.addActionListener(e -> dispose());
    }
}