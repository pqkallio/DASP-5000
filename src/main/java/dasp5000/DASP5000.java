/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasp5000;

import dasp5000.controllers.AudioController;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        File filee = new File("/home/pqkallio/wavtest/mono.wav");
        AudioController controller;
        try {
            controller = new AudioController("/home/pqkallio/wavtest/mono.wav");
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.out.println(ex.toString());
            return;
        }
        
        controller.printAudioAnalysis();
    }
    
}
