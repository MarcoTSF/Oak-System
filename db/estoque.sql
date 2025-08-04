-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS controle_estoque;
USE controle_estoque;

-- Limpeza das tabelas
--DROP TABLE IF EXISTS movimentacao;
--DROP TABLE IF EXISTS produto;
--DROP TABLE IF EXISTS categoria;
--DROP TABLE IF EXISTS fornecedor;

-- Tabela de Categorias
CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    tamanho ENUM('Pequeno', 'Médio', 'Grande') CHARACTER SET utf8mb4 NOT NULL,
    embalagem ENUM('Lata', 'Vidro', 'Plástico') CHARACTER SET utf8mb4 NOT NULL
);

-- Tabela de Fornecedores
CREATE TABLE IF NOT EXISTS fornecedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    razao_social VARCHAR(150) NOT NULL,
    nome_fantasia VARCHAR(150),
    cnpj VARCHAR(18) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    endereco VARCHAR(255),
    data_cadastro DATETIME DEFAULT CURRENT_TIMESTAMP
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
    fornecedor_id INT,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    FOREIGN KEY (fornecedor_id) REFERENCES fornecedor(id)
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

-- Inserção de dados fornecedores fictícios
INSERT INTO fornecedor (razao_social, nome_fantasia, cnpj, telefone, email, endereco) VALUES
('Indústrias de Limpeza Brasil LTDA', 'Limpabras', '12.345.678/0001-90', '(11) 1234-5678', 'contato@limpabras.com', 'Rua das Flores, 123 - São Paulo/SP'),
('Alimentos Dona Maria SA', 'Dona Maria', '98.765.432/0001-10', '(21) 9876-5432', 'vendas@donamaria.com.br', 'Av. Brasil, 456 - Rio de Janeiro/RJ'),
('Bebidas Tropicais EIRELI', 'Trop Bebidas', '45.678.912/0001-22', '(31) 3333-2222', 'sac@tropbebidas.com', 'Rua das Águas, 78 - Belo Horizonte/MG');

-- Inserção de dados de exemplo para produtos
INSERT INTO produto (nome, preco_unitario, unidade, quantidade_estoque, quantidade_minima, quantidade_maxima, categoria_id, fornecedor_id) VALUES
('Detergente', 4.36, 'Unidade', 30, 20, 100, 1, 1),
('Arroz', 27.71, 'Pacote', 70, 15, 60, 5, 2),
('Feijão', 10.59, 'Pacote', 25, 10, 50, 5, 2),
('Refrigerante', 9.08, 'Garrafa', 40, 20, 80, 3, 3),
('Sabonete', 2.78, 'Unidade', 60, 30, 120, 4, 1),
('Milho em Conserva', 3.20, 'Lata', 35, 15, 70, 2, 2);