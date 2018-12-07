package Excecoes;

public class RespostaRepetidaException extends RespostaException {

    public RespostaRepetidaException(){
        super("Há respostas repetidas");
    }
}
