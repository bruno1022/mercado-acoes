# Mercado de Ações

## O sistema deverá tratar da compra de ações para pessoas físicas.
- Uma Empresa possui um número limitado de ações para serem vendidas;
- As Empresas podem emitir novas ações porém não podemos diminuir o número de ações atuais;
- Cada ação pode pertencer a somente um Comprador;
- Uma Ação deve possuir a informação de quando foi comprada e de qual seu valor inicial e atual, juntamente das informações do seu ## Comprador;
- Um Comprador pode possuir várias Ações;
- O sistema precisa tratar de forma assíncrona a compra e venda das Ações;
- Durante uma compra ou venda, seu Comprador antigo e o novo precisam receber um email com a informação adequada sobre a operação;

## Instruções para Execução

### 1. Executar Docker MongoDB e RabbitMQ
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
Alterar as constantes dentro do arquivo de configuração: /src/main/java/com/javaee/bruno/mercadoacoes/emailsender/EmailSender.java **Exemplo: **
final String fromEmail = "example@gmail.com";
final String password = "example";
```

### 3. Executar o projeto no Eclipse
```
Executar o projeto no Eclipse como um Springboot App
```
### 4. Endereços Docker:
* MongoDB: mongodb://localhost:27017
* RabbitMQ: http://localhost:15672/

### 5. API's
#### 5.1. Empresas
##### 5.1.1. Criar empresa
```
POST http://localhost:8080/api/v1/empresas
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
    "name": "Lorem Ipsum",
    "email": "loremipsum@mail.com",
    "timestamp": "2018-12-22 17:00:00.000"
}
```
