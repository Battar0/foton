package regrasNegocio;

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
        setAlternativas(alternativas);
    }

    /**
     * Seta as respostas da pergunta, verificando se não há respostas repetidas ou que não estão nas alternativas
     *
     * @param respostas as respostas da pergunta
     */
    @Override
    public void setRespostas(String[] respostas) {

        //checa se não há respostas repetidas
        for(int i = 0; i < respostas.length-1; i++){
            for(int j = i; j < respostas.length; j++){
                if(respostas[i].equals(respostas[j])){
                    //lança exceção
                }
            }
        }

        //checa se todas as respostas estão presentes nas alternativas
        boolean valida = false;
        for(int i = 0; i < alternativas.length; i++){
            valida = false;
            for(int j = 0; j < respostas.length; j++) {
                if (alternativas[i].equals(respostas[j])) {
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
            //lança exceção
        }
    }

    /**
     * Seta as respostas da pergunta a partir dos índices das alternativas, verificando se não há respostas repetidas
     *
     * @param numerosRespostas os índices das respostas da pergunta
     */
    public void setResposta(int... numerosRespostas){

        for(int i = 0; i < numerosRespostas.length-1; i++){
            for(int j = i; j < numerosRespostas.length; j++){
                if(numerosRespostas[i] == numerosRespostas[j]){
                    //lança exceção
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
    public int getNumeroAlternativas(){
        return alternativas.length;
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
