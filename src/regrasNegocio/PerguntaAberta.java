package regrasNegocio;

/**
 * Classe usada para as perguntas do tipo aberta
 *
 * @author b2198
 */
public class PerguntaAberta extends Pergunta {

    /**
     * Cria uma pergunta aberta e inicializa o atributo texto
     *
     * @param texto o texto da pergunta
     */
    public PerguntaAberta(String texto){
        super(texto);
    }
    
    //

    /**
     * Seta as respostas da pergunta de forma padrão
     *
     * @param respostas as respostas da pergunta
     */
    @Override
    public void setRespostas(String[] respostas) {
        if(respostas.length == 1) {
            setRespostasInterno(respostas);
        } else{
            //lançar exceção
        }
    }

    /**
     * Seta as respostas da pergunta como uma única a partir de uma String
     *
     * @param resposta a resposta da pergunta
     */
    public void setReposta(String resposta){
        setRespostas(new String[]{resposta});
    }
}
