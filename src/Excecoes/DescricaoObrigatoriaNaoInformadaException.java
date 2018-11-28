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
public class DescricaoObrigatoriaNaoInformadaException extends Exception 
{
    public DescricaoObrigatoriaNaoInformadaException()
    {
        
    }
    
    @Override
    public String getMessage()
    {
        String message = "Descrição obrigatória não informada";
        
        return message;
    }
}
