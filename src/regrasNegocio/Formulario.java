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
    private String nome;
    private String descricao;
    private String dataInicio;
    private String dataTermino;
    private String nomeAutor;
    public ArrayList<Pergunta> questoes;
    
    public Formulario(String nome, String descricao, String nomeAutor){
        this.nome = nome;
        this.descricao = descricao;
        this.nomeAutor = nomeAutor;
        
        questoes = new ArrayList<>();
    }
    
    /**
     *  
     * @param pergunta
     * Adiciona uma pergunta ao banco de questões do formulário
     */
    public void add(Pergunta pergunta){
        questoes.add(pergunta);
    }
    
    /**
     *
     * @param pergunta
     * Remove uma pergunta ao banco de questões do formulário
     */
    public void rmv(Pergunta pergunta){
        questoes.remove(pergunta);
    }
    
    public int questoesSize(){
        return questoes.size();
    }
    
    public Pergunta get(int x){
        return questoes.get(x);
    }
    
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataTermino() {
        return dataTermino;
    }

    public ArrayList<Pergunta> getQuestoes() {
        return questoes;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public boolean setNome(String nome) {
        this.nome = nome;
        
        return true;
    }
    
    public boolean setNomeAutor(String nome) {
        this.nomeAutor = nome;
        
        return true;
    }

    public boolean setDescricao(String descricao) {
        this.descricao = descricao;
        
        return true;
    }

    public boolean setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
        
        return true;
    }

    public boolean setDataTermino(String dataTermino) {
        this.dataTermino = dataTermino;
        
        return true;
    }    
}
