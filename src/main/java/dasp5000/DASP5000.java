/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000;

import dasp5000.controllers.AudioController;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author pqkallio
 */
public class DASP5000 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        String filePathMac = "/Users/petrikallio/wavtest/mono.wav";
        String filePathUbuntu = "/home/pqkallio/wavtest/mono.wav";
        AudioController controller;
        try {
            controller = new AudioController(filePathMac);
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.out.println(ex.toString());
            return;
        }
        
        controller.printAudioAnalysis();
    }
    
}
