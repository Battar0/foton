/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import regrasNegocio.Formulario;
import regrasNegocio.Pergunta;
import regrasNegocio.PerguntaAberta;


/**
 *  Use essa classe para gravar dados do formularío em um arquivo
 * @author Jarvis
 */
public class fdfWriter extends fdfFormat
{
    private final String arquivo;
    private RandomAccessFile fdfWriter_file;
    
    public fdfWriter(String nomeArquivo)
    {
        arquivo = nomeArquivo + ".fdf";
        super.outputFileHandle = new File(this.arquivo);
        
        fdfWriter_file = null;
    }
    
    /**
     *  Inicia a gravação dos dados no arquivo do formulário
     * @throws FileNotFoundException
     */
    private void init() throws FileNotFoundException, IOException
    {
        if(super.outputFileHandle.exists())
            fdfWriter_file = openWrite(this.arquivo, true);
        else
            fdfWriter_file = openWrite(this.arquivo);
    }
    
    private void stop() throws IOException
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
        
        // Organizo os dados de maneira correta
        data = "=" + tipo + "," + id_pergunta + "\n";
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

        // Abre o arquivo origianl no modo leitura/escrita
        init();
        
        // Agora precisamos ir até a posição correta no arquivo e iniciar a gravação dos dados
        
        /*
            Como não é possível gravar um arquivo em uma posição específica sem sobrescrever os dados posteriores, então
            temos que copiar o conteúdo do arquivo para um temporário, salvar os dados no temporário e depois sobrescrever 
            o original com todo o conteúdo do arquivo temporário
        */

        File localFileHandle = new File(this.arquivo + ".tmp");
        try (RandomAccessFile raf = new RandomAccessFile(localFileHandle.getName(), "rws")) 
        {
            String original_file_line;
            System.out.println( "Abrindo arquivo: " + localFileHandle.getAbsolutePath());
            // Preciso garantir que estou no início do arquivo
            fdfWriter_file.seek(0);
            // Os dados precisam começar a ser gravados antes do final da seção respostas
            while(true)
            {
                original_file_line= fdfWriter_file.readLine(); // Leio as linhas do arquivo original
                if(original_file_line == null)
                    break;
                
                if(original_file_line.startsWith(super.nome_fim_secao_perguntas))
                {
                    // Pego os dados, sobrescrevo essa posição e finalizo a seção
                    raf.writeBytes(data + "\n" + super.nome_fim_secao_perguntas + "\n");
                } else {
                    // Pego os dados do arquivo original e copio para o arquivo temporário
                    raf.writeBytes(original_file_line + "\n");
                }
            }   // Fecha o arquivo original
            stop();
            // Fecha o arquivo temporário
        }
        
        // Apága o arquivo original
        File original = new File(this.arquivo);
        original.delete();
        
        File newFile = new File(this.arquivo);
        
        // Agora sobrescrevemos o original com o conteúdo do arquivo temporário
        boolean renameTo = localFileHandle.renameTo(newFile);
        if(!renameTo)
        {
            throw new IOException( "Não foi possível renomear o arquivo temporário");
        } else {
            System.out.println( "Arquivo renomeado para: " + newFile.getPath());
        }
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
        String str_data;
        File tempFileHandle = new File(arquivo + ".tmp");
        RandomAccessFile raf_tempFile = new RandomAccessFile(tempFileHandle.getName(), "rws");
        String original_file_line;
        
        str_data = id_pergunta + ",";
        
        // Abrindo o arquivo do formulário no modo leitura/escrita
        init();
        
        // Garantindo que estaremos sempre no início do arquivo ...
        super.raf_outputStream.seek(0);
        
        while(true)
        {
            original_file_line = super.raf_outputStream.readLine();
            if(original_file_line == null)
                break;
            
            if(original_file_line.startsWith(super.nome_fim_secao_respostas))
            {
                // Pego todas as respostas, e salvo no lugar daquela seção
                // Após isso, finalizo ela
                for(String s: respostas)
                {
                    str_data += s + ";";
                    raf_tempFile.writeBytes(str_data);
                    str_data = "";
                }
                raf_tempFile.writeBytes( "\n" + super.nome_fim_secao_respostas + "\n");
            } else {
                raf_tempFile.writeBytes(original_file_line + "\n");
            }
        }
        
        // Fecha o arquivo temporário
        raf_tempFile.close();
        
        // Fecha o arquivo original
        stop();
        
        // Sobrescreve o arquivo original
        tempFileHandle.renameTo(super.outputFileHandle);
    }
    
    /**
     *
     * @param frm
     * Instância da classe inicializada aonde os dados a serem salvos estão armazenados
     * @throws IOException
     */
    public void writeFormulario(Formulario frm) throws IOException
    {
        File tempFileHandle = new File(arquivo + ".tmp");
        try (RandomAccessFile raf_tempFile = new RandomAccessFile(tempFileHandle.getName(), "rws")) {
            String original_file_line;
            // Abrindo o arquivo do formulário no modo leitura/escrita
            init();
            // Garantindo que estaremos sempre no início do arquivo ...
            super.raf_outputStream.seek(0);
            while (true) {
                original_file_line = super.raf_outputStream.readLine();
                if(original_file_line == null)
                    break;
                
                if(original_file_line.startsWith(super.nome_fim_secao_perguntas))
                {
                    // Pego todas as respostas, e salvo no lugar daquela seção
                    // Após isso, finalizo ela
                    
                    for(Pergunta p : frm.questoes)
                    {
                        java.security.SecureRandom random = new java.security.SecureRandom();
                        int id = random.nextInt();
                        
                        // Escreve no arquivo o tipo, o id e o título da pergunta
                        raf_tempFile.writeBytes("=" + get_tipo_str(p.getTipo()) + "," + id + "\n" + "#" + p.getTexto() + "\n");
                        
                        switch(p.getTipo())
                        {
                            case LIVRE:
                                break;
                                
                            case LISTA:
                                
                                break;
                        }
                    }
                    
                    raf_tempFile.writeBytes( "\n" + super.nome_fim_secao_respostas + "\n");
                } else {
                    raf_tempFile.writeBytes(original_file_line + "\n");
                }
            }
            // Fecha o arquivo temporário
        }
        
        // Finalizando as operações de E/S
        stop();
        
        // Sobrescreve o arquivo original
        tempFileHandle.renameTo(super.outputFileHandle);

    }
};
