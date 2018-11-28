package regrasNegocio;

public class PerguntaAberta extends Pergunta {

    public PerguntaAberta(String texto){
        super(texto);
    }

    @Override
    public boolean setResposta(String[] resposta) {
        setRespostaInterno(resposta);
        return true;
    }

    public boolean setReposta(String texto){
        setResposta(new String[]{texto});
        return true;
    }
}
