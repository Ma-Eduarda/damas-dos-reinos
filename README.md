# Damas dos Reinos

Projeto desenvolvido em **Java** para a disciplina de **Padrões de Projeto de Software**, do curso de **Sistemas para Internet – IFPB**.

O projeto consiste em uma variação do jogo de damas executada via console, utilizando diferentes tipos de peças e regras próprias de movimentação e captura.

## Objetivo

Aplicar conceitos de **GRASP** e **SOLID** no desenvolvimento do jogo, buscando uma estrutura organizada, reutilizável e de fácil manutenção.

## Principais funcionalidades

* Tabuleiro 8x8;
* Soldado, Soldado Real, Cavaleiro e Mago;
* Movimentos e capturas específicos para cada peça;
* Controle de turnos;
* Promoção do Soldado;
* Captura opcional;
* Fim de jogo por eliminação ou afogamento;
* Testes automatizados com JUnit 5.

## Tecnologias

* Java 25
* Maven
* JUnit 5
* Git/GitHub

## Como executar

Na raiz do projeto:

```bash
mvn clean package
java -jar target/damas.jar
```

## Como executar os testes

```bash
mvn test
```
