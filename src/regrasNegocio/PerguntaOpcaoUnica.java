package regrasNegocio;

import Excecoes.NumeroRespostasIncorretoException;
import Excecoes.RespostaInvalidaException;
import Excecoes.RespostaRepetidaException;

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
    public void setRespostas(String[] respostas) throws NumeroRespostasIncorretoException, RespostaInvalidaException {
        if(respostas.length == 1){
            try {
                super.setRespostas(respostas);
            } catch(RespostaRepetidaException ex){
                System.out.println("This exception should be impossible: " + ex.getMessage());
            }
        } else{
            throw new NumeroRespostasIncorretoException(1,respostas.length);
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
