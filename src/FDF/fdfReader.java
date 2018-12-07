/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import Excecoes.NumeroRespostasIncorretoException;
import Excecoes.RespostaInvalidaException;
import Excecoes.RespostaRepetidaException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Base64;
import java.util.Base64.Decoder;

import regrasNegocio.Formulario;
import regrasNegocio.Pergunta;
import regrasNegocio.PerguntaAberta;
import regrasNegocio.PerguntaAlternativa;
import regrasNegocio.PerguntaExclusiva;
import regrasNegocio.PerguntaLista;
import regrasNegocio.PerguntaOpcional;

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
    
    /**
     *
     * @param nome_arquivo
     * Abre um arquivo FDF, lê suas informações e retorna uma classe do tipo Formulário
     * já instanciada, contendo todas as informações extraídas do arquivo.
     * @return
     * Retorna null caso o arquivo contenha algum erro em seu conteúdo
     * @throws java.io.FileNotFoundException
     * Lança essa exceção caso o arquivo não exista
     */
    public Formulario readFormulario(String nome_arquivo) throws FileNotFoundException, IOException
    {
        RandomAccessFile raf;
        FileReader fr;
        Formulario form;
        fdfFile ff = new fdfFile();
        Decoder dec = Base64.getDecoder();
        
        // Apenas para verificar se o arquivo existe
        fr = new FileReader(nome_arquivo);
        fr.close();
        
        raf = new RandomAccessFile(nome_arquivo, "rws");
        form = new Formulario();
        
        if(!ff.isValid(nome_arquivo))
            return null;
        
        String criacao, inicio, termino;
        String line;
        String[] vline;
        
        line = raf.readLine();
        criacao = String.valueOf(dec.decode(line));
        line = raf.readLine();
        inicio = String.valueOf(dec.decode(line));
        line = raf.readLine();
        termino = String.valueOf(dec.decode(line));
        
        
        form.setDataInicio(inicio);
        form.setDataTermino(termino);
        form.setDataCriacao(criacao);
        
        // Obtém o nome do formulário
        vline = raf.readLine().split("=");
        
        if(vline.length != 2)
            form.setNome("");
        else
            form.setNome(vline[1]);
        
        // Obtém a quantidade de questões que compõem o formulário
        vline = raf.readLine().split("=");
        
        if(vline.length != 2)
            form.setQuantidadeQuestoes(0);
        else
            form.setQuantidadeQuestoes(Integer.parseInt(vline[1]));
        
        // Extrai todas as perguntas do arquivo
        while(true)
        {
            line = raf.readLine();
            if(line == null)
                break;
            
            if(line.startsWith(nome_secao_perguntas))
            {
                do
                {
                    // Pulo as linhas vazias e vou para a parte TIPO,ID
                    do {
                        line = raf.readLine();
                    } while(line.isEmpty());

                    // Extraio o tipo, o ID e o enunciado da pergunta em questão
                    vline = line.split(",");

                    if(vline.length != 2)
                    {
                        // O arquivo está mal-formatado
                        raf.close();
                        return null;
                    }

                    String tipo = vline[0].substring(1).toUpperCase();
                    
                    // Enunciado
                    do {
                        line = raf.readLine();
                    } while(!line.startsWith("#"));
                    
                    String enunciado = line.substring(1);
                    String[] alternativas = new String[LIMITE_ALTERNATIVAS];
                    int c = 0;
                    
                    // Verifico qual é o tipo de pergunta. Após isso, basta extrair o as alternativas de cada uma, armazenar em um
                    // vetor e criar uma instância correspondente ao tipo da pergunta em questão
                    
                    if(tipo.equals(ff.get_tipo_str(tipos_perguntas.LISTA)))
                    { 
                        do {
                            line = raf.readLine();
                            alternativas[c] = line.substring(1);
                            c++;
                        } while(line.startsWith("@") && (c < LIMITE_ALTERNATIVAS));
                        
                         PerguntaLista pl = new PerguntaLista(enunciado, alternativas);
                         
                         form.add(pl);
                    } else if(tipo.equals(ff.get_tipo_str(tipos_perguntas.LIVRE)))
                    {
                         // Esse tipo de pergunta não possui alternativas
                        PerguntaAberta pa = new PerguntaAberta(enunciado);
                        form.add(pa);
                    } else if(tipo.equals(ff.get_tipo_str(tipos_perguntas.OPCIONAL)))
                    {
                        do {
                            line = raf.readLine();
                            alternativas[c] = line.substring(1);
                            c++;
                        } while(line.startsWith("$") && (c < LIMITE_ALTERNATIVAS));
                        
                         PerguntaOpcional pc = new PerguntaOpcional(enunciado);
                         
                         form.add(pc);                        
                    } else if(tipo.equals(ff.get_tipo_str(tipos_perguntas.ALTERNATIVA)))
                    {
                        do {
                            line = raf.readLine();
                            alternativas[c] = line.substring(1);
                            c++;
                        } while(line.startsWith("-") && (c < LIMITE_ALTERNATIVAS));
                        
                         PerguntaAlternativa pa = new PerguntaAlternativa(enunciado, alternativas);
                         
                         form.add(pa);
                    } else if(tipo.equals(ff.get_tipo_str(tipos_perguntas.EXCLUSIVA)))
                    {
                        do {
                            line = raf.readLine();
                            alternativas[c] = line.substring(1);
                            c++;
                        } while(line.startsWith("-") && (c < LIMITE_ALTERNATIVAS));
                        
                         PerguntaExclusiva pe = new PerguntaExclusiva(enunciado, alternativas);
                         form.add(pe);                           
                    } else 
                    {
                        // Tipo inválido encontrado
                        raf.close();
                        return null;
                    }
                } while(!line.startsWith(nome_fim_secao_perguntas));
            } else if(line.startsWith(nome_fim_secao_respostas))
            {
                do {
                    line = raf.readLine();
                    if(!line.isEmpty())
                    {
                        // Extraio informações sobre a resposta registrada em questão
                        
                        // Obtendo o tipo:
                        String[] first = line.split("$")[0].split(",");
                        
                        String tipo = first[0];
                        
                        // Obtendo o ID
                        int id = Integer.parseInt(first[1]);
                        
                        // Com o tipo, podemos inicializar uma classe do tipo Pergunta e adicionar ao formulário
                        
                    }
                } while(!line.startsWith(nome_fim_secao_respostas));
            }
        }
        
        raf.close();
        
        return form;
    }
};
