# Damas dos Reinos

Projeto em Java desenvolvido com Maven para simular o jogo de Damas dos Reinos em modo console.

## Sobre o projeto
Este repositório implementa um jogo de tabuleiro com:
- interface de console para exibir o tabuleiro;
- movimentação de peças;
- captura de peças;
- controle de turno;
- verificação de fim de jogo.

## Estrutura do projeto
- src/main/java: código principal do jogo e da interface de terminal;
- src/test/java: testes automatizados com JUnit 5.

## Requisitos
- Java 25
- Maven 3.9+

## Como executar
Na raiz do projeto, execute:

```bash
mvn clean package
java -jar target/damas.jar
```

Ao iniciar, o jogo solicitará entradas no formato:

```text
LinhaOrigem ColunaOrigem LinhaDestino ColunaDestino
```

Exemplo:

```text
5 0 4 1
```

## Como rodar os testes

```bash
mvn test
```
