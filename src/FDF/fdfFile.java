/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.RandomAccessFile;

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
    protected RandomAccessFile raf_inputStream;          // Indentificador para o arquivo a ser lido
    protected RandomAccessFile raf_outputStream;        // Identificador para o arquivo a ser criado
    protected File inputFileHandle;                 // Indicativo para o arquivo aberto no modo leitura
    protected File outputFileHandle;                // Inditativo para o arquivo aberto no modo escrita
    public static enum tipos_perguntas
    {
        LIVRE, LISTA, ALTERNATIVA, EXCLUSIVA, OPCIONAL
    };
    public static final String tipos_perguntas_str[] =
    {
        "LIVRE",
        "LISTA",
        "ALTERNATIVA",
        "EXCLUSIVA",
        "OPCIONAL"
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
     * @param tp
     * Tipo da pergunta
     * @return
     */
    protected final String get_tipo_str(tipos_perguntas tp)
    {
        switch (tp) 
        {
            case LIVRE:
                return tipos_perguntas_str[0];
            case LISTA:
                return tipos_perguntas_str[1];
            case ALTERNATIVA:
                return tipos_perguntas_str[2];
            case EXCLUSIVA:
                return tipos_perguntas_str[3];
            case OPCIONAL:
                return tipos_perguntas_str[4];
            default:
                return "";
        }
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
    }
    
    /**
     *  Abre o arquivo no modo leitura. O nome do arquivo precisa ter sido fornecido na função construtura
     * @return
     * @throws FileNotFoundException
     */
    public RandomAccessFile openRead() throws FileNotFoundException
    {
        inputFileHandle = new File(nomeArquivo);
        raf_inputStream = new RandomAccessFile(inputFileHandle, "r");
        
        return raf_inputStream;
    }
    
    /**
     *
     * @param filenameString
     * Nome do arquivo a ser aberto. Ele precisa existir em disco
     * @return
     * @throws FileNotFoundException
     */
    public RandomAccessFile openRead(String filenameString) throws FileNotFoundException
    {
        raf_inputStream = new RandomAccessFile(filenameString, "r");
        inputFileHandle = new File(nomeArquivo);        
        return raf_inputStream;
    }
    
    private void writeHeader() throws IOException
    {
        fdfFormat fdf = new fdfFormat();
        
        String fdf_header = fdf.buildHeader();
        
        raf_outputStream.writeBytes(fdf_header);
    }
    
    /**
     *  Abre o arquivo especificado pela função construtora no modo escrita
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public RandomAccessFile openWrite() throws FileNotFoundException, IOException
    {
        raf_outputStream = new RandomAccessFile(nomeArquivo, "rws");
        outputFileHandle = new File(nomeArquivo);
        
        // Força todos os dados no arquivo a serem gravados imediatamente
        raf_outputStream.getChannel().force(true);
        
        // Escreve o cabeçalho no arquivo
        writeHeader();
        
        return raf_outputStream;
    }
    
    /**
     *
     * @param fileString
     * Nome do arquivo a ser aberto
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    protected RandomAccessFile openWrite(String fileString) throws FileNotFoundException, IOException
    {
        raf_outputStream = new RandomAccessFile(fileString, "rws");
        outputFileHandle = new File(nomeArquivo);
        
        raf_outputStream.getChannel().force(true);
        
        // Escreve o cabeçalho no arquivo
        writeHeader();
        
        return raf_outputStream;
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
    protected RandomAccessFile openWrite(String fileString, boolean append) throws FileNotFoundException, IOException
    {
        raf_outputStream = new RandomAccessFile(fileString, "rws");
        raf_outputStream.getChannel().force(true);
        
        // Se o arquivo já existe, então supõe-se que não é necessário escrever o cabeçalho nele novamente
        
        return raf_outputStream;
    }
        
    /**
     *  Fecha um arquivo anteriormente aberto no modo escrita
     * @throws IOException
     */
    protected void closeWrite() throws IOException
    {
        raf_outputStream.close();
    }
    
    /**
     *  Fecha um arquivo anteriormente aberto no modo leitura
     * @throws IOException
     */
    protected void closeRead() throws IOException
    {
        raf_inputStream.close();
    }
    
    /**
     *  Fecha todos os arquivos abertos pela instância dessa classe
     * @throws IOException
     */
    protected void closeAll() throws IOException
    {
        raf_outputStream.close();
        raf_inputStream.close();
    }
    
    protected boolean isOpenRead()
    {
        // TODO: implementar
        return false;
    }
    
    /**
     *  Verifica se o arquivo está aberto no modo escrita
     * @return
     * Falso caso o arquivo não esteja aberto
     */
    protected boolean isOpenWrite()
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
    public static boolean isValid(String fileName)
    {        
        // TODO: Implementar
        return false;
    }
    
    /**
     *  Obtém a localização em disco do arquivo aberto no modo escrita
     * @return
     */
    protected String getInputFileLocation()
    {
        return inputFileHandle.getPath();
    }
    
    /**
     *  Obtém a localização em disco do arquivo aberto no modo leitura
     * @return
     */
    protected String getOutputFileLocation()
    {
        return outputFileHandle.getPath();
    }
}
