
package dasp5000;

import dasp5000.controllers.AudioController;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Program's main class.
 * @author Petri Kallio
 */
public class DASP5000 {

    /**
     * The main method. Note that you are required to pass the path of the 
     * audio file you want to open as the first argument. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AudioController controller;
        if (args.length > 0) {
            try {
                controller = new AudioController(args[0]);
            } catch (UnsupportedAudioFileException | IOException ex) {
                System.out.println(ex.toString());
                return;
            }
        } else {
            return;
        }
        
        controller.printAudioAnalysis();
    }
    
}
