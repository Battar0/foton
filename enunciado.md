# Trabalho final - Orientação a objetos

## Projeto de uma aplicação para criação de enquetes

Considere o cenário descrito a seguir. Você é parte de uma equipe de desenvolvimento de software que foi contratada para criar uma aplicação que gera formulários e gerencia as respostas desses formulários. Em termos gerais, essa aplicação é utilizada por uma empresa para capturar informações de interesse a partir de um conjunto de pessoas. Cada formulário é criado com um propósito específico e contém um conjunto de questões definidas por um usuário da empresa. O formulário é então disponibilizado ao conjunto de pessoas (respondentes) para que eles preencham com suas informações. Ao final, as respostas são agrupadas e enviadas de volta à empresa que criou o formulário.

Para a geração do formulário o funcionário da empresa deverá informar alguns itens básicos tais como, nome do formulário, descrição sobre a enquete que está sendo realizada, data de início e término da captura de respostas. Todos esses campos deverão ser informados durante a criação do formulário. À partir da criação básica do formulário, o responsável por sua criação informará as questões que compõem o formulário na ordem que elas deverão ser respondidas - só há um fluxo de resposta ao formulário, determinado pela sequência das perguntas.

As perguntas podem ser de diversos tipos, descritos em seguida. Perguntas abertas são aquelas que permitem a entrada de texto livre pelo respondente. Essas respostas podem ser de tamanho curto para, por exemplo, informar textos como endereço de e-mail, nome do respondente, naturalidade, etc… Outros textos livres permitem uma inclusão de informações de grande tamanho, por exemplo, observações, relatos, etc…

Outras perguntas são chamadas de fechadas pelo fato delas oferecerem um conjunto de respostas pré-definidas ao respondentes. Essas perguntas podem ser dos seguintes tipos: lista, alternativa, exclusiva e opcional. Perguntas do tipo lista oferecem uma conjunto de respostas pré-definidas dentre as quais o respondente deve selecionar apenas um item, como uma lista de estados de um país, por exemplo. Perguntas do tipo alternativa oferecem um conjunto de alternativas para resposta, sendo que o respondente pode marcar mais de uma alternativa. De modo similar, perguntas do tipo exclusiva oferecem um conjunto de alternativas de respostra dentre as quais o respondente deverá selecionar apenas uma alternativa. Por fim, perguntas do tipo opcional oferece apenas “sim” e “não” como alternativas para resposta. Independente do tipo de questão, todas elas possuem um enunciado descrito por um texto livre.

Durante a geração desses formulários as seguintes situações de erro devem ser previnidas. Ao criar um formulário sem informar valor para algum de seus campos básicos (nome, descricao, data de inicio e fim), uma exceção do tipo DescricaoObrigatoriaNaoInformadaException deve ser lançada, capturada e tratada pela aplicação. Ao criar qualquer tipo de questão (aberta ou fechada) sem o enunciado da questão, uma exceção do tipo EnunciadoNaoInformadoException deve ser lançada, capturada e tratada. Ao criar uma pergunta fechada, se não for adicionada nenhuma alternativa para resposta, uma exceção do tipo AlternativasNaoInformadasException deve ser lançada, captura e tratada. Todas as exceções deverão ser tratadas pela classe que faz a interface com o usuário e apresentar a mensagem de erro ao usuário.

A aplicação deve ser capaz de criar e gerenciar diversos (=mais de um) formulários os mesmo tempo. Cada formulário em criação deverá ser armazenado em disco e recuperado para edições posteriores.

Enunciado: Com base no cenário apresentado acima, faça o que se pede:

Apresente o diagrama de classes proposto e implementado para essa aplicação.
As classes que implementam interface com usuario, regras de negócio (classes relativas aos formulários) e exceções deverão estar separadas em pacotes distintos.
A aplicação deverá ser capaz de criar, armazenar e recuperar formulário salvos no disco.
Grupos: grupos de 3 alunos no mínimo e 4 no máximo.

### Entrega: Dia 30/11/2018

Pontos de controle: em todas as aulas a partir da publicação desse enunciado.