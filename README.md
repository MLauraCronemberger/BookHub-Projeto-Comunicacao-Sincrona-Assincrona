# 📚 BookHub - Comunicação Síncrona e Assíncrona

> Projeto desenvolvido para a disciplina **CCOM0003 – Arquitetura Orientada a Serviços**

Este projeto explora a comunicação entre microsserviços utilizando diferentes linguagens e padrões síncronos (gRPC, REST) e assíncronos (RabbitMQ). O sistema simula um hub de livros que reúne dados de obras, autores, notícias e adaptações literárias em uma arquitetura distribuída.

---

## 🎯 1. Visão Geral

### 1.1. Propósito

Compreender o funcionamento da comunicação entre microsserviços, explorando modelos síncronos e assíncronos, observando como serviços independentes trocam informações.

### 1.2. Contexto

Projeto acadêmico desenvolvido para fins de estudo e experimentação com arquiteturas distribuídas.

### 1.3. Requisitos da Disciplina

A proposta era criar um álbum de figurinhas distribuído, onde:

- O serviço **Main** reúne dados de outros serviços
- Serviços **A**, **B** e **C** formam a parte síncrona
- **A** e **B** devem ser implementados em linguagens diferentes
- Pelo menos **3 serviços assíncronos** conectados via RabbitMQ
- O **Main** unifica todos os dados e serve a API final

---

## 🏗️ 2. Arquitetura Geral

### 2.1. Microsserviços

**Total:** 7 microsserviços

### 2.2. Descrição dos Serviços

| Serviço | Linguagem/Framework | Tipo | Função |
|---------|-------------------|------|--------|
| **A** | Python | Síncrono (gRPC) | Retorna lista de livros e detalhes de obras (dados mockados) |
| **B** | Node.js | Síncrono (gRPC) | Retorna informações sobre autores (dados mockados) |
| **C** | Python | Ponte (gRPC + REST) | Consome A e B por gRPC e expõe dados ao Main via REST |
| **D** | Java/Spring Boot | Assíncrono (RabbitMQ) | Armazena e fornece notícias de adaptações literárias |
| **E** | Java/Spring Boot | Assíncrono (RabbitMQ) | Armazena e fornece notícias dos autores |
| **F** | Java/Spring Boot | Assíncrono (RabbitMQ) | Contabiliza pesquisas sobre livros |
| **Main** | Java/Spring Boot | Central | Consolida dados síncronos e assíncronos, fornece API ao front |

### 2.3. Escolha das Linguagens

- **Python e Node.js** → Simplicidade para implementação de gRPC
- **Spring Boot** → Integração robusta com RabbitMQ e familiaridade com o ecossistema Java
- **Main em Spring** → Melhor integração com os demais serviços

### 2.4. Fluxo de Dados

```
Front-end
    ↓
  Main (Spring)
    ↓ REST
Service C (Python)
    ↓ gRPC
Service A ← → Service B
(Python)     (Node.js)

Main → RabbitMQ → Service D, E, F
                  (Spring Boot)
```

1. Front consulta o **Main**
2. Main chama o **Service C** para dados síncronos
3. **Service C** conversa via gRPC com **A** e **B**
4. Eventos assíncronos são publicados no RabbitMQ
5. **Services D, E, F** consomem e processam eventos
6. Front atualiza dados ao recarregar a página

---

## 🔄 3. Comunicação entre Serviços

### 3.1. gRPC - Service A (Livros)

**Estrutura de dados detalhados:**

```json
{
  "dados_livro": {
    "ano_lancamento": 1868,
    "genero": "Romance / Literatura clássica",
    "idioma_original": "Inglês",
    "sinopse": "A história acompanha as irmãs March...",
    "titulo": "Mulherzinhas"
  }
}
```

**Método "listar todos":** retorna apenas `id` e `titulo`

### 3.2. gRPC - Service B (Autores)

```json
{
  "dados_autor": {
    "idade_autor": 55,
    "nacionalidade": "Americana",
    "nome_autor": "Louisa May Alcott",
    "obras_conhecidas": [
      "Mulherzinhas",
      "Homens Jovens",
      "Uma Garota Antiga"
    ]
  }
}
```

### 3.3. Service C - Combinação de Dados

**Processo:**
1. Busca detalhes do livro no **Service A**
2. Busca detalhes do autor no **Service B**
3. Combina ambos e retorna ao Main

**Resposta combinada:**

```json
{
  "livro_id": 2,
  "dados_autor": { ... },
  "dados_livro": { ... }
}
```

### 3.4. REST - Service C → Main

**Endpoints expostos:**

- `GET /all-livros` - Lista todos os livros
- `GET /infos-livro/{livro_id}` - Detalhes de um livro específico

### 3.5. REST - Main → Front-end

**Endpoints síncronos:**
- `GET /sync/all-livros`
- `GET /sync/infos-livro/{livro_id}`

**Endpoints assíncronos:**
- `POST /async/nova-adaptacao`
- `GET /async/todas-adaptacoes-registradas`
- `POST /async/nova-noticia-autor`
- `GET /async/todas-noticias-registradas`
- `GET /async/contador`

### 3.6. RabbitMQ - Configuração

**Publicador:** Apenas o **Main**

**Consumidores:** Services D, E, F

#### Configuração por Serviço

**Service D (Adaptações):**
```properties
queue.name.serviceD=adaptacoes.livros
exchange.name.serviceD=adaptacoes.exchange
routing.key.serviceD=adaptacoes.livros.key
```

**Service E (Notícias de Autores):**
```properties
queue.name.serviceE=noticias.autores
exchange.name.serviceE=noticias.exchange
routing.key.serviceE=noticias.autores.key
```

**Service F (Contador):**
```properties
queue.name.serviceF=counter.pesquisas
exchange.name.serviceF=counter.exchange
routing.key.serviceF=counter.key
```

### 3.7. Eventos Assíncronos

- 📰 Notícias sobre autores
- 🎬 Notícias de adaptações literárias
- 📊 Contador de pesquisas de livros

---

## 🛠️ 4. Tecnologias Utilizadas

| Tecnologia | Versão |
|------------|--------|
| Python | 3.13.3 |
| Node.js | 22.15.0 |
| Java | 17.0.15 |
| Spring Boot | 4.0.0 |
| RabbitMQ | 3.13.7 |
| gRPC | 1.14.1 |

---

## 🚀 5. Como Executar

### 5.1. Iniciar Serviços Síncronos

```bash
# Service A (Python)
python run_service_A.py

# Service B (Node.js)
node server_B.js

# Service C (Python)
python client_C.py
```

### 5.2. Iniciar RabbitMQ (Docker)

**Pré-requisito:** Docker Desktop instalado

```bash
docker run -d --hostname my-rabbit --name rabbitmq_service \
  -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

**Interface de gerenciamento:** `http://localhost:15672`  
**Credenciais padrão:** `guest` / `guest`

### 5.3. Iniciar Serviços Assíncronos (Spring Boot)

**Ordem recomendada:**
1. Service D
2. Service E
3. Service F

### 5.4. Iniciar o Main (Spring Boot)

O Main expõe todas as APIs do BookHub.

### 5.5. Iniciar o Front-end

```bash
npm i
npm run dev
```

*Repositório do front-end separado:* [Front-BookHub](https://github.com/MLauraCronemberger/Front-Bookhub)


---

## ⚙️ 6. Funcionalidades do Sistema

### 6.1. Funcionalidades Síncronas

- ✅ Listagem de livros
- ✅ Busca detalhada de livro por ID
- ✅ Informações do autor vinculadas ao livro

### 6.2. Funcionalidades Assíncronas

- ✅ Cadastro e listagem de notícias sobre autores
- ✅ Cadastro e listagem de adaptações literárias
- ✅ Contador de pesquisas realizadas

### 6.3. Dashboard do Main

O serviço Main centraliza e disponibiliza:

- 📚 Lista completa de livros
- 📖 Detalhes de livro + informações do autor
- 📰 Notícias e adaptações cadastradas
- 📊 Estatísticas de pesquisas

---

## 👥 7. Créditos

**Aluna:** Maria Laura Rangel Urbano Cronemberger  
**Disciplina:** CCOM0003 — Arquitetura Orientada a Serviços  
**Professor:** Carlos de Salles Soares Neto  
**Instituição:** UFMA — Universidade Federal do Maranhão  
**Semestre:** 2025.2

---

<div align="center">

**Desenvolvido para fins acadêmicos**

</div>
