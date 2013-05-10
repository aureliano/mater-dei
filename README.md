Mater Dei
====
Mater Dei é um framework que auxilia na implementação de testes funcionais. Seguindo a premissa de BDD (Behavior Development Driven)
é utilizado o framework [JBehave](http://jbehave.org/) como parser das [Estórias de Usuário](http://en.wikipedia.org/wiki/User_story).
O driver utilizado como interface entre o teste e o navegador é o [Selenium](http://docs.seleniumhq.org/).

Este projeto foi criado com o objetivo de atender aos requisitos principais:
* Livrar o usuário da tarefa de configurar o seu projeto web para implementar testes funcionais. Basta ao usuário adicionar a dependência ao JAR deste projeto.  
* Implementar passos básicos para interação com o navegador. Ex.: preencher campo de texto, clicar em botão, clicar em link etc.
* Prover interface para preparação da base de dados, possibilitando que a base de dados esteja vazia antes da execução de cada cenário de teste.

Instalação
---
* Faça o download do [código-fonte como ZIP](https://github.com/aureliano/mater-dei/archive/master.zip), extraia-o e entre na pasta mater-dei-master para executar o comando.
```
mvn install
```

Ou

* Clone este repositório localmente e gere o JAR do projeto.
```
git clone git://github.com/aureliano/mater-dei.git
mvn install
```

Documentação
---
* [JavaDoc](/)
* [Wiki](https://github.com/aureliano/mater-dei/wiki): utilização, configuração do framework e exemplos 
* [Integração contínua](/): dicas para implantação