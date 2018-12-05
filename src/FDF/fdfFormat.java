/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.nio.ByteBuffer;
import java.sql.Time;
import java.time.Instant;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Calendar;
import java.util.Date;

/**
 *  Representa o formato de um arquivo FDF.
 * @author Jarvis
 */
public class fdfFormat extends fdfFile
{    

    /**
     *  Nome da seção aonde estão localizadas as perguntas do formulário em questão
     */
    protected String nome_secao_perguntas = "%perguntas";

    /**
     *  Nome da seção aonde estão localizadas as respostas do formulário em questão
     */
    protected String nome_secao_respostas = "%respostas";

    /**
     *  Armazena a quantidade total de perguntas contidas no formulário
     */
    protected String nome_secao_quantidade = "quantidade";

    /**
     *  Nome da seção que armazena o nome dado ao formulário
     */
    protected String nome_secao_nome = "nome";

    /**
     * Nome da seção que armazena a posição no arquivo aonde estão localizadas as respostas do formulário
     */
    protected String nome_secao_local_respostas = "local_respostas";
    
    /**
     *  Nome da seção aonde terminam as perguntas
     */
    protected String nome_fim_secao_perguntas = "%fim_perguntas";

    /**
     *  Nome da seção aonde terminam as respostas
     */
    protected String nome_fim_secao_respostas = "%fim_respostas";
    
    /**
     *  Cria o cabeçalho do arquivo
     * @return
     */
    protected String buildHeader()
    {
        String str_header_data;
        ByteBuffer headerData;
        Date data = Time.from(Instant.now());
        String str_data_criacao;
        Encoder b64 = Base64.getEncoder();
        
        str_header_data = "" + header + "\n";
        str_data_criacao = "" + Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        str_data_criacao += Calendar.getInstance().get(Calendar.MONTH);
        str_data_criacao += Calendar.getInstance().get(Calendar.YEAR);
        
        str_header_data += b64.encodeToString(str_data_criacao.getBytes()) + "\n";
        str_header_data += nome_secao_nome + "=" + nomeFormulario + "\n";
        str_header_data += nome_secao_quantidade + "=" + this.quantidade_questoes + "\n";
        str_header_data += nome_secao_local_respostas + "=0" + "\n\n";
        str_header_data += nome_secao_perguntas + "\n\n" + nome_fim_secao_perguntas + "\n\n";
        str_header_data += nome_secao_respostas + "\n\n" + nome_fim_secao_respostas + "\n";
        
        return str_header_data;
    }
};
