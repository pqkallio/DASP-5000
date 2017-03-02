
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.audioprocessors.Delay;
import dasp5000.domain.audioprocessors.Reverser;
import dasp5000.gui.GraphicalUI;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingUtilities;

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
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        GraphicalUI gui = new GraphicalUI();
        SwingUtilities.invokeLater(gui);
//        AudioController ac = new AudioController("/home/pqkallio/wavtest/piano_A2.wav");
//        Delay dly = new Delay(ac.getAudioContainer(), 0.5, (int)(44100 * 1.5), 4);
//        dly.process();
//        ac.writeToFile("/home/pqkallio/wavtest/dly_test.wav");
    }
}
