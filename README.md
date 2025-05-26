# Sistema de Controle de Estoque 📦

## 📝 Descrição do Projeto

Este sistema foi desenvolvido com base em um problema contextualizado do cotidiano: o gerenciamento de estoque de uma empresa comercial. O software permite:

- Cadastro, edição, consulta e exclusão de produtos; `(CRUD)`
- Classificação por categorias (ex: Limpeza, Alimentos, Bebidas);
- Controle de movimentações de entrada e saída de estoque;
- Relatórios para gestão e tomada de decisão.

## ⚙️ Funcionalidades

- **CRUD de Produtos**
- **CRUD de Categorias**
- **Movimentação de Estoque (Entrada/Saída)**
- **Reajuste de preços em massa por percentual**
- **Geração dos relatórios:**
  - Lista de Preços
  - Balanço Físico/Financeiro
  - Produtos abaixo da quantidade mínima
  - Produtos acima da quantidade máxima
  - Quantidade de produtos por categoria

## 🛠️ Tecnologias e Ferramentas Utilizadas

- **Linguagem:** Java
- **IDE:** IntelliJ IDEA
- **Banco de Dados:** MySQL
- **Padrão de Acesso a Dados:** DAO (Data Access Object)
- **Controle de Versão:** Git + GitHub

## 🧱 Estrutura do Projeto

- `src/model`: Classes de entidade (Produto, Categoria, Movimentação)
- `src/view`: Telas e interface gráfica (Swing)
- `src/dao`: Classes de persistência e consultas ao banco
- `src/util`: Classes utilitárias (Validador, GeradorRelatorio)
- `db/estoque.sql`: Script SQL para criação do banco de dados

## 📂 Banco de Dados

Arquivo `estoque.sql` contendo:

- Criação das tabelas `produto`, `categoria`, `movimentacao`
- Relações entre produto e categoria
- Restrições e tipos adequados
- Dados de exemplo para testes

## 📊 Relatórios Gerados

- `Relatório de Preços`
- `Balanço Físico/Financeiro`
- `Produtos Abaixo do Mínimo`
- `Produtos Acima do Máximo`
- `Total por Categoria`

## 📁 Como Executar o Projeto

### Pré-requisitos

- Java JDK 11 ou superior
- IDE Java (IntelliJ IDEA recomendado)
- MySQL Server instalado e em execução

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/PedroDella/Sistemas-Controle-Estoque
   ```

2. **Configure o banco de dados:**
  - Certifique-se de que o MySQL Server esteja instalado e em execução
  - Abra o MySQL Command Line Client ou MySQL Workbench
  - Crie o banco de dados e execute o script SQL:
    ```sql
    CREATE DATABASE controle_estoque;
    USE controle_estoque;
    source caminho/para/db/estoque.sql
    ```
  - Alternativamente, no MySQL Workbench, vá em File > Open SQL Script, selecione o arquivo `db/estoque.sql` e execute-o

3. **Configure a conexão com o banco de dados:**
  - Abra o arquivo `src/dao/ConnectionFactory.java`
  - Ajuste as constantes de conexão conforme sua configuração:
    ```java
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/controle_estoque";
    private static final String USER = "seu_usuario"; // Altere para seu usuário MySQL
    private static final String PASS = "sua_senha";   // Altere para sua senha MySQL
    ```

4. **Importe o projeto na sua IDE:**
  - No IntelliJ IDEA: File > Open > Selecione a pasta do projeto
  - Adicione o driver JDBC do MySQL ao projeto:
    - File > Project Structure > Libraries
    - Clique no "+" e selecione "From Maven"
    - Pesquise por "mysql-connector-java" e adicione a versão mais recente

5. **Execute a aplicação:**
  - Navegue até a classe `src/view/App.java`
  - Clique com o botão direito e selecione "Run 'App.main()'"
  - A interface gráfica do sistema será iniciada
