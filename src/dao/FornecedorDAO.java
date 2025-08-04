package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Fornecedor;

/**
 * Classe de acesso a dados para a entidade Fornecedor.
 * Implementa operações de CRUD (Create, Read, Update, Delete).
 */
public class FornecedorDAO {

    /**
     * Insere um novo fornecedor no banco de dados.
     *
     * @param fornecedor O fornecedor a ser inserido
     * @return O ID gerado para o fornecedor inserido ou -1 em caso de erro
     */
    public int inserir(Fornecedor fornecedor) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idGerado = -1;

        try {
            conn = ConnectionFactory.getConnection();

            String sql = "INSERT INTO fornecedor (razao_social, nome_fantasia, cnpj, telefone, email, endereco, data_cadastro) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, fornecedor.getRazaoSocial());
            stmt.setString(2, fornecedor.getNomeFantasia());
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setString(4, fornecedor.getTelefone());
            stmt.setString(5, fornecedor.getEmail());
            stmt.setString(6, fornecedor.getEndereco());

            // Para data_cadastro, usa Timestamp do LocalDateTime
            LocalDateTime dt = fornecedor.getDataCadastro();
            if (dt != null) {
                stmt.setTimestamp(7, Timestamp.valueOf(dt));
            } else {
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                    fornecedor.setId(idGerado);
                }
            }
            return idGerado;

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir fornecedor: " + ex.getMessage());
            return -1;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar ResultSet: " + ex.getMessage()); }
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar Statement: " + ex.getMessage()); }
            ConnectionFactory.closeConnection(conn);
        }
    }

    /**
     * Atualiza um fornecedor existente no banco de dados.
     *
     * @param fornecedor O fornecedor a ser atualizado
     * @return true se a atualização foi bem-sucedida, false caso contrário
     */
    public boolean atualizar(Fornecedor fornecedor) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();

            String sql = "UPDATE fornecedor SET razao_social = ?, nome_fantasia = ?, cnpj = ?, telefone = ?, email = ?, endereco = ?, data_cadastro = ? WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fornecedor.getRazaoSocial());
            stmt.setString(2, fornecedor.getNomeFantasia());
            stmt.setString(3, fornecedor.getCnpj());
            stmt.setString(4, fornecedor.getTelefone());
            stmt.setString(5, fornecedor.getEmail());
            stmt.setString(6, fornecedor.getEndereco());

            LocalDateTime dt = fornecedor.getDataCadastro();
            if (dt != null) {
                stmt.setTimestamp(7, Timestamp.valueOf(dt));
            } else {
                stmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            }

            stmt.setInt(8, fornecedor.getId());

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            System.err.println("Erro ao atualizar fornecedor: " + ex.getMessage());
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar Statement: " + ex.getMessage()); }
            ConnectionFactory.closeConnection(conn);
        }
    }

    /**
     * Exclui um fornecedor do banco de dados.
     *
     * @param id O ID do fornecedor a ser excluído
     * @return true se a exclusão foi bem-sucedida, false caso contrário
     */
    public boolean excluir(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();

            String sql = "DELETE FROM fornecedor WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            return linhasAfetadas > 0;

        } catch (SQLException ex) {
            System.err.println("Erro ao excluir fornecedor: " + ex.getMessage());
            return false;
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar Statement: " + ex.getMessage()); }
            ConnectionFactory.closeConnection(conn);
        }
    }

    /**
     * Busca um fornecedor pelo ID.
     *
     * @param id O ID do fornecedor
     * @return O fornecedor encontrado ou null se não existir
     */
    public Fornecedor buscarPorId(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Fornecedor fornecedor = null;

        try {
            conn = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM fornecedor WHERE id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                fornecedor = extrairFornecedorDoResultSet(rs);
            }

            return fornecedor;

        } catch (SQLException ex) {
            System.err.println("Erro ao buscar fornecedor por ID: " + ex.getMessage());
            return null;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar ResultSet: " + ex.getMessage()); }
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar Statement: " + ex.getMessage()); }
            ConnectionFactory.closeConnection(conn);
        }
    }

    /**
     * Lista todos os fornecedores cadastrados.
     *
     * @return Lista de fornecedores
     */
    public List<Fornecedor> listarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fornecedor> fornecedores = new ArrayList<>();

        try {
            conn = ConnectionFactory.getConnection();

            String sql = "SELECT * FROM fornecedor ORDER BY razao_social";

            stmt = conn.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Fornecedor fornecedor = extrairFornecedorDoResultSet(rs);
                fornecedores.add(fornecedor);
            }

            return fornecedores;

        } catch (SQLException ex) {
            System.err.println("Erro ao listar fornecedores: " + ex.getMessage());
            return fornecedores;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar ResultSet: " + ex.getMessage()); }
            try { if (stmt != null) stmt.close(); } catch (SQLException ex) { System.err.println("Erro ao fechar Statement: " + ex.getMessage()); }
            ConnectionFactory.closeConnection(conn);
        }
    }

    /**
     * Método auxiliar para extrair um objeto Fornecedor a partir do ResultSet.
     *
     * @param rs ResultSet da consulta
     * @return Objeto Fornecedor preenchido
     * @throws SQLException
     */
    private Fornecedor extrairFornecedorDoResultSet(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(rs.getInt("id"));
        fornecedor.setRazaoSocial(rs.getString("razao_social"));
        fornecedor.setNomeFantasia(rs.getString("nome_fantasia"));
        fornecedor.setCnpj(rs.getString("cnpj"));
        fornecedor.setTelefone(rs.getString("telefone"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setEndereco(rs.getString("endereco"));

        Timestamp ts = rs.getTimestamp("data_cadastro");
        if (ts != null) {
            fornecedor.setDataCadastro(ts.toLocalDateTime());
        } else {
            fornecedor.setDataCadastro(null);
        }

        return fornecedor;
    }
}