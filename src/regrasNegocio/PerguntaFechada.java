package regrasNegocio;

public class PerguntaFechada extends Pergunta {

    private String[] alternativas;

    public PerguntaFechada(String texto, String[] alternativas){
        super(texto);
        setAlternativas(alternativas);
    }

    @Override
    public boolean setResposta(String[] resposta) {

        boolean valida = true;
        for(int i = 0; i < alternativas.length; i++){
            if(!alternativas[i].equals(resposta[i])){
                valida = false;
                break;
            }
        }

        setRespostaInterno(resposta);

        return valida;
    }

    public boolean setResposta(int... resposta){

        String[] respostas = new String[resposta.length];
        for(int i = 0; i < resposta.length; i++){
            respostas[i] = alternativas[resposta[i]];
        }

        setRespostaInterno(respostas);

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
