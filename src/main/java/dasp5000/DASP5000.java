
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Normalizer;
import dasp5000.domain.audioprocessors.Reverser;
import java.io.IOException;
import java.net.URL;
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
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        URL url = ClassLoader.getSystemResource("test.wav");
        AudioController controller = new AudioController(url.getPath());
        AudioContainer container = controller.getAudioContainer();
        controller.printAudioAnalysis();
        Reverser rev = new Reverser(container);
        Normalizer norm = new Normalizer(container, -20);
        rev.process();
        norm.process();
        controller.writeToFile("/home/pqkallio/wavtest/testus.wav");
        controller.printAudioAnalysis();
    }
    
}
