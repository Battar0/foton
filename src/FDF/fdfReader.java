/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Jarvis
 */
public class fdfReader extends fdfFormat 
{
    private final String arquivo;
    
    public fdfReader(String fileName)
    {
        this.arquivo = fileName;
    }
    
    public void init() throws FileNotFoundException
    {
        openRead(arquivo);
    }
    
    public void stop() throws IOException
    {
        closeRead();
    }
    
    public long getLocalSecaoRespostas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = inBufferedReader.readLine()) != null)
        {
            chars_lidos = line.length();
            
            if(line.equals(nome_secao_respostas))
            {
                local = count;
                break;
            }
            count++;
        }
        
        local *= (chars_lidos - nome_secao_respostas.length());
        setLocalSecaoRespostas(local);
        
        return local;
    }
    
    public long getLocalSecaoPerguntas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = inBufferedReader.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(nome_secao_perguntas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - nome_secao_perguntas.length());
        
        setLocalSecaoPerguntas(local);
        
        return local;
    }
    
    public long getLocalFimSecaoPerguntas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = inBufferedReader.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(nome_fim_secao_perguntas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - nome_fim_secao_perguntas.length());
        
        setLocalSecaoPerguntas(local);
        
        return local;        
    }
    
    public long getLocalFimSecaoRespostas() throws IOException
    {
        long local = 0, count = 0;
        String line;
        long chars_lidos = 0;
        
        while((line = inBufferedReader.readLine()) != null)
        {
            chars_lidos = line.length();
            if(line.equals(nome_fim_secao_respostas))
            {
                local = count;
                break;
            }
            count++;
        }
     
        local *= (chars_lidos - nome_fim_secao_respostas.length());
        
        setLocalSecaoPerguntas(local);
        
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
};
