package regrasNegocio;

public abstract class PerguntaOpcaoUnica extends PerguntaFechada {


    public PerguntaOpcaoUnica(String texto, String[] alternativas) {
        super(texto, alternativas);
    }


    @Override
    public boolean setResposta(String[] resposta) {
        if(resposta.length == 1) {
            return super.setResposta(resposta);
        }
        return false;
    }

    @Override
    public boolean setResposta(int... numerosRespostas) {
        if(numerosRespostas.length == 1) {
            return super.setResposta(numerosRespostas);
        }
        return false;
    }
}
