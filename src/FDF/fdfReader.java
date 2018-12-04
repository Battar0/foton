/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Executa todas as operações de leitura em um formulário FDF
 * @author Jarvis
 */
public class fdfReader extends fdfFormat 
{
    private final String arquivo;
    
    public fdfReader()
    {
        this.arquivo = "";
    }
    
    public fdfReader(String fileName)
    {
        this.arquivo = fileName;
    }
    
    private void init() throws FileNotFoundException
    {
        super.openRead(arquivo);
    }
    
    private void stop() throws IOException
    {
        super.closeRead();
    }
    
    private long getLocalSecaoRespostas() throws IOException
    {
        long local = 0;
        String line;
        long chars_lidos = 0;
        
        // Inicia o processo de E/S
        init();
        
        while((line = super.raf_inputStream.readLine()) != null)
        {
            chars_lidos += line.length();
            
            if(line.equals(super.nome_secao_respostas))
            {
                local = chars_lidos;
                break;
            }
        }
        
        local += super.nome_secao_respostas.length();
        
        setLocalSecaoRespostas(local);
        
        super.raf_inputStream.seek(0);
        
        // Finaliza o processo de E/S
        stop();
        
        return local;
    }
    
    private long getLocalSecaoPerguntas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = super.raf_inputStream.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(super.nome_secao_perguntas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - super.nome_secao_perguntas.length());
        
        setLocalSecaoPerguntas(local);
        
        super.raf_inputStream.seek(0);
        
        return local;
    }
    
    private long getLocalFimSecaoPerguntas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = super.raf_inputStream.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(super.nome_fim_secao_perguntas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - super.nome_fim_secao_perguntas.length());
        
        setLocalSecaoPerguntas(local);
        
        super.raf_inputStream.seek(0);
        
        return local;        
    }
    
    private long getLocalFimSecaoRespostas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = super.raf_inputStream.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(super.nome_fim_secao_respostas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - super.nome_fim_secao_respostas.length());
        
        setLocalSecaoPerguntas(local);
        
        super.raf_inputStream.seek(0);
        
        return local;        
    }
    
    private void setLocalSecaoRespostas(long local)
    {
        super.local_respostas = local;
    }
    
    private void setLocalSecaoPerguntas(long local)
    {
        super.local_perguntas = local;
    }
    
    /**
     *  Lê as repostas armazenadas em um determinado formulário
     * @param id_pergunta
     * ID da pergunta
     * @param quantidade_respostas
     * Não coloque nada nesse parâmetro. Ele serve apenas para salvar a quantidade de respostas obtidas
     * @return
     * @throws IOException
     */
    public String[] readRespostas(int id_pergunta, Integer quantidade_respostas) throws IOException
    {
        int count = 0;
        boolean start = false;
        String line;
        String id;
        String respostas;
        String[] vetor_respostas;
        String[] novo_vetor_respostas = {""};
        String[] line_split;
        
        // Inicia o processo de E/S
        init();
        
        // Precisamos garantir que estamos no início do arquivo
        raf_inputStream.seek(0);
        
        while(true)
        {
            line = raf_inputStream.readLine();
            if(line == null)
                break;
            
            if(line.startsWith(super.nome_secao_respostas))
            {
                // O início da seção respostas foi encontrado
                start = true;
                continue;
            }
            
            if(line.startsWith(super.nome_fim_secao_respostas))
                break;
            
            if(start)
            {
                // Precisamos procurar pelo id fornecido
                if(line.startsWith(String.valueOf(id_pergunta))) 
                {
                    // Basta varrer os itens dessa linha e armazenar em um vetor
                    line_split = line.split(",");
                    id = line_split[0];
                    respostas = line_split[1];
                    vetor_respostas = respostas.split(";");
                    
                    // Agora basta armazenar no vetor de respostas
                    for(String resposta: vetor_respostas)
                        count++;
                    
                    // Sabendo a quantidade de itens que existem no vetor, então
                    // fica fácil alocar memória para o ArrayList
                    novo_vetor_respostas = new String[count];
                    for(String r : vetor_respostas)
                        novo_vetor_respostas[count] = r;
                    
                    break;
                }
            }
        }
        
        if(count > 0)
        {
            quantidade_respostas = count;
            return novo_vetor_respostas;
        }
        
        return null;
    }
};
