package regrasNegocio;

import Excecoes.NumeroRespostasIncorretoException;
import Excecoes.RespostaInvalidaException;
import Excecoes.RespostaRepetidaException;

/**
 * Classe para todas as perguntas que envolvem alternativas
 *
 * @author b2198
 */
public class PerguntaFechada extends Pergunta {

    /**
     * as alternativas da pergunta fechada
     */
    private String[] alternativas;

    /**
     * Cria uma pergunta fechada e inicializa os atributos texto e alternativas
     *
     * @param texto o texto da pergunta
     * @param alternativas as alternativas da pergunta
     */
    public PerguntaFechada(String texto, String[] alternativas){
        super(texto);
        this.setAlternativas(alternativas);
    }

    /**
     * Cria uma pergunta fechada
     */
    public PerguntaFechada(){}

    /**
     * Seta as respostas da pergunta, verificando se não há respostas repetidas ou que não estão nas alternativas
     *
     * @param respostas as respostas da pergunta
     */
    @Override
    public void setRespostas(String[] respostas) throws NumeroRespostasIncorretoException, RespostaRepetidaException, RespostaInvalidaException {

        //checa se não há respostas repetidas
        for(int i = 0; i < respostas.length-1; i++){
            for(int j = i+1; j < respostas.length; j++){
                if(respostas[i].equals(respostas[j])){
                    throw new RespostaRepetidaException();
                }
            }
        }

        //checa se todas as respostas estão presentes nas alternativas
        boolean valida = false;
        for(int i = 0; i < respostas.length; i++){
            valida = false;
            for(int j = 0; j < alternativas.length; j++) {
                if (respostas[i].equals(alternativas[j])) {
                    valida = true;
                    break;
                }
            }
            if(!valida){
                break;
            }
        }

        //se a resposta é valida seta o atributo resposta em Pergunta
        if(valida) {
            setRespostasInterno(respostas);
        } else{
            throw new RespostaInvalidaException();
        }
    }

    /**
     * Seta as respostas da pergunta a partir dos índices das alternativas, verificando se não há respostas repetidas
     *
     * @param numerosRespostas os índices das respostas da pergunta
     */
    public void setResposta(int... numerosRespostas) throws RespostaRepetidaException{

        for(int i = 0; i < numerosRespostas.length-1; i++){
            for(int j = i+1; j < numerosRespostas.length; j++){
                if(numerosRespostas[i] == numerosRespostas[j]){
                    throw new RespostaRepetidaException();
                }
            }
        }

        String[] resposta = new String[numerosRespostas.length];
        for(int i = 0; i < numerosRespostas.length; i++){
            resposta[i] = alternativas[numerosRespostas[i]];
        }

        setRespostasInterno(resposta);
    }

    /**
     *
     * @return o número de alternativas desta pergunta fechada
     */
    public int getNumeroAlternativas()
    {
        if(alternativas != null)
            return alternativas.length;
        
        return 0;
    }

    /**
     *
     * @param alternativa o índice da alternativa
     * @return a alternativa no índice indicado
     */
    public String getAlternativa(int alternativa){
        return alternativas[alternativa];
    }

    /**
     * Seta as alternativas da pergunta fechada
     *
     * @param alternativas as alternativas da pergunta fechada
     */
    public void setAlternativas(String[] alternativas) {
        this.alternativas = alternativas;
    }
}
