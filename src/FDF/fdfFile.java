/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Representa um arquivo FDF.
 * @author Jarvis
 */
public class fdfFile
{
    protected final String header = "FDF";             // Cabeçalho do arquivo
    protected final int header_magic = 0x444644;    // Numero mágico do arquivo (cabeçalho em hexa)
    protected String nomeArquivo;                   // Nome do arquivo em disco
    protected String nomeFormulario;                // Nome do formulário
    protected int quantidade_questoes;              // Quantidade de questões que o compõem
    protected long local_respostas;                 // Posição no arquivo aonde estão armazenadas as repsostas
    protected long local_perguntas;                 // Posição no arquivo estão armazenadas as pergntas
    protected FileReader inputStream;          // Indentificador para o arquivo a ser lido
    protected FileWriter outputStream;        // Identificador para o arquivo a ser criado
    protected BufferedReader outBufferedReader;        // Leitura de dados através de um buffer
    protected BufferedReader inBufferedReader;      // Leitura de dados através de um buffer
    protected File inputFileHandle;                 // Indicativo para o arquivo aberto no modo leitura
    protected File outputFileHandle;                // Inditativo para o arquivo aberto no modo escrita
    public static enum tipos_perguntas
    {
        LIVRE, LISTA, ALTERNATIVA, EXCLUSIVA, OPCIONAL
    };

    /**
     *  Não utilize este construtor, exceto para fins de teste
     */
    public fdfFile()
    {
        nomeArquivo = "test.fdf";
        nomeFormulario = "formulario-teste";
        quantidade_questoes = 30;
        
    }
    
    /**
     *
     * @param filename
     * Nome do arquivo a ser aberto
     * @param nomeFormulario
     * Nome do formulário
     * @param quantidade_de_questoes
     * Quantidade de questões que compõem o formulário
     */
    public fdfFile(String filename, String nomeFormulario, int quantidade_de_questoes)
    {
        this.nomeArquivo = filename;
        this.nomeFormulario = nomeFormulario;
        this.quantidade_questoes = quantidade_de_questoes;
        outBufferedReader = null;
        inBufferedReader = null;
    }
    
    /**
     *  Abre o arquivo no modo leitura. O nome do arquivo precisa ter sido fornecido na função construtura
     * @return
     * @throws FileNotFoundException
     */
    public FileReader openRead() throws FileNotFoundException
    {
        inputStream = new FileReader(nomeArquivo);
        inputFileHandle = new File(nomeArquivo);
        inBufferedReader = new BufferedReader(new FileReader(inputFileHandle));
        
        return inputStream;
    }
    
    /**
     *
     * @param filenameString
     * Nome do arquivo a ser aberto. Ele precisa existir em disco
     * @return
     * @throws FileNotFoundException
     */
    public FileReader openRead(String filenameString) throws FileNotFoundException
    {
        inputStream = new FileReader(filenameString);
        inputFileHandle = new File(nomeArquivo);
        inBufferedReader = new BufferedReader(new FileReader(inputFileHandle));
        
        return inputStream;
    }
    
    private void writeHeader() throws IOException
    {
        fdfFormat fdf = new fdfFormat();
        
        String fdf_header = fdf.buildHeader();
        
        outputStream.write(fdf_header);
        outputStream.flush();
    }
    
    /**
     *  Abre o arquivo especificado pela função construtora no modo escrita
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public FileWriter openWrite() throws FileNotFoundException, IOException
    {
        outputStream = new FileWriter(nomeArquivo);
        outputFileHandle = new File(nomeArquivo);
        outBufferedReader = new BufferedReader(new FileReader(outputFileHandle));
        
        // Escreve o cabeçalho no arquivo
        writeHeader();
        
        return outputStream;
    }
    
    /**
     *
     * @param fileString
     * Nome do arquivo a ser aberto
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public FileWriter openWrite(String fileString) throws FileNotFoundException, IOException
    {
        outputStream = new FileWriter(fileString);
        outputFileHandle = new File(nomeArquivo);
        outBufferedReader = new BufferedReader(new FileReader(outputFileHandle));
        
        // Escreve o cabeçalho no arquivo
        writeHeader();
        
        return outputStream;
    }
        
    /**
     *
     * @param fileString
     * Nome do arquivo a ser aberto
     * @param append
     * Se verdadeiro, então os dados começarão a ser gravados no final do arquivo
     * @return
     * @throws FileNotFoundException
     */
    public FileWriter openWrite(String fileString, boolean append) throws FileNotFoundException, IOException
    {
        outputStream = new FileWriter(fileString, append);
        outBufferedReader = new BufferedReader(new FileReader(outputFileHandle));
        
        // Escreve o cabeçalho no arquivo
        writeHeader();
        
        return outputStream;
    }
        
    /**
     *  Fecha um arquivo anteriormente aberto no modo escrita
     * @throws IOException
     */
    public void closeWrite() throws IOException
    {
        outputStream.close();
    }
    
    /**
     *  Fecha um arquivo anteriormente aberto no modo leitura
     * @throws IOException
     */
    public void closeRead() throws IOException
    {
        inputStream.close();
    }
    
    /**
     *  Fecha todos os arquivos abertos pela instância dessa classe
     * @throws IOException
     */
    public void closeAll() throws IOException
    {
        outputStream.close();
        inputStream.close();
    }
    
    public boolean isOpenRead()
    {
        // TODO: implementar
        return false;
    }
    
    /**
     *  Verifica se o arquivo está aberto no modo escrita
     * @return
     * Falso caso o arquivo não esteja aberto
     */
    public boolean isOpenWrite()
    {
        // TODO: implementar
        return false;
    }
    
    /**
     *
     * @param fileName
     * Nome do arquivo a ser verificado quanto à sua validade.
     * @return
     */
    public boolean isValid(String fileName)
    {        
        // TODO: Implementar
        return false;
    }
    
    /**
     *  Obtém a localização em disco do arquivo aberto no modo escrita
     * @return
     */
    public String getInputFileLocation()
    {
        return inputFileHandle.getPath();
    }
    
    /**
     *  Obtém a localização em disco do arquivo aberto no modo leitura
     * @return
     */
    public String getOutputFileLocation()
    {
        return outputFileHandle.getPath();
    }
}
