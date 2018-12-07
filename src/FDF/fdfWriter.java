/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.RandomAccessFile;

import regrasNegocio.Formulario;
import regrasNegocio.Pergunta;
import regrasNegocio.PerguntaAberta;
import regrasNegocio.PerguntaAlternativa;
import regrasNegocio.PerguntaExclusiva;
import regrasNegocio.PerguntaLista;
import regrasNegocio.PerguntaOpcional;


/**
 *  Use essa classe para gravar dados do formularío em um arquivo
 * @author Jarvis
 */
public class fdfWriter extends fdfFormat
{
    private String arquivo;
    private RandomAccessFile fdfWriter_raf_file;
    private File fdfWriter_file;
    private final fdfFile localInstance;
    
    /**
     *
     * @param nomeArquivo
     * Nome do arquivo aonde os dados serão salvos (sem extensão)
     * @param nome_formulario
     * Nome do formulário
     * @param qtd_questoes
     * Quantidade de questões que compõem o formulário
     * @param dataInicio
     * Data em que o formulário começará a aceitar respostas
     * @param dataTermino
     * Data limite para que o formulário seja respondido
     * @param autor
     * Nome do autor do formulário
     * @param descricao
     * Breve descrição sobre o formulário
     */
    public fdfWriter(String nomeArquivo, String nome_formulario, int qtd_questoes, String dataInicio, String dataTermino,
            String autor, String descricao)
    {
        arquivo = nomeArquivo + ".fdf";
        
        File pasta = new File(local_formularios);
        if(!pasta.exists()) 
        {
            if(pasta.mkdir())
                this.arquivo = local_formularios + "/" + arquivo;
        } else {
            this.arquivo = local_formularios + "/" + arquivo;
        }
        
        fdfWriter_file = null;
        fdfWriter_raf_file = null;
        
        localInstance = new fdfFile(nomeArquivo, nomeFormulario, qtd_questoes, dataInicio, dataTermino, autor, descricao);
        localInstance.outputFileHandle = new File(this.arquivo);
        
        localInstance.quantidade_questoes = qtd_questoes;
        localInstance.nomeFormulario = nome_formulario;
        localInstance.data_inicio = dataInicio;
        localInstance.data_termino = dataTermino;
        localInstance.descricaoFormulario = descricao;
        
        System.out.println( "[fdfWriter] arquivo inicializado: "  + localInstance.outputFileHandle.getAbsolutePath());
    }
    
    /**
     *  Inicia a gravação dos dados no arquivo do formulário
     * @throws FileNotFoundException
     */
    private void init() throws FileNotFoundException, IOException
    {
        System.out.println( "Init: " + localInstance.outputFileHandle.getAbsolutePath());
        
        if(localInstance.outputFileHandle.exists())
            fdfWriter_raf_file = localInstance.openWrite(this.arquivo, true);
        else
            fdfWriter_raf_file = localInstance.openWrite(this.arquivo);
        
        fdfWriter_file = new File(this.arquivo);
    }
    
    /**
     *  Fecha os arquivos utilizados pela instância dessa classe
     * anteriormente abertos pela função init()
     * @throws IOException
     */
    private void stop() throws IOException
    {
        localInstance.closeWrite();
    }
    
    /**
     * Salva num arquivo em disco a pergunta do formulário
     * @param titulo_pergunta
     * Texto da pergunta
     * @param tipo
     * Tipo da pergunta: pode ser LIVRE, LISTA, ALTERNATIVA, EXCLUSIVA ou OPCIONAL
     * @param id_pergunta
     * Identificador da pergunta em questão - Sequência dela
     * @param alternativas
     * Um vetor contendo todas as alternativas para a pergunta em questão. Se for do tipo livre, este parâmetro não é necessário
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
            fdfWriter_raf_file.seek(0);
            // Os dados precisam começar a ser gravados antes do final da seção respostas
            while(true)
            {
                original_file_line= fdfWriter_raf_file.readLine(); // Leio as linhas do arquivo original
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
            original_file_line = localInstance.raf_outputStream.readLine();
            if(original_file_line == null)
                break;
            
            if(!original_file_line.startsWith(super.nome_fim_secao_respostas))
            {
                raf_tempFile.writeBytes(original_file_line + "\n");
            } else {
                // Pego todas as respostas, e salvo no lugar daquela seção
                // Após isso, finalizo ela
                for(String s: respostas)
                {
                    str_data += s + ";";
                    raf_tempFile.writeBytes(str_data);
                    str_data = "";
                }
                raf_tempFile.writeBytes( "\n" + super.nome_fim_secao_respostas + "\n");
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
     * @throws java.io.InvalidClassException
     */
    public void writeFormulario(Formulario frm) throws IOException, InvalidClassException
    {
        String[] v_str, v_respostas;
        File tempFileHandle = new File(this.arquivo + ".tmp");
        int qtd_perguntas = frm.getPerguntas().size();
        RandomAccessFile raf_tempFile = null;
        
        System.out.println( "[writeFormulario] Quantidade de perguntas adicionadas: " + qtd_perguntas);

        try 
        {
            raf_tempFile = new RandomAccessFile(tempFileHandle.getAbsolutePath(), "rws");
                   
            raf_tempFile.getChannel().force(true);
            
            String original_file_line;
            
            // Abrindo o arquivo do formulário no modo leitura/escrita
            init();
            
            // Garantindo que estaremos sempre no início do arquivo ...
            fdfWriter_raf_file.seek(0);
            
            while (true) 
            {
                original_file_line = fdfWriter_raf_file.readLine();
                
                if(original_file_line == null)
                    break;
                                
                if(original_file_line.startsWith(super.nome_secao_descricao))
                {
                    // Gravo a descrição do formulário no arquivo
                    v_str = original_file_line.split("=");
                    if(v_str.length == 2)
                    {
                        v_str[1] = frm.getDescricao().replace("\n", " ");
                        original_file_line = v_str[0] + "=" + v_str[1];
                    }                    
                } else if(original_file_line.startsWith(super.nome_secao_autor))
                {
                    // Gravo o nome do autor no arquivo
                    v_str = original_file_line.split("=");
                    if(v_str.length == 2)
                    {
                        v_str[1] = frm.getNomeAutor();
                        original_file_line = v_str[0] + "=" + v_str[1];
                    }
                } else if(original_file_line.startsWith(super.nome_secao_nome + "="))
                {
                    // Preciso substituir o nome armazenado pelo nome especificado
                    
                    v_str = original_file_line.split("=");
                    if(v_str.length == 2)
                    {
                        v_str[1] = frm.getNome();
                        original_file_line = v_str[0] + "=" + v_str[1];
                    }
                }
                
                if(original_file_line.startsWith(super.nome_fim_secao_perguntas))
                {
                    // Pego todas as respostas, e salvo no lugar daquela seção
                    // Após isso, finalizo ela
                    
                    for(int i = 0; i < qtd_perguntas; i++)
                    {
                        Pergunta p = frm.getPerguntas().get(i);
                        
                        if(p instanceof PerguntaAberta)
                        {
                            // Escreve o tipo e o ID da pergunta
                            raf_tempFile.writeBytes("=" + get_tipo_str(tipos_perguntas.LIVRE) + "," + i + "\n");

                            // Escreve o texto dela
                            raf_tempFile.writeBytes("#" + p.getTexto() + "\n");
                            
                        } else if(p instanceof PerguntaOpcional)
                        {
                            // Escreve o tipo e o ID da pergunta
                            raf_tempFile.writeBytes("=" + get_tipo_str(tipos_perguntas.OPCIONAL) + "," + i + "\n");

                            // Escreve o texto dela
                            raf_tempFile.writeBytes("#" + p.getTexto() + "\n");
                            
                            // Escreve no arquivo as alternativas daquela pergunta
                            PerguntaOpcional pf = (PerguntaOpcional)p;
                            int n_alternativas = pf.getNumeroAlternativas();
                            
                            if(n_alternativas > 2)
                            {
                                System.out.println( "Por qual motivo uma pergunta do tipo opcional tem mais de duas alternativas?");
                            }
                            
                            for(int j = 0; j < n_alternativas; j++)
                                raf_tempFile.writeBytes("$" + pf.getAlternativa(j) + "\n");
                        }
                        else if(p instanceof PerguntaExclusiva)
                        {
                            // Escreve o tipo e o ID da pergunta
                            raf_tempFile.writeBytes("=" + get_tipo_str(tipos_perguntas.EXCLUSIVA) + "," + i + "\n");

                            // Escreve o texto dela
                            raf_tempFile.writeBytes("#" + p.getTexto() + "\n");
                            
                            // Escreve no arquivo as alternativas daquela pergunta
                            PerguntaExclusiva pe = (PerguntaExclusiva)p;
                            
                            int count = pe.getNumeroAlternativas();
                            
                            for(int j = 0; j < count; j++)
                            {
                                raf_tempFile.writeBytes("!" + pe.getAlternativa(j) + "\n");
                            }                          
                        } else if(p instanceof PerguntaLista)
                        {
                            // Escreve o tipo e o ID da pergunta
                            raf_tempFile.writeBytes("=" + get_tipo_str(tipos_perguntas.LISTA) + "," + i + "\n");

                            // Escreve o texto dela
                            raf_tempFile.writeBytes("#" + p.getTexto() + "\n");
                            
                            // Escreve no arquivo as alternativas daquela pergunta
                            PerguntaLista pl = (PerguntaLista)p;
                                
                            int count = pl.getNumeroAlternativas();
                            
                            for(int j = 0; j < count; j++)
                            {
                                raf_tempFile.writeBytes("@" + pl.getAlternativa(j) + "\n");
                            }  
                        } else if(p instanceof PerguntaAlternativa)
                        {
                             // Escreve o tipo e o ID da pergunta
                            raf_tempFile.writeBytes("=" + get_tipo_str(tipos_perguntas.ALTERNATIVA) + "," + i + "\n");

                            // Escreve o texto dela
                            raf_tempFile.writeBytes("#" + p.getTexto() + "\n");
                            
                            PerguntaAlternativa pa = (PerguntaAlternativa)p;
                            int count = pa.getNumeroAlternativas();
                            
                            for(int j = 0; j < count; j++)
                            {
                                raf_tempFile.writeBytes("-" + pa.getAlternativa(j) + "\n");
                            }  
                        }
                        else {
                            throw new InvalidClassException("Tipo de pergunta inválida especificada");
                        }
                    }
                    
                    raf_tempFile.writeBytes( "\n" + super.nome_fim_secao_perguntas + "\n");
                } else if(original_file_line.startsWith(nome_fim_secao_respostas))
                {
                    // Preciso ler todas as respostas registradas e armazenar no arquivo, caso existam
                    Pergunta px;
                    String tipo = "";
                    
                    for(int j = 0; j < qtd_perguntas; j++)
                    {
                        px = frm.get(j);
                        if(px != null) 
                        {                            
                            v_respostas = frm.get(j).getRespostas();
                            
                            if(v_respostas != null) 
                            {
                                if(v_respostas.length > 0) 
                                {
                                    if(px instanceof PerguntaAberta)
                                    {
                                        tipo = get_tipo_str(tipos_perguntas.LIVRE);
                                        raf_tempFile.writeBytes(tipo + "," + (j + 1) + "$");
                                        
                                        // Perguntas desse tipo possuem uma resposta armazenada na posição 0 do vetor
                                        
                                        // Infelizmente temos uma limitação no formato de arquivo, que nos atrapalharia se a resposta
                                        // tivesse '\n' em seu conteúdo
                                        if(v_respostas[0].contains("\n"))
                                            v_respostas[0] = v_respostas[0].replace("\n", " ");
                                        
                                        raf_tempFile.writeBytes(v_respostas[0]);
                                    } else if(px instanceof PerguntaOpcional)
                                    {
                                        tipo = get_tipo_str(tipos_perguntas.OPCIONAL);
                                        raf_tempFile.writeBytes(tipo + "," + (j + 1) + "$");
                                        
                                        // Perguntas desse tipo possuem apenas duas respostas
                                        raf_tempFile.writeBytes(v_respostas[0] + ";" + v_respostas[1]);
                                    } else if(px instanceof PerguntaAlternativa)
                                    {
                                        tipo = get_tipo_str(tipos_perguntas.ALTERNATIVA);
                                        raf_tempFile.writeBytes(tipo + "," + (j + 1) + "$");
                                        
                                        for (String v_resposta : v_respostas) 
                                        {
                                            raf_tempFile.writeBytes(v_resposta + ";");
                                        }
                                    } else if(px instanceof PerguntaLista)
                                    {
                                        tipo = get_tipo_str(tipos_perguntas.LISTA);
                                        raf_tempFile.writeBytes(tipo + "," + (j + 1) + "$");
                                        
                                        for (String v_resposta : v_respostas) 
                                        {
                                            raf_tempFile.writeBytes(v_resposta + ";");
                                        }
                                    } else if(px instanceof PerguntaExclusiva)
                                    {
                                        tipo = get_tipo_str(tipos_perguntas.EXCLUSIVA);
                                        raf_tempFile.writeBytes(tipo + "," + (j + 1) + "$");
                                        
                                        for (String v_resposta : v_respostas) 
                                        {
                                            raf_tempFile.writeBytes(v_resposta + ";");
                                        }
                                    } else {
                                        System.out.println( "Tipo de instância inválida!");
                                        System.exit(-1);
                                    }
                                    
                                    raf_tempFile.writeBytes("\n");
                                }
                            }
                        }
                    }
                    
                    raf_tempFile.writeBytes(super.nome_fim_secao_respostas + "\n");
                } else {
                    raf_tempFile.writeBytes(original_file_line + "\n");
                }
            }
        } catch(IOException e)
        {
            System.out.println("Erro: " + e.getMessage());
        }
        
        // Fecha o arquivo original
        stop();
        
        // Fecha o arquivo temporário
        if(fdfWriter_raf_file != null)
            fdfWriter_raf_file.close();
        
        if(raf_tempFile != null)
            raf_tempFile.getChannel().close();
        
        fdfWriter_file.delete();
        
        // Sobrescreve o arquivo original
        if(tempFileHandle.renameTo(fdfWriter_file) == false)
            System.out.println("Falha ao renomear " + tempFileHandle.getAbsolutePath());
        else {
            System.out.println( "Arquivo salvo em: " + fdfWriter_file.getAbsolutePath());
            tempFileHandle.delete();
            
            /*
            String sFile = fdfEncoder.encodeFDF(fdfWriter_file.getAbsolutePath());
            fdfWriter_file.delete();
            
            fdfWriter_file.renameTo(new File(sFile));
            */
        }
    }
};
