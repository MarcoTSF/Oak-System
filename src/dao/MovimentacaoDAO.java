package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.Movimentacao;
import model.Produto;
import model.Categoria;

/**
 * Classe de acesso a dados para a entidade Movimentacao.
 * Implementa operações de CRUD (Create, Read, Update, Delete) e consultas específicas.
 */
public class MovimentacaoDAO {
    
    /**
     * Insere uma nova movimentação no banco de dados e atualiza o estoque do produto.
     * 
     * @param movimentacao A movimentação a ser inserida
     * @return O ID gerado para a movimentação inserida ou -1 em caso de erro
     */
    public int inserir(Movimentacao movimentacao) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int idGerado = -1;
        
        try {
            // Obtém conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Inicia transação
            
            // SQL para inserção
            String sql = "INSERT INTO movimentacao (produto_id, tipo, quantidade, data_hora, observacao) " +
                         "VALUES (?, ?, ?, ?, ?)";
            
            // Prepara statement para inserção
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, movimentacao.getProduto().getId());
            stmt.setString(2, movimentacao.getTipo());
            stmt.setInt(3, movimentacao.getQuantidade());
            stmt.setTimestamp(4, Timestamp.valueOf(movimentacao.getDataHora()));
            stmt.setString(5, movimentacao.getObservacao());
            
            // Executa a inserção
            int linhasAfetadas = stmt.executeUpdate();
            
            // Verifica se a inserção foi bem-sucedida
            if (linhasAfetadas > 0) {
                // Obtém o ID gerado
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    idGerado = rs.getInt(1);
                    movimentacao.setId(idGerado);
                    
                    // Atualiza o estoque do produto
                    atualizarEstoqueProduto(conn, movimentacao);
                    
                    // Confirma a transação
                    conn.commit();
                }
            } else {
                // Desfaz a transação em caso de erro
                conn.rollback();
            }
            
            return idGerado;
        } catch (SQLException ex) {
            // Desfaz a transação em caso de erro
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                System.err.println("Erro ao desfazer transação: " + e.getMessage());
            }
            
            System.err.println("Erro ao inserir movimentação: " + ex.getMessage());
            return -1;
        } finally {
            // Restaura o modo de auto-commit
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao restaurar auto-commit: " + ex.getMessage());
            }
            
            // Fecha recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    /**
     * Atualiza o estoque do produto com base na movimentação.
     * 
     * @param conn A conexão com o banco de dados
     * @param movimentacao A movimentação que afeta o estoque
     * @throws SQLException Se ocorrer um erro ao atualizar o estoque
     */
    private void atualizarEstoqueProduto(Connection conn, Movimentacao movimentacao) throws SQLException {
        PreparedStatement stmt = null;
        
        try {
            String sql;
            
            // Verifica o tipo de movimentação
            if (movimentacao.isEntrada()) {
                // Entrada: aumenta o estoque
                sql = "UPDATE produto SET quantidade_estoque = quantidade_estoque + ? WHERE id = ?";
            } else {
                // Saída: diminui o estoque
                sql = "UPDATE produto SET quantidade_estoque = quantidade_estoque - ? WHERE id = ?";
            }
            
            // Prepara statement para atualização
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movimentacao.getQuantidade());
            stmt.setInt(2, movimentacao.getProduto().getId());
            
            // Executa a atualização
            stmt.executeUpdate();
        } finally {
            // Fecha recursos
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
        }
    }
    
    /**
     * Consulta uma movimentação pelo ID.
     * 
     * @param id O ID da movimentação a ser consultada
     * @return A movimentação encontrada ou null se não existir
     */
    public Movimentacao consultar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Movimentacao movimentacao = null;
        
        try {
            // Obtém conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // SQL para consulta
            String sql = "SELECT m.id, m.produto_id, m.tipo, m.quantidade, m.data_hora, m.observacao, " +
                         "p.nome as produto_nome, p.preco_unitario, p.unidade, p.quantidade_estoque, " +
                         "p.quantidade_minima, p.quantidade_maxima, p.categoria_id, " +
                         "c.nome as categoria_nome, c.tamanho as categoria_tamanho, " +
                         "c.embalagem as categoria_embalagem " +
                         "FROM movimentacao m " +
                         "JOIN produto p ON m.produto_id = p.id " +
                         "JOIN categoria c ON p.categoria_id = c.id " +
                         "WHERE m.id = ?";
            
            // Prepara statement para consulta
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            // Executa a consulta
            rs = stmt.executeQuery();
            
            // Verifica se encontrou a movimentação
            if (rs.next()) {
                // Cria a categoria
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                categoria.setTamanho(rs.getString("categoria_tamanho"));
                categoria.setEmbalagem(rs.getString("categoria_embalagem"));
                
                // Cria o produto
                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                produto.setCategoria(categoria);
                
                // Cria a movimentação
                movimentacao = new Movimentacao();
                movimentacao.setId(rs.getInt("id"));
                movimentacao.setProduto(produto);
                movimentacao.setTipo(rs.getString("tipo"));
                movimentacao.setQuantidade(rs.getInt("quantidade"));
                movimentacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                movimentacao.setObservacao(rs.getString("observacao"));
            }
            
            return movimentacao;
        } catch (SQLException ex) {
            System.err.println("Erro ao consultar movimentação: " + ex.getMessage());
            return null;
        } finally {
            // Fecha recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    /**
     * Lista todas as movimentações cadastradas.
     * 
     * @return Uma lista com todas as movimentações
     */
    public List<Movimentacao> listarTodos() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();
        
        try {
            // Obtém conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // SQL para listar todas as movimentações
            String sql = "SELECT m.id, m.produto_id, m.tipo, m.quantidade, m.data_hora, m.observacao, " +
                         "p.nome as produto_nome, p.preco_unitario, p.unidade, p.quantidade_estoque, " +
                         "p.quantidade_minima, p.quantidade_maxima, p.categoria_id, " +
                         "c.nome as categoria_nome, c.tamanho as categoria_tamanho, " +
                         "c.embalagem as categoria_embalagem " +
                         "FROM movimentacao m " +
                         "JOIN produto p ON m.produto_id = p.id " +
                         "JOIN categoria c ON p.categoria_id = c.id " +
                         "ORDER BY m.data_hora DESC";
            
            // Prepara statement para consulta
            stmt = conn.prepareStatement(sql);
            
            // Executa a consulta
            rs = stmt.executeQuery();
            
            // Percorre os resultados
            while (rs.next()) {
                // Cria a categoria
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                categoria.setTamanho(rs.getString("categoria_tamanho"));
                categoria.setEmbalagem(rs.getString("categoria_embalagem"));
                
                // Cria o produto
                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                produto.setCategoria(categoria);
                
                // Cria a movimentação
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setId(rs.getInt("id"));
                movimentacao.setProduto(produto);
                movimentacao.setTipo(rs.getString("tipo"));
                movimentacao.setQuantidade(rs.getInt("quantidade"));
                movimentacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                movimentacao.setObservacao(rs.getString("observacao"));
                
                movimentacoes.add(movimentacao);
            }
            
            return movimentacoes;
        } catch (SQLException ex) {
            System.err.println("Erro ao listar movimentações: " + ex.getMessage());
            return movimentacoes;
        } finally {
            // Fecha recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    /**
     * Lista as movimentações de um produto específico.
     * 
     * @param produtoId O ID do produto
     * @return Uma lista com as movimentações do produto
     */
    public List<Movimentacao> listarPorProduto(int produtoId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();
        
        try {
            // Obtém conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // SQL para listar movimentações por produto
            String sql = "SELECT m.id, m.produto_id, m.tipo, m.quantidade, m.data_hora, m.observacao, " +
                         "p.nome as produto_nome, p.preco_unitario, p.unidade, p.quantidade_estoque, " +
                         "p.quantidade_minima, p.quantidade_maxima, p.categoria_id, " +
                         "c.nome as categoria_nome, c.tamanho as categoria_tamanho, " +
                         "c.embalagem as categoria_embalagem " +
                         "FROM movimentacao m " +
                         "JOIN produto p ON m.produto_id = p.id " +
                         "JOIN categoria c ON p.categoria_id = c.id " +
                         "WHERE m.produto_id = ? " +
                         "ORDER BY m.data_hora DESC";
            
            // Prepara statement para consulta
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, produtoId);
            
            // Executa a consulta
            rs = stmt.executeQuery();
            
            // Percorre os resultados
            while (rs.next()) {
                // Cria a categoria
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                categoria.setTamanho(rs.getString("categoria_tamanho"));
                categoria.setEmbalagem(rs.getString("categoria_embalagem"));
                
                // Cria o produto
                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                produto.setCategoria(categoria);
                
                // Cria a movimentação
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setId(rs.getInt("id"));
                movimentacao.setProduto(produto);
                movimentacao.setTipo(rs.getString("tipo"));
                movimentacao.setQuantidade(rs.getInt("quantidade"));
                movimentacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                movimentacao.setObservacao(rs.getString("observacao"));
                
                movimentacoes.add(movimentacao);
            }
            
            return movimentacoes;
        } catch (SQLException ex) {
            System.err.println("Erro ao listar movimentações por produto: " + ex.getMessage());
            return movimentacoes;
        } finally {
            // Fecha recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
            ConnectionFactory.closeConnection(conn);
        }
    }
    
    /**
     * Lista as movimentações por tipo (Entrada ou Saída).
     * 
     * @param tipo O tipo de movimentação ("Entrada" ou "Saída")
     * @return Uma lista com as movimentações do tipo especificado
     */
    public List<Movimentacao> listarPorTipo(String tipo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Movimentacao> movimentacoes = new ArrayList<>();
        
        try {
            // Obtém conexão com o banco de dados
            conn = ConnectionFactory.getConnection();
            
            // SQL para listar movimentações por tipo
            String sql = "SELECT m.id, m.produto_id, m.tipo, m.quantidade, m.data_hora, m.observacao, " +
                         "p.nome as produto_nome, p.preco_unitario, p.unidade, p.quantidade_estoque, " +
                         "p.quantidade_minima, p.quantidade_maxima, p.categoria_id, " +
                         "c.nome as categoria_nome, c.tamanho as categoria_tamanho, " +
                         "c.embalagem as categoria_embalagem " +
                         "FROM movimentacao m " +
                         "JOIN produto p ON m.produto_id = p.id " +
                         "JOIN categoria c ON p.categoria_id = c.id " +
                         "WHERE m.tipo = ? " +
                         "ORDER BY m.data_hora DESC";
            
            // Prepara statement para consulta
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, tipo);
            
            // Executa a consulta
            rs = stmt.executeQuery();
            
            // Percorre os resultados
            while (rs.next()) {
                // Cria a categoria
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));
                categoria.setTamanho(rs.getString("categoria_tamanho"));
                categoria.setEmbalagem(rs.getString("categoria_embalagem"));
                
                // Cria o produto
                Produto produto = new Produto();
                produto.setId(rs.getInt("produto_id"));
                produto.setNome(rs.getString("produto_nome"));
                produto.setPrecoUnitario(rs.getDouble("preco_unitario"));
                produto.setUnidade(rs.getString("unidade"));
                produto.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
                produto.setQuantidadeMinima(rs.getInt("quantidade_minima"));
                produto.setQuantidadeMaxima(rs.getInt("quantidade_maxima"));
                produto.setCategoria(categoria);
                
                // Cria a movimentação
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setId(rs.getInt("id"));
                movimentacao.setProduto(produto);
                movimentacao.setTipo(rs.getString("tipo"));
                movimentacao.setQuantidade(rs.getInt("quantidade"));
                movimentacao.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                movimentacao.setObservacao(rs.getString("observacao"));
                
                movimentacoes.add(movimentacao);
            }
            
            return movimentacoes;
        } catch (SQLException ex) {
            System.err.println("Erro ao listar movimentações por tipo: " + ex.getMessage());
            return movimentacoes;
        } finally {
            // Fecha recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar recursos: " + ex.getMessage());
            }
            ConnectionFactory.closeConnection(conn);
        }
    }
}
