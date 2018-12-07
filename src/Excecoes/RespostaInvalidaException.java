package Excecoes;

public class RespostaInvalidaException extends Exception {

    public RespostaInvalidaException(){
        super("A resposta não está nas alternativas");
    }

}
