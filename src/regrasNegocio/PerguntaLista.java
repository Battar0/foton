package regrasNegocio;

/**
 * Classe para perguntas de lista
 *
 * @author b2198
 */
public class PerguntaLista extends PerguntaOpcaoUnica {

    /**
     * Cria uma pergunta de lista e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     * @param alternativas as alternativas da pergunta
     */
    public PerguntaLista(String texto, String[] alternativas) {
        super(texto, alternativas);
    }

}
