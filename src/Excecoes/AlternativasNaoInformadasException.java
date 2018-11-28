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
public class AlternativasNaoInformadasException extends Exception
{
    public AlternativasNaoInformadasException()
    {
        
    }
    
    @Override
    public String getMessage()
    {
        String s = "Alternativas não informadas";
        
        return s;
    }
}
