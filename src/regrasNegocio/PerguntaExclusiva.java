package regrasNegocio;

/**
 * Classe para perguntas exclusivas
 *
 * @author b2198
 */
public class PerguntaExclusiva extends PerguntaOpcaoUnica {

    /**
     * Cria uma pergunta exclusiva e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     * @param alternativas as alternativas da pergunta
     */
    public PerguntaExclusiva(String texto, String[] alternativas) {
        super(texto, alternativas);
    }

    /**
     * Cria uma pergunta exclusiva
     */
    public PerguntaExclusiva(){}
}
