Rodrigo Augusto Valeretto - 10684792
Leonardo Cerce Guioto - 10716640

Considerações sobre a parte dois do trabalho:

Sobre a parte 2, ela implementa a interface gráfica do editor de texto;
a forma como implementamos tem o seguinte funcionamento:
A interface é composta de dois campos de texto, um editável com subtítulo
"Comandos" e outro não editável com subtítulo "Texto".
Todos os comandos são colocados no campo comando e após isso aperta-se um botão
correspondente à ação desejada.
O campo do visor exibe o texto gravado na variável texto.

Sobre as funções:

Desfazer: Desfaz uma função anteriormente escolhida, por exemplo, se inserir um texto e selecionar
desfazer, esse texto é removido. A função desfazer não funciona com copiar e recortar texto. Não
é necessário digitar nada na aba de comandos, basta clicar no botão "Desfazer".

Refazer: Refaz uma função que foi anteriormente desfeita, no exemplo anterior, após desfazer a
inserção, ao selecionar refazer o texto será reinserido. A função refazer não funciona com copiar
e recortar. Não é necessário digitar nada na aba de comandos, basta clicar no botão "Refazer".

Inserir Texto: Digitar o texto desejado no campo comandos e clicar em "Inserir texto",
o texto desejado deve ser exibido no campo "Texto".

Remover Texto: Digitar a quantidade de caracteres que deseja remover e clicar em "Remover texto",
o texto sem os caracteres removidos deve ser exibido no campo "texto".
Caso seja digitado um número maior de caracteres do que tem no texto ou um caracter diferente
de um inteiro, nada será feito.

Copiar: Para que essa função funcione, é necessário selecionar com o mouse um trecho do texto
exibido no campo "Texto". Após isso, clicar no botão copiar salva essa seleção em uma variável,
como se fosse uma Área de Transferência, que pode ser usada com o colar depois.

Recortar: Tem o mesmo comportamento do copiar, com o diferencial de que ao clicar no botão "Recortar",
o texto que estava selecionado no campo "Texto" vai ser removido do texto principal que está sendo
editado.

Colar: Ao clicar no botão "Colar", o texto contido na variável copiado, que funciona como uma Área
de Transferência, é inserido no fim do texto que está sendo editado e deve aparecer no campo "Texto".

Salvar: Ao clicar no botão "Salvar", é iniciada uma thread, caso ela já não esteja iniciada, e começa
um processo de salvar um arquivo texto; para isso, antes de pressionar o botão é necessário que seja
digitado no campo de comandos o nome do arquivo que deseja ser salvo.

Desconectar: Clicar no botão "Desconectar" finaliza a thread que estava em execução esperando para salvar
o arquivo que estava sendo editado e também limpa quaisquer vestígios do arquivo que estava sendo editado,
como se abrisse um novo arquivo vazio.

Na Terceira parte foi implementada a função de comunicação entre clientes através de um servidor.
Se não houver nenhum arquivo criado disponivel e o usuário tentar abrir o arquivo, o cliente finalizará.
Caso o usuário feche a pagina de nomear arquivo ou escolher, o cliente também finalizará.
O contador de clientes apenar atualiza ao enviar uma mensagem do cliente, e às vezes ele recebe um delay
sendo necessário enviar mais de uma mensagem para atualizar.
