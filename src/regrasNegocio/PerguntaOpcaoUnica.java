package regrasNegocio;

/**
 * Classe para perguntas fechadas que admitem apenas uma resposta
 *
 * @author b2198
 */
public abstract class PerguntaOpcaoUnica extends PerguntaFechada {

    /**
     * Cria uma pergunta de opção única e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     * @param alternativas as alternativas da pergunta
     */
    public PerguntaOpcaoUnica(String texto, String[] alternativas) {
        super(texto, alternativas);
    }

    /**
     * Seta as respostas da pergunta, verificando se há apenas uma
     *
     * @param respostas as respostas da pergunta
     */
    @Override
    public void setRespostas(String[] respostas) {
        if(respostas.length == 1) {
            setRespostas(respostas);
        } else{
            //lança exceção
        }
    }

    /**
     * Seta as respostas da pergunta a partir dos índices das alternativas, verificando se há apenas uma resposta
     *
     * @param numerosRespostas os índices das respostas da pergunta
     */
    @Override
    public void setResposta(int... numerosRespostas) {
        if(numerosRespostas.length == 1) {
            setResposta(numerosRespostas);
        } else{
            //lança exceção
        }
    }
}
