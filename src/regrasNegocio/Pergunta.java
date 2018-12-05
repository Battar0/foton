/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regrasNegocio;

import FDF.fdfFile.tipos_perguntas;

/**
 * Classe usada para representar perguntas de um formulário
 *
 * @author b2198
 */
public abstract class Pergunta 
{
    /**
     * texto da pergunta
     */
    private String texto;
    /**
     * lista de repostas da pergunta
     */
    private String[] respostas;

    /**
     * Cria uma pergunta e inicializa o atributo texto
     *
     * @param texto o texto da pergunta
     */
    public Pergunta(String texto){
        setTexto(texto);
    }
    /**
     *
     * @return o texto da pergunta
     */
    public String getTexto(){
        return texto;
    }

    /**
     * Seta o texto da pergunta
     *
     * @param texto o texto da pergunta
     */
    public void setTexto(String texto) {
        this.texto = texto;
    }

    /**
     *
     * @return as respostas da pergunta
     */
    public String[] getRespostas() {
        return respostas;
    }

    /**
     * Seta as respostas da pergunta
     *
     * @param respostas as respostas da pergunta
     */
    protected void setRespostasInterno(String[] respostas){
        this.respostas = respostas;
    }

    /**
     * Seta as respostas da pergunta
     *
     * @param respostas as respostas da pergunta
     */
    public abstract void setRespostas(String[] respostas);
}
