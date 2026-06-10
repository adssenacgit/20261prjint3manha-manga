# Manga API

Projeto backend em **Java 21** com **Spring Boot**, **Spring Data JPA**, **MySQL** e **Springdoc Swagger/OpenAPI**.

## Regras de status

O projeto usa apagado lógico em todas as tabelas:

| Valor | Significado |
|---:|---|
| -1 | apagado logicamente |
| 0 | inativo |
| 1 | ativo |

Por padrão, os endpoints `GET /api/...` retornam registros com status diferente de `-1`, ou seja, ativos e inativos. Os endpoints `GET /api/.../ativos` retornam somente status `1`.

## Tecnologias

- Java 21
- Spring Boot 3.5.0
- Spring Web
- Spring Data JPA
- Bean Validation
- Springdoc OpenAPI UI
- MySQL Connector/J 8.4.0

## Configuração do banco

Edite o arquivo:

```properties
src/main/resources/application.properties
```

Troque:

```properties
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

A URL já está configurada para o banco informado:

```properties
jdbc:mysql://edumysql.acesso.rj.senac.br:3306/20261_prjint3_manha_felipenobrega
```

Caso precise criar as tabelas localmente, use o script:

```text
database/schema.sql
```

## Como executar

Na pasta do projeto:

```bash
mvn spring-boot:run
```

Ou gerar o `.jar`:

```bash
mvn clean package
java -jar target/manga-api-0.0.1-SNAPSHOT.jar
```

## Swagger

Depois de subir o projeto, acesse:

```text
http://localhost:8080/swagger-ui.html
```

## Endpoints principais

### Mangás

| Método | Endpoint | Ação |
|---|---|---|
| GET | `/api/mangas` | lista mangás não apagados |
| GET | `/api/mangas/ativos` | lista mangás ativos |
| GET | `/api/mangas/{id}` | busca por ID |
| POST | `/api/mangas` | cria mangá |
| PUT | `/api/mangas/{id}` | atualiza mangá |
| PATCH | `/api/mangas/{id}/status` | altera status |
| DELETE | `/api/mangas/{id}` | apagado lógico |

### Capítulos

| Método | Endpoint | Ação |
|---|---|---|
| GET | `/api/capitulos` | lista capítulos não apagados |
| GET | `/api/capitulos/ativos` | lista capítulos ativos |
| GET | `/api/capitulos/manga/{mangaId}` | lista capítulos de um mangá |
| GET | `/api/capitulos/{id}` | busca por ID |
| POST | `/api/capitulos` | cria capítulo |
| PUT | `/api/capitulos/{id}` | atualiza capítulo |
| PATCH | `/api/capitulos/{id}/status` | altera status |
| DELETE | `/api/capitulos/{id}` | apagado lógico |

### Páginas

| Método | Endpoint | Ação |
|---|---|---|
| GET | `/api/paginas` | lista páginas não apagadas |
| GET | `/api/paginas/ativos` | lista páginas ativas |
| GET | `/api/paginas/capitulo/{capituloId}` | lista páginas de um capítulo |
| GET | `/api/paginas/{id}` | busca por ID |
| POST | `/api/paginas` | cria página |
| PUT | `/api/paginas/{id}` | atualiza página |
| PATCH | `/api/paginas/{id}/status` | altera status |
| DELETE | `/api/paginas/{id}` | apagado lógico |

### Usuários

| Método | Endpoint | Ação |
|---|---|---|
| GET | `/api/usuarios` | lista usuários não apagados |
| GET | `/api/usuarios/ativos` | lista usuários ativos |
| GET | `/api/usuarios/{id}` | busca por ID |
| POST | `/api/usuarios` | cria usuário |
| PUT | `/api/usuarios/{id}` | atualiza usuário |
| PATCH | `/api/usuarios/{id}/status` | altera status |
| DELETE | `/api/usuarios/{id}` | apagado lógico |

### Favoritos

| Método | Endpoint | Ação |
|---|---|---|
| GET | `/api/favoritos` | lista favoritos não apagados |
| GET | `/api/favoritos/ativos` | lista favoritos ativos |
| GET | `/api/favoritos/usuario/{usuarioId}` | lista favoritos de um usuário |
| GET | `/api/favoritos/manga/{mangaId}` | lista favoritos de um mangá |
| GET | `/api/favoritos/{usuarioId}/{mangaId}` | busca pela chave composta |
| POST | `/api/favoritos` | cria favorito |
| PUT | `/api/favoritos/{usuarioId}/{mangaId}` | atualiza favorito |
| PATCH | `/api/favoritos/{usuarioId}/{mangaId}/status` | altera status |
| DELETE | `/api/favoritos/{usuarioId}/{mangaId}` | apagado lógico |

## Observações importantes

- O projeto não usa Lombok, para facilitar leitura e manutenção pelos alunos.
- A senha do usuário está sendo gravada conforme a estrutura original do banco. Em projeto real, o ideal é usar hash de senha com Spring Security/BCrypt.
- O `DELETE` não remove fisicamente o registro. Ele altera o campo `*_status` para `-1`.
- Os relacionamentos foram mapeados por IDs simples, evitando ciclos de serialização JSON e facilitando o CRUD didático.
