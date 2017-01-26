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
        System.out.println(filee.exists());
        AudioController controller = new AudioController("/home/pqkallio/wavtest/mono.wav");
        try {
            controller.process("analyze");
        } catch (IOException ex) {
            System.out.println("No no no!");
        }
    }
    
}
