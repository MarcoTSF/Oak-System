package view.categoria;

import dao.CategoriaDAO;
import model.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultarCategoriasView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    public ConsultarCategoriasView() {
        setTitle("Consultar Categorias");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("Tamanho");
        modelo.addColumn("Embalagem");

        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        carregarDados();
    }

    private void carregarDados() {
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.listarTodos();

        modelo.setRowCount(0);  // Limpa linhas antigas
        for (Categoria cat : categorias) {
            modelo.addRow(new Object[]{
                    cat.getId(),
                    cat.getNome(),
                    cat.getTamanho(),
                    cat.getEmbalagem()
            });
        }
    }
}