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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Representa um arquivo FDF. Essa classe libera todos os recursos
 * utilizados automaticamente.
 * @author Jarvis
 */
class fdfFile
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
    protected String data_inicio;                   // Data em que o formulário começa a aceitar respostas
    protected String data_termino;                  // Data em que o formulário termina de aceitar respostas
    protected final String local_formularios = "foton-formularios";
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

    /*
    
    */
    /**
     *  Não utilize este construtor, exceto para fins de teste
     */
    public fdfFile()
    {
        nomeArquivo = "";
        nomeFormulario = "";
        quantidade_questoes = 0;
        data_inicio = "";
        data_termino = "";
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
    public fdfFile(String filename, String nomeFormulario, int quantidade_de_questoes, String dataInicio, String dataTermino)
    {
        this.nomeArquivo = filename;
        this.nomeFormulario = nomeFormulario;
        this.quantidade_questoes = quantidade_de_questoes;
        this.data_inicio = dataInicio;
        this.data_termino = dataTermino;
        
        File pasta = new File(local_formularios);
        if(pasta.mkdir())
            this.nomeArquivo = local_formularios + "/" + filename;
        
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
    protected boolean isValid(String fileName)
    {        
        String modo = "rws";
        RandomAccessFile raf = null;
        
        try 
        {
            raf = new RandomAccessFile(fileName, modo);
            
            // Obtém o número mágico do arquivo
            int magic = raf.readInt();
            
            if(magic != this.header_magic)
            {
                System.out.println( "Numéro mágico inválido no arquivo " + fileName + ": " + Integer.toHexString(magic));
                return false;
            }
            
            String criacao = "", inicio = "", termino = "";
            
            //criacao = Base64.base64Decode(raf.readLine());
            //inicio = Base64.base64Decode(raf.readLine());
            //termino = Base64.base64Decode(raf.readLine());
            
            // Validar as datas
            int dia_criacao, mes_criacao, ano_criacao;
            int dia_inicio, mes_inicio, ano_inicio;
            int dia_termino, mes_termino, ano_termino;
            
            String[] sc = criacao.split("/");
            String[] si = inicio.split("/");
            String[] st = termino.split("/");
            
            if(sc.length != 3 || si.length != 3 || st.length != 3) 
            {
                System.out.println( "[ERRO] Pelo menos uma das datas no arquivo " + fileName + " está incorreta");
                raf.close();
                return false;
            }
            
            // TODO: melhorar validação
            dia_criacao = Integer.parseInt(sc[0]);
            mes_criacao = Integer.parseInt(sc[1]);
            ano_criacao = Integer.parseInt(sc[2]);
            
            dia_inicio = Integer.parseInt(si[0]);
            mes_inicio = Integer.parseInt(si[1]);
            ano_inicio = Integer.parseInt(si[2]);
            
            dia_termino = Integer.parseInt(st[0]);
            mes_termino = Integer.parseInt(st[1]);
            ano_termino = Integer.parseInt(st[2]);
            
            // Verifica se os dias são aceitáveis
            if(dia_criacao < 0 || dia_inicio < 0 || dia_termino < 0 || dia_inicio > 31 || dia_termino > 31 || dia_criacao > 31)
            {
                raf.close();
                return false;
            }
            
            // Verifica se os meses são aceitáveis
            if(mes_criacao < 0 || mes_inicio < 0 || mes_termino < 0 || mes_criacao > 12 || mes_inicio > 12 || mes_termino > 12)
            {
                raf.close();
                return false;
            }
            
            if(ano_criacao < 0 || ano_inicio < 0 || ano_termino < 0)
            {
                return false;
            }
            
            System.out.println( "Data de criação: " + criacao);
            System.out.println( "O formulário começará a aceitar respostas a partir de " + inicio);
            System.out.println( "O formulário irá parar de aceitar respostas a partir de " + termino);
            
            String nm, qtd, lresp;
            
            nm = raf.readLine();
            qtd = raf.readLine();
            lresp = raf.readLine();
            
            fdfFormat ff = new fdfFormat();
            
            if(!nm.startsWith(ff.nome_secao_nome))
                return false;
            
            if(!qtd.startsWith(ff.nome_secao_quantidade))
                return false;
            
            if(!lresp.startsWith(ff.nome_secao_respostas))
                return false;
            
            // Procura pelas seções de perguntas e respostas
            boolean bInicioResp = false, bInicioPerg = false, bFimResp = false, bFimPerg = false;
            
            while(true)
            {
                String line = raf.readLine();
                
                if(line == null)
                    break;
                
                if(line.equals(ff.nome_secao_perguntas))
                    bInicioPerg = true;
                
                if(line.equals(ff.nome_fim_secao_perguntas))
                    bFimPerg = true;
                
                if(line.equals(ff.nome_secao_respostas))
                    bInicioResp = true;
                
                if(line.equals(ff.nome_fim_secao_respostas))
                    bFimResp = true;
            }
            
            boolean condicao = bInicioPerg && bFimPerg && bInicioResp && bFimResp;
            
            if(condicao == false)
            {
                System.out.println( "O arquivo não contém uma das seções obrigatórias");
                return false;
            }
            
        } catch(IOException e)
        {
            try {
                System.out.println( "Erro durante verificação do arquivo " + fileName + ": " + e.getMessage());
                if(raf != null)
                    raf.close();
                
                return false;
            } catch (IOException ex) {
                Logger.getLogger(fdfFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return true;
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
