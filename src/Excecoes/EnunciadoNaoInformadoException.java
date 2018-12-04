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
public class EnunciadoNaoInformadoException extends Exception
{
    public EnunciadoNaoInformadoException()
    {
       
    }
    
    @Override
    public String getMessage()
    {
        String s = "Enunciado não informado";
        
        return s;
    }
}
