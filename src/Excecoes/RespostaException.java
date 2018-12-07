package Excecoes;

public abstract class RespostaException extends Exception {
    public RespostaException(){
        super();
    }
    public RespostaException(String message){
        super(message);
    }
    public RespostaException(String message,Throwable cause){
        super(message,cause);
    }
    public RespostaException(Throwable cause){
        super(cause);
    }
    public RespostaException(String message,Throwable cause,boolean enableSuppression,boolean writableStackTrace){
        super(message,cause,enableSuppression,writableStackTrace);
    }
}
