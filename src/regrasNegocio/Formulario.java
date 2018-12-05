/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package regrasNegocio;

import FDF.fdfFile;
import FDF.fdfWriter;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *  Classe que armazena todos os dados de um formulário
 * @author b2198
 */
public class Formulario
{
    /**
     * nome do formulário
     */
    private String nome;
    /**
     * descrição do formulário
     */
    private String descricao;
    /**
     * data de início do formulário
     */
    private String dataInicio;
    /**
     * data de término do formulário
     */
    private String dataTermino;
    /**
     * nome do autor do formulário
     */
    private String nomeAutor;
    /**
     * lista de perguntas do formulário
     */
    public ArrayList<Pergunta> perguntas;

    /**
     * Cria um formulário e inicializa os atributos nome, descricao, nomeAutor e perguntas
     * 
     * @param nome nome do formulário
     * @param descricao descrição do formulário
     * @param nomeAutor nome do autor do formulário
     */
    public Formulario(String nome, String descricao, String nomeAutor){
        this.nome = nome;
        this.descricao = descricao;
        this.nomeAutor = nomeAutor;
        
        perguntas = new ArrayList<>();
    }
    
    /**
     * Adiciona uma pergunta à lista de perguntas do formulário
     * 
     * @param pergunta pergunta a ser adicionada
     */
    public void add(Pergunta pergunta){
        perguntas.add(pergunta);
    }
    
    /**
     *Remove uma pergunta da lista de perguntas do formulário
     * 
     * @param pergunta pergunta a ser removida
     */
    public void rmv(Pergunta pergunta){
        perguntas.remove(pergunta);
    }

    /**
     * Equivalente a getPerguntas().size()
     * 
     * @return o número de perguntas do formulário
     */
    public int perguntasSize(){
        return perguntas.size();
    }

    /**
     * Equivalente a getPerguntas.get(x)
     *
     * @param x
     * @return a pergunta no índice x da lista
     */
    public Pergunta get(int x){
        return perguntas.get(x);
    }

    /**
     *
     * @return o nome do formulário
     */
    public String getNome() {
        return nome;
    }

    /**
     *
     * @return a descrição do formulário
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     *
     * @return a data de início do formulário
     */
    public String getDataInicio() {
        return dataInicio;
    }

    /**
     *
     * @return a data de término do formulário
     */
    public String getDataTermino() {
        return dataTermino;
    }

    /**
     *
     * @return a lista de perguntas do formulário
     */
    public ArrayList<Pergunta> getPerguntas() {
        return perguntas;
    }

    /**
     *
     * @return o nome do autor do formulário
     */
    public String getNomeAutor() {
        return nomeAutor;
    }

    /**
     * Seta o nome do formulário
     *
     * @param nome o nome do formulário
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Seta o nome do autor do formulário
     *
     * @param nome o nome do autor do formulário
     */
    public void setNomeAutor(String nome) {
        this.nomeAutor = nome;
    }

    /**
     * Seta a descrição do formulário
     *
     * @param descricao a descrição do formulário
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;

    }

    /**
     * Seta a data de início do formulário
     *
     * @param dataInicio a data de início do formulário
     */
    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    /**
     * Seta a data de término do formulário
     *
     * @param dataTermino a data de término do formulário
     */
    public void setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
    }    
}
