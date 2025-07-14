package view.sobre;

import javax.swing.*;
import java.awt.*;

public class SobreView extends JDialog {

    public SobreView(JFrame owner) {
        super(owner, "Sobre o Oak System", true); // modal
        setSize(450, 280);
        setLocationRelativeTo(owner);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30)); // padding

        JLabel lblTexto = new JLabel(
                "<html>" +
                        "<div style='text-align: center; font-family: sans-serif;'>" +
                        "<h2 style='margin-bottom: 5px;'>Oak System</h2>" +
                        "<p style='font-size: 12px;'>O Oak System é um sistema de controle de estoque<br>" +
                        "desenvolvido para facilitar o cadastro, consulta e movimentação<br>" +
                        "de produtos em pequenas e médias empresas.</p>" +
                        "<p style='margin-top: 10px; font-size: 11px;'>Versão 1.0 – Jul 2025</p>" +
                        "<p style='font-size: 10px;'>&copy; 2025 Lucas Chagas / Oak System</p>" +
                        "</div>" +
                        "</html>",
                SwingConstants.CENTER
        );

        painelCentro.add(lblTexto, BorderLayout.CENTER);
        add(painelCentro, BorderLayout.CENTER);

        JPanel painelSul = new JPanel();
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setPreferredSize(new Dimension(90, 28));
        btnFechar.addActionListener(e -> dispose());
        painelSul.add(btnFechar);
        add(painelSul, BorderLayout.SOUTH);
    }
}