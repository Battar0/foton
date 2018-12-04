package regrasNegocio;

public class PerguntaOpcional extends PerguntaOpcaoUnica {

    public PerguntaOpcional(String texto) {
        super(texto, new String[]{"sim","não"});
    }

    @Override
    public void setAlternativas(String[] alternativas) {}
}
