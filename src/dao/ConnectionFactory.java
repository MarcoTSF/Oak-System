package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por criar e gerenciar conexões com o banco de dados.
 * Implementa o padrão Factory para centralizar a criação de conexões.
 */
public class ConnectionFactory {
    
    // Constantes para conexão com o banco de dados
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private static final String USER = "root";
    private static final String PASS = "";
    
    /**
     * Obtém uma conexão com o banco de dados.
     * 
     * @return Uma conexão com o banco de dados
     * @throws SQLException Se ocorrer um erro ao conectar ao banco de dados
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Carrega o driver JDBC
            Class.forName(DRIVER);
            
            // Retorna uma conexão com o banco de dados
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Driver do banco de dados não encontrado", ex);
        } catch (SQLException ex) {
            throw new SQLException("Erro ao conectar ao banco de dados", ex);
        }
    }
    
    /**
     * Fecha uma conexão com o banco de dados.
     * 
     * @param conn A conexão a ser fechada
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao fechar conexão: " + ex.getMessage());
        }
    }
    
    /**
     * Fecha uma conexão com o banco de dados e exibe uma mensagem de erro.
     * 
     * @param conn A conexão a ser fechada
     * @param mensagem A mensagem de erro a ser exibida
     */
    public static void closeConnection(Connection conn, String mensagem) {
        System.err.println(mensagem);
        closeConnection(conn);
    }
}
