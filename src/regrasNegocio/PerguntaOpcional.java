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
        super(texto, null);
    }

    /**
     * Cria uma pergunta alternativa e inicializa o atributo alternativas
     */
    public PerguntaOpcional(){ setAlternativas(null); }

    /**
     * Define as alternativas como "Sim" e "Não"
     *
     * @param alternativas irrelevante
     */
    @Override
    public void setAlternativas(String[] alternativas) {
        super.setAlternativas(new String[]{"Sim","Não"});
    }
}
