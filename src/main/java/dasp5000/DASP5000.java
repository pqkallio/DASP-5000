
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Gate;
import dasp5000.domain.audioprocessors.MixerFromAbstract;
import dasp5000.obsoletestuff.audioprocessors.Normalizer;
import dasp5000.domain.audioprocessors.NormalizerFromAbstract;
import dasp5000.domain.audioprocessors.PhaseSwitcherFromAbstract;
import dasp5000.obsoletestuff.audioprocessors.Reverser;
import dasp5000.domain.audioprocessors.ReverserFromAbstract;
import dasp5000.gui.GraphicalUI;
import java.io.IOException;
import java.net.URL;
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
//        URL url = ClassLoader.getSystemResource("test.wav");
//        AudioController controller = new AudioController(url.getPath());
//        AudioContainer container = controller.getAudioContainer();
//        controller.printAudioAnalysis();
//        Reverser rev = new Reverser(container);
//        Normalizer norm = new Normalizer(container, -20);
//        rev.process();
//        norm.process();
//        controller.writeToFile("/home/pqkallio/wavtest/testus.wav");
//        controller.printAudioAnalysis();
//        GraphicalUI gui = new GraphicalUI();
//        SwingUtilities.invokeLater(gui);
        AudioController controller1 = new AudioController("/home/pqkallio/wavtest/test.wav");
        Gate g = new Gate(controller1.getAudioContainer(), 10000, 1000, 5000, 50);
        g.process();
        controller1.writeToFile("/home/pqkallio/wavtest/gatetesti.wav");
    }
}
