/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegrasNegocio;

/**
 *
 * @author b2198
 */
public class Pergunta {
    public String texto;
    public byte tipo;

    public String getTexto() 
    {
        return texto;
    }
    
    public boolean setTexto(String texto)
    {
        this.texto = texto;
        
        return true;
    }

    public void setTipo(byte tipo) 
    {
        this.tipo = tipo;
    }
    
    private boolean checaTexto()
    {
        
        
        return true;
    }
}
