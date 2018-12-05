package regrasNegocio;

/**
 * Classe para perguntas do tipo alternativa, que admitem mais de uma resposta
 */
public class PerguntaAlternativa extends PerguntaFechada {

    /**
     * Cria uma pergunta alternativa e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     * @param alternativas as alternativas da pergunta
     */
    public PerguntaAlternativa(String texto, String[] alternativas) {
        super(texto, alternativas);
    }

}
