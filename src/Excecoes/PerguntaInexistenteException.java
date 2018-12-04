/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excecoes;

/**
 *
 * @author Jarvis
 */
public class PerguntaInexistenteException extends Exception
{
    public PerguntaInexistenteException()
    {
       
    }
    
    @Override
    public String getMessage()
    {
        String s = "Pergunta inexistente";
        
        return s;
    }
}
