# Mercado de Ações

## O sistema deverá tratar da compra de ações para pessoas físicas.
⋅⋅* Uma Empresa possui um número limitado de ações para serem vendidas;
⋅⋅* As Empresas podem emitir novas ações porém não podemos diminuir o número de ações atuais;
⋅⋅* Cada ação pode pertencer a somente um Comprador;
⋅⋅* Uma Ação deve possuir a informação de quando foi comprada e de qual seu valor inicial e atual, juntamente das informações do seu Comprador;
⋅⋅* Um Comprador pode possuir várias Ações;
⋅⋅* O sistema precisa tratar de forma assíncrona a compra e venda das Ações;
⋅⋅* Durante uma compra ou venda, seu Comprador antigo e o novo precisam receber um email com a informação adequada sobre a operação;

## Instruções para Execução

### 1. Executar Docker: MongoDB e RabbitMQ
#### 1.1. MongoDB:
```
docker run -d -p 27017:27017 -v ~/docker_data/mongodb:/data/db mongo
```

#### 1.2. RabbitMQ:
```
docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management
```

### 2. Configurar e-mail de Origem
```
Alterar as constantes dentro do arquivo de configuração: /src/main/java/com/javaee/bruno/mercadoacoes/emailsender/EmailSender.java 
```
**Exemplo:**
```
final String fromEmail = "example@gmail.com";
final String password = "example";
```

### 3. Executar o projeto no Eclipse
```
Executar o projeto no Eclipse como um Springboot App
```
### 4. Endereços Docker:
* **MongoDB:** localhost:27017
* **RabbitMQ:** http://localhost:15672/

### 5. API's
#### 5.1. Empresas
##### 5.1.1. Criar empresa

**POST**
```
http://localhost:8080/api/v1/empresas
```

```
{
	"nome": "Lorem Ipsum",
	"email": "loremipsum@mail.com"
}
```

Resposta:
```
{
    "id": "X12345678-X123-12A4-123C-A012345678912",
    "nome": "Lorem Ipsum",
    "email": "loremipsum@mail.com",
    "timestamp": "2018-12-22 17:00:00.000"
}
```

##### 5.1.2. Listar empresas
**GET**
```
http://localhost:8080/api/v1/empresas
```

Resposta:
```
[
    {
        "id": "X12345678-X123-12A4-123C-A012345678912",
        "nome": "Lorem Ipsum",
        "email": "loremipsum@mail.com",
        "timestamp": "2018-12-22 17:00:00.000"
    }
]
```

#### 5.2. Investidores
##### 5.2.1. Criar investidor
**POST**
```
http://localhost:8080/api/v1/investidores/
```
```
{
        "nome": "John Doe",
        "email": "johndoe@mail.com"
}
```

Resposta:
```
{
    "id": "X12345678-X123-12A4-123C-A012345678912",
    "nome": "John Doe",
    "email": "johndoe@mail.com",
    "timestamp": "2018-12-22 17:00:00.000"
}
```

##### 5.2.2. Listar investidores
**GET**
```
http://localhost:8080/api/v1/investidores/
```

**Resposta:**
```
[
    {
        "id": "X12345678-X123-12A4-123C-A012345678912",
        "nome": "John Doe",
        "email": "johndoe@mail.com",
        "timestamp": "2018-12-22 17:00:00.000"
    }
]
```

#### 5.3. Emitir Ações (Empresa)
##### 5.3.1. Emitir ação para uma empresa cadastrada
**POST**
```
http://localhost:8080/api/v1/acaoes/emit/{empresaId}
```
```
{
    "nome": "XPTO1",
    "quantidade": 100000,
    "PrecoInicial": 50
}
```

**Resposta:**
```
{
    "id": "X12345678-X123-12A4-123C-A012345678912",
    "nome": "XPTO1",
    "quantidade": 100000,
    "quantidadeEmpresa": 100000,
    "PrecoInicial": 50,
    "timestamp": "2018-12-22 17:00:00.000"
}
```

##### 5.3.2. Exibir ações de uma empresa
**GET**
```
http://localhost:8080/api/v1/acoes/emit/{empresaId}
```

**Resposta:**
```
[
    {
        "id": "X12345678-X123-12A4-123C-A012345678912",
        "nome": "XPTO1",
        "quantidade": 100000,
        "quantidadeEmpresa": 100000,
        "PrecoInicial": 50,
        "timestamp": "2018-12-22 17:00:00.000"
    }
]
```

#### 5.4. Comprar ações (investidor)
##### 5.4.1. Comprar ações

**POST**
```
http://localhost:8080/api/v1/acoes/comprar/
http://localhost:8080/api/v1/acoes/comprar/{acaoId}/
http://localhost:8080/api/v1/acoes/comprar/{acaoId}/{investidorId}
```

```
{
    "acaoId": {acaoId},
    "investidorId": {investidorId},
    "quantidade": 5000,
    "preco": 50
}
```

**Resposta:**
```
{
    "id": "X12345678-X123-12A4-123C-A012345678912"
}
```

##### 5.4.2. Exibir demandas (compras recebidas) de ações

**GET**
```
http://localhost:8080/api/v1/acoes/comprar/
```

**Resposta:**
```
[
    {
        "id": "X12345678-X123-12A4-123C-A012345678912",
        "quantidade": 2000,
        "quantidadeComprador": 0,
        "preco": 20,
        "timestamp": "2018-08-07 00:38:33.477",
        "investidor": {
            "id": "X12345678-X123-12A4-123C-A012345678912",
            "nome": "John Doe",
            "email": "johndoe@mail.com",
            "timestamp": "2018-12-22 17:00:00.000"
        },
        "acao": {
            "id": "X12345678-X123-12A4-123C-A012345678912",
            "nome": "XPTO",
            "quantidade": 10000,
            "quantidadeEmpresa": 0,
            "precoInicial": 20,
            "timestamp": "2018-12-22 17:00:00.000"
        }
    }    
]
```

#### 5.5. Vender ações (investidor)
##### 5.5.1. Vender ações

**POST**
```
http://localhost:8080/api/v1/acoes/vender/
http://localhost:8080/api/v1/acoes/vender/{acaoId}/
http://localhost:8080/api/v1/acoes/vender/{acaoId}/{investidorId}
```

```
{
    "acaoId": {acaoId},
    "investidorId": {investidorId},
    "quantidade": 1000,
    "preco": 20
}
```

##### 5.5.2. Exibir ofertas (vendas) de ações

**GET**
```
http://localhost:8080/api/v1/acoes/vender/
```
**Resposta**
```
[
    {
        "id": "X12345678-X123-12A4-123C-A012345678912",
        "empresaOferta": true,
        "quantidade": 10000,
        "quantidadeVendido": 0,
        "preco": 30,
        "timestamp": "2018-12-22 17:00:00.000",
        "investidor": null,
        "acao": {
            "id": "X12345678-X123-12A4-123C-A012345678912",
            "nome": "XPTO",
            "quantidade": 10000,
            "quantidadeEmpresa": 10000,
            "precoInicial": 30,
            "timestamp": "2018-12-22 17:00:00.000"
        }
    }
]
```

#### 5.6. Texto de e-mails (exemplos)
##### 5.6.1. Venda realizada com sucesso

**Assunto:**
```
Notificação de venda ação X12345678-X123-12A4-123C-A012345678912
```
**Corpo do e-mail:**
```
5000 ações foram vendidas com sucesso no valor de 99.99 (preço unitário).
```

##### 5.6.2. Compra realizada com sucesso

**Assunto:**
```
Notificação de compra ação X12345678-X123-12A4-123C-A012345678912
```
**Corpo do e-mail:**
```
1500 ações foram compradas com sucesso no valor de 99.99 (preço unitário).
```





