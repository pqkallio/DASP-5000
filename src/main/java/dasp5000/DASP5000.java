
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.audioprocessors.Mixer;
import dasp5000.domain.audioprocessors.Normalizer;
import dasp5000.domain.audioprocessors.Reverser;
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
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException {
        AudioController controller1;
        AudioController controller2;
        if (args.length > 0) {
            try {
                controller1 = new AudioController(args[0]);
                controller2 = new AudioController(args[1]);
            } catch (UnsupportedAudioFileException | IOException ex) {
                System.out.println(ex.toString());
                return;
            }
        } else {
            return;
        }
        
        controller1.printAudioAnalysis();
        controller2.printAudioAnalysis();
        Mixer mixer = new Mixer(controller1.getAudioContainer(), 
                controller2.getAudioContainer());
        mixer.process();
        MonoAudio mix = mixer.getMix();
        AudioController mixed = new AudioController(mix);
        mixed.printAudioAnalysis();
        if (args.length > 2) {
            mixed.writeToFile(args[2]);
        }
    }
    
}
