package Excecoes;

public class RespostaRepetidaException extends Exception {

    public RespostaRepetidaException(){
        super("Há respostas repetidas");
    }
}
