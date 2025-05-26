package view.produto;

import dao.ProdutoDAO;
import model.Produto;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConsultarProdutoView extends JFrame {

    private JTextArea textArea;

    public ConsultarProdutoView(){
        setTitle("Consultar Produtos");
        setSize(400, 300);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        try {
            List<Produto> produtos = new ProdutoDAO().listarTodos();
            for (Produto p : produtos) {
                textArea.append("ID: " + p.getId() + " - " + p.getNome() + " - R$ " + p.getPrecoUnitario());
            }
        } catch (Exception ex){
            JOptionPane.showMessageDialog(ConsultarProdutoView.this, "Erro ao consultar produtos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        setVisible(true);
    }
}
