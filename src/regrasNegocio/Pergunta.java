/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regrasNegocio;

import FDF.fdfFile.tipos_perguntas;

/**
 *
 * @author b2198
 */
public abstract class Pergunta 
{
    private String texto;
    private String[] resposta;
    private tipos_perguntas tipo;

    public Pergunta(String texto){
        setTexto(texto);
    }

    public void setTipo(tipos_perguntas tipo_perg){
        tipo = tipo_perg;
    }
    
    public tipos_perguntas getTipo()
    {
        return tipo;
    }
    
    public String getTexto() 
    {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String[] getResposta() {
        return resposta;
    }

    protected void setRespostaInterno(String[] resposta){
        this.resposta = resposta;
    }

    public abstract boolean setResposta(String[] resposta);
}
