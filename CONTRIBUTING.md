# Contribuindo para o Sistema de Controle de Estoque - UNISUL

Abaixo estão algumas diretrizes importantes para garantir uma colaboração eficiente e organizada.

## 📋 Como Contribuir

### Reportando Problemas ou Bugs

- Utilize a aba **Issues** do GitHub para relatar comportamentos inesperados ou erros.
- Explique claramente o problema e, se possível, inclua:
    - Passos para reproduzir o erro
    - Capturas de tela ou mensagens de erro
    - Trechos de código relacionados

### Sugerindo Melhorias

- Antes de implementar, abra uma **Issue** para descrever a ideia.
- Justifique a importância da sugestão e indique como ela contribui para o objetivo do projeto.

## ⚙️ Configuração do Projeto

### Pré-requisitos

- Java JDK 11 ou superior
- IDE Java (recomendado: IntelliJ IDEA ou NetBeans)
- MySQL Server ativo

### Instruções de Instalação e Execução

1. **Clone este repositório:**
   ```bash
   git clone https://github.com/PedroDella/Sistema-Controle-Estoque
   ```

2. **Importe o projeto em sua IDE Java**

3. **Configure o banco de dados:**
    - Certifique-se de que o MySQL Server esteja instalado e em execução
    - Execute o script SQL localizado em `db/estoque.sql`
    - Ajuste as credenciais de conexão em `src/dao/ConnectionFactory.java`

4. **Compile e execute o projeto**
    - A classe principal é `src/view/App.java`

## 📝 Lista de Tarefas (ToDo)

O projeto possui uma estrutura base sólida, mas ainda necessita da implementação de várias funcionalidades. Abaixo estão as principais tarefas pendentes:

### Interfaces Gráficas
- [ ] Implementar tela de cadastro de produtos (`view/produto/TelaCadastroProduto.java`)
- [ ] Implementar tela de consulta de produtos (`view/produto/TelaConsultaProduto.java`)
- [ ] Implementar tela de reajuste de preços (`view/produto/TelaReajusteProduto.java`)
- [ ] Implementar tela de cadastro de categorias (`view/categoria/TelaCadastroCategoria.java`)
- [ ] Implementar tela de consulta de categorias (`view/categoria/TelaConsultaCategoria.java`)
- [ ] Implementar tela de entrada de estoque (`view/movimentacao/TelaEntradaEstoque.java`)
- [ ] Implementar tela de saída de estoque (`view/movimentacao/TelaSaidaEstoque.java`)

### Relatórios
- [ ] Implementar tela de relatório de preços (`view/relatorio/TelaRelatorioPrecos.java`)
- [ ] Implementar tela de relatório de balanço (`view/relatorio/TelaRelatorioBalanco.java`)
- [ ] Implementar tela de relatório de produtos abaixo do mínimo (`view/relatorio/TelaRelatorioAbaixoMinimo.java`)
- [ ] Implementar tela de relatório de produtos acima do máximo (`view/relatorio/TelaRelatorioAcimaMaximo.java`)
- [ ] Implementar tela de relatório por categoria (`view/relatorio/TelaRelatorioPorCategoria.java`)

## 🚀 Enviando Pull Requests

- Crie um branch a partir da `main` com um nome descritivo:
  ```bash
  git checkout -b feature/descricao-funcionalidade
  ```

- Siga as convenções de codificação Java e o padrão do projeto.
- Faça commits claros e frequentes com mensagens explicativas:
  ```
  feat: adicionar funcionalidade de relatório financeiro
  fix: corrigir bug na atualização de estoque
  ```

- Atualize a documentação e arquivos `.md` quando necessário.
- Ao concluir, abra um Pull Request detalhado e associe à Issue correspondente, se houver.

## 🧑‍💻 Convenções de Código

- Padrão DAO para persistência
- Nome de classes em **CamelCase**
- Nome de pacotes em **letras minúsculas**
- Comentários explicativos sempre que necessário
- Nomenclatura em português para classes, métodos e variáveis
- Seguir princípios de Clean Code:
    - Métodos pequenos e com responsabilidade única
    - Nomes significativos
    - Evitar duplicação de código
    - Tratamento adequado de exceções

## 📜 Código de Conduta

Este projeto segue diretrizes de respeito, colaboração e ética acadêmica. Espera-se que todas as interações sejam cordiais e construtivas.
