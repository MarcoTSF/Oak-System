-- Estrutura do banco de dados
CREATE DATABASE IF NOT EXISTS controle_estoque;
USE controle_estoque;

-- Limpeza das tabelas (ordem importa!)
-- DROP TABLE IF EXISTS movimentacao;
-- DROP TABLE IF EXISTS produto;
-- DROP TABLE IF EXISTS categoria;

-- Tabela de Categorias
CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tamanho ENUM('Pequeno', 'Médio', 'Grande') CHARACTER SET utf8mb4 NOT NULL,
    embalagem ENUM('Lata', 'Vidro', 'Plástico') CHARACTER SET utf8mb4 NOT NULL
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    unidade VARCHAR(20) NOT NULL,
    quantidade_estoque INT NOT NULL DEFAULT 0,
    quantidade_minima INT NOT NULL,
    quantidade_maxima INT NOT NULL,
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);

-- Tabela de Movimentações
CREATE TABLE IF NOT EXISTS movimentacao (
    id INT AUTO_INCREMENT PRIMARY KEY,
    produto_id INT NOT NULL,
    tipo ENUM('Entrada', 'Saída') NOT NULL,
    quantidade INT NOT NULL,
    data_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    observacao TEXT,
    FOREIGN KEY (produto_id) REFERENCES produto(id)
);

-- Inserção de dados de exemplo para categorias
INSERT INTO categoria (nome, tamanho, embalagem) VALUES 
('Limpeza', 'Médio', 'Plástico'),
('Alimentos', 'Pequeno', 'Lata'),
('Bebidas', 'Grande', 'Vidro'),
('Higiene', 'Pequeno', 'Plástico'),
('Cereais', 'Médio', 'Plástico');

-- Inserção de dados de exemplo para produtos
INSERT INTO produto (nome, preco_unitario, unidade, quantidade_estoque, quantidade_minima, quantidade_maxima, categoria_id) VALUES 
('Detergente', 3.50, 'Unidade', 50, 20, 100, 1),
('Arroz', 22.90, 'Pacote', 30, 15, 60, 5),
('Feijão', 8.75, 'Pacote', 25, 10, 50, 5),
('Refrigerante', 7.50, 'Garrafa', 40, 20, 80, 3),
('Sabonete', 2.30, 'Unidade', 60, 30, 120, 4),
('Milho em Conserva', 3.20, 'Lata', 35, 15, 70, 2);
