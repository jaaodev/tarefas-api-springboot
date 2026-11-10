# API de Tarefas — Spring Boot

Projeto de estudos em **Java 21 e Spring Boot** para praticar o desenvolvimento de uma API REST, organização em camadas, DTOs, validação de dados e tratamento de exceções.

> **Status:** em desenvolvimento. As tarefas ficam armazenadas em memória (`ArrayList`); ao reiniciar a aplicação, os dados cadastrados são perdidos e as duas tarefas de exemplo são recriadas.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC
- Jakarta Bean Validation (`@Valid` e `@NotBlank`)
- Maven e Maven Wrapper
- IntelliJ IDEA e HTTP Client para testes manuais

## Funcionalidades

- Cadastrar tarefas com ID gerado automaticamente pelo servidor.
- Listar as tarefas cadastradas.
- Consultar uma tarefa pelo ID.
- Marcar uma tarefa como concluída.
- Excluir uma tarefa.
- Validar descrições obrigatórias e retornar respostas HTTP apropriadas.
- Centralizar o tratamento de exceções com `@RestControllerAdvice`.

## Endpoints

| Método | Rota | Descrição |
| --- | --- | --- |
| `GET` | `/tarefas` | Exibe mensagem de boas-vindas |
| `GET` | `/tarefas/lista` | Lista todas as tarefas |
| `GET` | `/tarefas/{id}` | Busca uma tarefa pelo ID |
| `POST` | `/tarefas` | Cadastra uma tarefa |
| `PATCH` | `/tarefas/{id}/concluir` | Marca uma tarefa como concluída |
| `DELETE` | `/tarefas/{id}` | Exclui uma tarefa |

Existe também uma rota de demonstração `GET /tarefa`, que retorna um objeto de exemplo fixo.

### Exemplo: cadastrar uma tarefa

**Requisição**

```http
POST http://localhost:8080/tarefas
Content-Type: application/json

{
  "descricao": "Estudar Spring Boot",
  "concluida": false
}
```

**Resposta esperada:** `201 Created`, com uma mensagem de confirmação. O ID é gerado pelo servidor, portanto não precisa ser informado no JSON.

**Consultar tarefas**

```http
GET http://localhost:8080/tarefas/lista
```

Exemplo de item retornado:

```json
{
  "id": 3,
  "descricao": "Estudar Spring Boot",
  "concluida": false
}
```

## Validação e respostas HTTP

A entrada de cadastro usa `CadastroTarefaDTO` e a anotação `@NotBlank` para exigir uma descrição preenchida. A aplicação utiliza `GlobalExceptionHandler` para centralizar os tratamentos de erro.

| Situação | Código HTTP |
| --- | --- |
| Cadastro realizado | `201 Created` |
| Consulta, conclusão ou exclusão realizada | `200 OK` |
| Descrição inválida | `400 Bad Request` |
| Tarefa não encontrada | `404 Not Found` |
| Tentativa de concluir uma tarefa já concluída | `409 Conflict` |

## Estrutura do projeto

```text
tarefas-api/
├── pom.xml
├── mvnw
├── mvnw.cmd
├── .mvn/wrapper/
└── src/
    ├── main/
    │   ├── java/br/com/estudos/tarefas_api/
    │   │   ├── TarefasApiApplication.java
    │   │   ├── TarefaController.java
    │   │   ├── TarefaService.java
    │   │   ├── Tarefa.java
    │   │   ├── CadastroTarefaDTO.java
    │   │   ├── GlobalExceptionHandler.java
    │   │   └── TarefaDuplicadaException.java
    │   └── resources/application.properties
    └── test/java/br/com/estudos/tarefas_api/
        └── TarefasApiApplicationTests.java

requisicoes.http
```

**Responsabilidades principais:**

- **Controller:** expõe os endpoints HTTP e encaminha operações ao Service.
- **Service:** gerencia a lista de tarefas e aplica regras de negócio.
- **Model (`Tarefa`):** representa as informações da tarefa.
- **DTO (`CadastroTarefaDTO`):** recebe apenas os campos necessários ao cadastro.
- **Exception Handler:** converte exceções em respostas HTTP.

## Como executar

### Pré-requisitos

- JDK 21
- Git (para clonar o repositório)
- Conexão com a internet na primeira execução, para baixar as dependências Maven

1. Clone o projeto:

   ```bash
   git clone https://github.com/jaaodev/tarefas-api-springboot.git
   cd tarefas-api-springboot/tarefas-api
   ```

2. Execute a aplicação pelo Maven Wrapper.

   **Windows (PowerShell ou Prompt de Comando):**

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   **Linux/macOS:**

   ```bash
   ./mvnw spring-boot:run
   ```

3. A API ficará disponível em `http://localhost:8080`, salvo se a porta for alterada.

4. Faça requisições com o IntelliJ HTTP Client, Postman, curl ou outra ferramenta HTTP. O repositório inclui um arquivo `requisicoes.http` com exemplos.

Não é necessário configurar PostgreSQL nesta versão.

## Testes

O projeto inclui o teste de contexto padrão gerado pelo Spring Initializr (`TarefasApiApplicationTests`).

Para executar os testes:

**Windows:**

```powershell
.\mvnw.cmd test
```

**Linux/macOS:**

```bash
./mvnw test
```

> A execução do conjunto de testes via Maven ainda não foi verificada nesta revisão do repositório. Testes adicionais de regras de negócio e endpoints fazem parte das próximas etapas de estudo.

## Próximas melhorias

- Ampliar os testes automatizados com JUnit e ferramentas de teste do Spring.
- Organizar as rotas seguindo convenções REST.
- Criar operações de atualização de dados além da conclusão.
- Integrar PostgreSQL com Spring Data JPA, substituindo o armazenamento em memória.
- Aprimorar respostas de erro e documentação da API.

## Autor

Desenvolvido por [João Rocha (@jaaodev)](https://github.com/jaaodev) como projeto prático de aprendizado em Java Backend.
