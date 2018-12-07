/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FDF;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Jarvis
 */
public class fdfDecoder 
{
    public static void decodeFDF(String fileIn, String fileOut) throws FileNotFoundException, IOException
    {
        String mode = "rws";
        int key;
        RandomAccessFile rafIn, rafOut;
        
        rafIn = new RandomAccessFile(fileIn, mode);
        if(fileOut.isEmpty())
        {
            rafOut = new RandomAccessFile(fileIn + ".fdd", mode);
        } else {
            rafOut = new RandomAccessFile(fileOut, mode);
        }
        
        // Obtém a chave para decodificar o arquivo
        key = rafIn.read();
        
        while(true)
        {
            int b = rafIn.read();
            if(b == -1)
                break;
            
            rafOut.write(~(b - key));
        }
        
        rafIn.close();
        rafOut.close();
    }
    
    public static void decodeFDF(String fileIn) throws FileNotFoundException, IOException
    {
        decodeFDF(fileIn, "");
    }
}
