# Barbershop Booking API

## Sobre o Projeto

O **Barbershop Booking API** é um projeto desenvolvido como um desafio pessoal para aprimorar conhecimentos em **Spring Boot** e **APIs REST**. A ideia surgiu ao observar um problema comum: longas filas de espera em barbearias, resultando em atrasos e desconforto para clientes e profissionais.

Essa API tem como objetivo permitir o agendamento de horários de forma prévia, tornando o processo mais eficiente e organizado, tanto para clientes quanto para barbeiros.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Web**
- **Spring Data JPA**
- **Maven**
- **Lombok**
- **Swagger (Springdoc OpenAPI)**
- **JUnit 5** (para testes unitários)
- **Docker**

## Como Executar o Projeto

### Requisitos
- Java 17+
- Maven instalado

### Passos
1. Clone o repositório:
   ```sh
   git clone https://github.com/EduardoBmAguiar/barbershop_booking.git
   ```
2. Acesse o diretório do projeto:
   ```sh
   cd barbershop_booking
   ```
3. Execute o projeto usando o Maven:
   ```sh
   mvn spring-boot:run
   ```

## Como Executar o Projeto (Utilizando Docker)
### Requisitos
- Docker instalado

### Passos
1. Execute o seguinte comando para construir o container:
   ```sh
   docker build -t barbershop_booking https://github.com/EduardoBmAguiar/barbershop_booking.git
   ```
2. Execute o container:
   ```sh
   docker run -p 8080:8080 barbershop_booking
   ```

## Endpoints Principais
###### A API estará disponível em `http://localhost:8080`

### Agendamento de Horários
| Método | Endpoint | Descrição |
|---------|---------|------------|
| **POST** | `/agendamentos` | Cria um novo agendamento |
| **GET** | `/agendamentos` | Lista todos os agendamentos |
| **GET** | `/agendamentos/{id}` | Busca um agendamento por ID |
| **GET** | `/agendamentos/hoursForAgendate?chosenDay=27` | Busca os horários disponíveis no dia escolhido |
| **PUT** | `/agendates/{id}` | Atualiza um agendamento por id |

#### Exemplos: 
##### POST (requisição): 
````json 
{
    "chosenDate": "2025-03-27T09:30:00Z",
    "status": "MARKED", 
    "idClient": "1",
    "idOption": "2"
}
````
##### GET (resposta): 
````json 
[
    {
        "id": 1,
        "chosenDate": "2025-02-28T19:00:00Z",
        "status": "MARKED",
        "client": {
            "id": 1,
            "username": "Eduardo Benjamin",
            "email": "eduardo.b@gmail.com"
        },
        "option": {
            "id": 1,
            "name": "Corte",
            "price": 30.00
        }
    },
    {
        "id": 2,
        "chosenDate": "2025-01-20T19:00:00Z",
        "status": "PAYED",
        "client": {
            "id": 2,
            "username": "Maria Eduarda",
            "email": "maria.eduarda@gmail.com"
        },
        "option": {
            "id": 2,
            "name": "Corte_barba",
            "price": 50.00
        }
    }
]
````
##### GET /agendamentos/1 (resposta): 
````json
{
    "id": 1,
    "chosenDate": "2025-02-28T19:00:00Z",
    "status": "MARKED",
    "client": {
        "id": 1,
        "username": "Eduardo Benjamin",
        "email": "eduardo.b@gmail.com"
    },
    "option": {
        "id": 1,
        "name": "Corte",
        "price": 30.00
    }
}
````
##### GET /agendamentos/hoursForAgendate?chosenDay=27 (resposta): 
````json
[
    "2025-03-27T09:00:00",
    "2025-03-27T09:30:00",
    "2025-03-27T10:00:00",
    "2025-03-27T10:30:00",
    "2025-03-27T11:00:00",
    "2025-03-27T11:30:00",
    "2025-03-27T12:00:00",
    "2025-03-27T12:30:00",
    "2025-03-27T13:00:00",
    "2025-03-27T13:30:00",
    "2025-03-27T14:00:00",
    "2025-03-27T14:30:00",
    "2025-03-27T15:00:00",
    "2025-03-27T15:30:00",
    "2025-03-27T16:00:00",
    "2025-03-27T16:30:00",
    "2025-03-27T17:00:00",
    "2025-03-27T17:30:00"
]
````
###### Caso seja Domingo ou Segunda-Feira - A Lista estará vazia
###### Caso um horário já esteja agendado - Não estará disponível na Lista

##### PUT (requisição): 
````json 
{
	"status": "PAYED"
}
````

### Clientes 
| Método | Endpoint | Descrição |
|---------|---------|------------|
| **GET** | `/users` | Lista todos os usuários cadastrados |
| **GET** | `/users/{id}` | Busca o usuário por Id |
| **POST** | `/users` | Cadastra um novo usuário |
| **PUT** | `/users/{id}` | Atualiza um usuário por Id |
| **DELETE** | `/users/{id}` | Deleta um usuário |

#### Exemplos: 
##### GET (resposta): 
````json 
[
    {
        "id": 1,
        "username": "Eduardo Benjamin",
        "email": "eduardo.b@gmail.com"
    },
    {
        "id": 2,
        "username": "Maria Eduarda",
        "email": "maria.eduarda@gmail.com"
    }
]
````
##### GET /users/1 (resposta): 
````json 
{
    "id": 1,
    "username": "Eduardo Benjamin",
    "email": "eduardo.b@gmail.com"
}
````
##### POST (requisição): 
````json 
{
 "username": "Lucas",
 "email": "lucas@gmail.com",
 "password": "123456"
}
````
##### PUT /users/3 (requisição): 
````json 
{  
    "username": "Luquinhas",
    "email": "lucasinho@gmail.com",
    "password": "222222"
}
````

### Opções
| Método | Endpoint | Descrição |
|---------|---------|------------|
| **GET** | `/options` | Lista todas as opções cadastrados |
| **POST** | `/options` | Cadastra uma nova opção |
| **DELETE** | `/options/{id}` | Deleta uma opção |
| **PATCH** | `/options/{id}/price` | Atualiza o preço de uma opção |

#### Exemplos: 
##### GET (resposta): 
````json 
[
    {
        "id": 1,
        "name": "Corte",
        "price": 30.00
    },
    {
        "id": 2,
        "name": "Corte_barba",
        "price": 50.00
    }
]
````
##### POST (requisição): 
````json 
{
	"name": "Corte_Tesoura",
    "price": 40
}
````
##### PATCH options/3/price (requisição): 
````json 
{
	"price": "42.00"
}
````
### Actuator
| Método | Endpoint | Descrição |
|---------|---------|------------|
| **GET** | `/actuator/health` | Realiza um health check |
#### Exemplos: 
##### GET (resposta): 
````json 
{
    "status": "UP",
    "components": {
        "db": {
            "status": "UP",
            "details": {
                "database": "H2",
                "validationQuery": "isValid()"
            }
        },
        "diskSpace": {
            "status": "UP",
            "details": {
                "total": 1081101176832,
                "free": 1019216388096,
                "threshold": 10485760,
                "path": "/app/.",
                "exists": true
            }
        },
        "ping": {
            "status": "UP"
        },
        "ssl": {
            "status": "UP",
            "details": {
                "validChains": [],
                "invalidChains": []
            }
        }
    }
}
````

## Documentação da API

A documentação da API pode ser acessada via **Swagger** após iniciar a aplicação:
- **URL:** `http://localhost:8080/swagger-ui.html`

## Autor

Projeto desenvolvido por [Eduardo Bm Aguiar](https://github.com/EduardoBmAguiar).

