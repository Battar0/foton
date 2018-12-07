package Excecoes;

public class RespostaInvalidaException extends RespostaException {

    public RespostaInvalidaException(){
        super("A resposta não está nas alternativas");
    }

}
