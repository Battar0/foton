package Excecoes;

public class NumeroRespostasIncorretoException extends Exception {

    public NumeroRespostasIncorretoException(int numeroEsperado, int numeroInserido){
        super("Número de respostas incorreto. Esperado: " + numeroEsperado + " Inserido: " + numeroInserido);
    }
}
