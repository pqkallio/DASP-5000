
package dasp5000.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class RiffParser {
    
    public static void parseFile(File file) throws FileNotFoundException, IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[44];
        int bytesRead = fis.read(bytes);
        if (bytesRead == 44) parseHeader(bytes);
    }

    private static void parseHeader(byte[] bytes) {
        for (int i = 0; i < 4; i++) {
            System.out.print((char)bytes[i]);
        }
        System.out.println();
        
        byte[] buffer = new byte[4];
        int j = 4;
        for (int i = 3; i > -1; i--) {
            buffer[i] = bytes[j];
            j++;
        }
        int cs = byteToWordConversion(buffer, 4);
        System.out.println("Chunk size: " + cs);
        
        for (int i = 0; i < 4; i++) {
            buffer[i] = bytes[j];
            j++;
        }
        for (int i = 0; i < buffer.length; i++) {
            System.out.print((char)buffer[i]);
        }
        
        System.out.println("\nSubchunk1:");
        
        for (int i = 0; i < 4; i++) {
            buffer[i] = bytes[j];
            j++;
        }
        for (int i = 0; i < buffer.length; i++) {
            System.out.print((char)buffer[i]);
        }
        System.out.println("");
        
        for (int i = 3; i > -1; i--) {
            buffer[i] = bytes[j];
            j++;
        }
        int sc1size = byteToWordConversion(buffer, 4);
        System.out.println("Subchunk1 size: " + sc1size);
        
        for (int i = 1; i > -1; i--) {
            buffer[i] = bytes[j];
            j++;
        }
        int audioFormat = byteToWordConversion(buffer, 2);
        System.out.println("Audio format: " + audioFormat);
        
        for (int i = 1; i > -1; i--) {
            buffer[i] = bytes[j];
            j++;
        }
        int numChannels = byteToWordConversion(buffer, 2);
        System.out.println("Number of channels: " + numChannels);
    }
    
    public static int byteToWordConversion(byte[] bytes, int bytesN) {
        int word = bytes[0];
        for (int i = 1; i < bytesN; i++) {
            word = (word << 8) | (bytes[i] & 0xff);
        }
        return word;
    }
}
