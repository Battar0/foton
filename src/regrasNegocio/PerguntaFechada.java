package regrasNegocio;

public class PerguntaFechada extends Pergunta {

    private String[] alternativas;

    public PerguntaFechada(String texto, String[] alternativas){
        super(texto);
        setAlternativas(alternativas);
    }

    @Override
    public boolean setResposta(String[] resposta) {

        //checa se não há respostas repetidas
        for(int i = 0; i < resposta.length-1; i++){
            for(int j = i; j < resposta.length; j++){
                if(resposta[i].equals(resposta[j])){
                    return false;
                }
            }
        }

        //checa se todas as respostas estão presentes nas alternativas
        boolean valida = false;
        for(int i = 0; i < alternativas.length; i++){
            valida = false;
            for(int j = 0; j < resposta.length; j++) {
                if (alternativas[i].equals(resposta[j])) {
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
            setRespostaInterno(resposta);
            return true;
        }

        return false;
    }

    public boolean setResposta(int... numerosRespostas){

        String[] resposta = new String[numerosRespostas.length];
        for(int i = 0; i < numerosRespostas.length; i++){
            resposta[i] = alternativas[numerosRespostas[i]];
        }

        setRespostaInterno(resposta);

        return true;
    }

    public int getNumeroAlternativas(){
        return alternativas.length;
    }

    public String getAlternativa(int alternativa){
        return alternativas[alternativa];
    }

    public void setAlternativas(String[] alternativas) {
        this.alternativas = alternativas;
    }
}
