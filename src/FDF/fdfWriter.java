/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author Jarvis
 */
public class fdfWriter extends fdfFormat
{
    private final String arquivo;
    private int count = 0;
    
    public fdfWriter(String nomeArquivo)
    {
        arquivo = nomeArquivo;
    }
    
    /**
     *  Inicia a gravação dos dados no arquivo do formulário
     * @throws FileNotFoundException
     */
    public void init() throws FileNotFoundException, IOException
    {
        openWrite(arquivo);
    }
    
    public void init_append() throws FileNotFoundException, IOException
    {
        openWrite(arquivo, true);
    }
    
    public void stop() throws IOException
    {
        closeWrite();
    }
    
    /**
     * Salva num arquivo em disco a pergunta do formulário
     * @param titulo_pergunta
     * @param tipo
     * @param id_pergunta
     * @param alternativas
     * @throws IOException
     */
    public void writePergunta(String titulo_pergunta, tipos_perguntas tipo, int id_pergunta, String[] alternativas) throws IOException
    {
        String data;
        
        if(!super.isOpenWrite())
            throw new IOException( "O arquivo " + arquivo + " não está aberto no modo escrita");
        
        data = "" + tipo + "," + id_pergunta + "\n";
        data += "#" + titulo_pergunta + "\n";
        
        switch(tipo)
        {
            case LIVRE:
                break;
                
            case LISTA:
                for(String d : alternativas)
                {
                    data += "@" + d + "\n";
                }
                break;    
                
            case ALTERNATIVA:
                for(String d : alternativas)
                {
                    data += "-" + d + "\n";
                }
                break;
         
            case EXCLUSIVA:
                for(String d : alternativas)
                {
                    data += "!" + d + "\n";
                }
                break;
                
            case OPCIONAL:
                break;
        }

        // Agora precisamos ir até a posição correta no arquivo e iniciar a gravação dos dados
        
        outputStream.write(data);
    }
    
    /**
     * Salva num arquivo em disco a(s) resposta(s) dada(s) pelo usuário
     * @param id_pergunta
     * Identificador da pergunta
     * @param respostas
     * Vetor contendo todas as respostas registradas pelo programa
     * @throws IOException
     */
    public void writeResposta(int id_pergunta, String[] respostas) throws IOException
    {
        String data;
        
        data = id_pergunta + ",";
        
        for(String s: respostas)
        {
            data += s + ";";
            outputStream.write(data);
            data = "";
        }
    }
};
