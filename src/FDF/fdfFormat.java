/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

/**
 *  Representa o formato de um arquivo FDF.
 * @author Jarvis
 */
public class fdfFormat extends fdfFile
{    

    /**
     *  Nome da seção aonde estão localizadas as perguntas do formulário em questão
     */
    protected final String nome_secao_perguntas = "%perguntas";

    /**
     *  Nome da seção aonde estão localizadas as respostas do formulário em questão
     */
    protected final String nome_secao_respostas = "%respostas";

    /**
     *  Armazena a quantidade total de perguntas contidas no formulário
     */
    protected final String nome_secao_quantidade = "quantidade";

    /**
     *  Nome da seção que armazena o nome dado ao formulário
     */
    protected final String nome_secao_nome = "nome";

    /**
     * Nome da seção que armazena a posição no arquivo aonde estão localizadas as respostas do formulário
     */
    protected final String nome_secao_local_respostas = "local_respostas";
    
    /**
     *  Nome da seção aonde terminam as perguntas
     */
    protected final String nome_fim_secao_perguntas = "%fim_perguntas";

    /**
     *  Nome da seção aonde terminam as respostas
     */
    protected final String nome_fim_secao_respostas = "%fim_respostas";
    
    /**
     *  Nome da seção aonde está localizado o nome do autor do formulário
     */
    protected final String nome_secao_autor = "autor";
    
    /**
     *  Nome da seção aonde está licalizado uma descrição breve do formulário
     */
    protected final String nome_secao_descricao = "descricao";
    
    /**
     *  Cria o cabeçalho do arquivo
     * @return
     */
    
    public String getLocalFormularios()
    {
        return local_formularios;
    }
};
