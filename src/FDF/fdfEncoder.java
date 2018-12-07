/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.SecureRandom;

/**
 *  Classe criada especialmente para tornar arquivos FDF inelegíveis pelo ser humano
 * @author Jarvis
 */
public class fdfEncoder 
{
    public static String encodeFDF(String filename, String output) throws FileNotFoundException, IOException
    {
        String mode = "rws";
        RandomAccessFile raf;
        RandomAccessFile raf_out; 
        SecureRandom rnd = new SecureRandom();
        String encoded_filename;
        
        raf = new RandomAccessFile(filename, mode);
        if(output.isEmpty())
        {
            encoded_filename = filename + ".fde";
        } else {
            encoded_filename = output + ".fde";
        }
        
        raf_out = new RandomAccessFile(encoded_filename, mode);
        // A chave para decodificar o arquivo está no próprio arquivo
        
        rnd.setSeed(System.currentTimeMillis());
        int key = rnd.nextInt();
        
        raf_out.write(key);
        
        while(true)
        {
            int c = raf.read();
            if(c == -1)
                break;
            
            int ch = ~c + key;
            
            raf_out.write(ch);
        }
        
        raf.close();
        raf_out.close();
        
        return encoded_filename;
    }
    
    public static String encodeFDF(String filename) throws FileNotFoundException, IOException
    {
        return encodeFDF(filename, "");
    }
}
