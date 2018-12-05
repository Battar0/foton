package regrasNegocio;

/**
 * Classe para perguntas de sim ou não
 *
 * @author b2198
 */
public class PerguntaOpcional extends PerguntaOpcaoUnica {

    /**
     * Cria uma pergunta opcional e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     */
    public PerguntaOpcional(String texto) {
        super(texto, new String[]{"sim","não"});
    }

    /**
     * Não faz nada
     *
     * @param alternativas
     */
    @Override
    public void setAlternativas(String[] alternativas) {}
}
