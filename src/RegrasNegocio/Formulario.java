/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RegrasNegocio;

import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author b2198
 */
public class Formulario
{
    public String nome;
    public String descricao;
    public LocalTime dataInicio;
    public LocalTime dataTermino;
    public String nomeAutor;
    public ArrayList<Pergunta> questao;
    
    public Formulario(){
        
    }
    
    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalTime getDataInicio() {
        return dataInicio;
    }

    public LocalTime getDataTermino() {
        return dataTermino;
    }

    public boolean setNome(String nome) {
        this.nome = nome;
        
        return true;
    }

    public boolean setDescricao(String descricao) {
        this.descricao = descricao;
        
        return true;
    }

    public boolean setDataInicio(LocalTime dataInicio) {
        this.dataInicio = dataInicio;
        
        return true;
    }

    public boolean setDataTermino(LocalTime dataTermino) {
        this.dataTermino = dataTermino;
        
        return true;
    }
    
    private boolean salvar()
    {
        boolean ok = false;
        
        /* Primeiramente precisamos ler todos os dados que esão gravados nesta classe */
        
        return ok;
    }
    
}
