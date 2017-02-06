
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.audioprocessors.Mixer;
import dasp5000.domain.audioprocessors.Normalizer;
import dasp5000.domain.audioprocessors.PhaseSwitcher;
import dasp5000.domain.audioprocessors.Reverser;
import dasp5000.utils.ByteConverter;
import dasp5000.utils.RiffParser;
import java.io.File;
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
        File file = new File("/home/pqkallio/wavtest/mono.wav");
        
        URL url = ClassLoader.getSystemResource("test.wav");
        File file2 = new File(url.getPath());
        System.out.println(file2.exists());
        RiffParser parser = new RiffParser(file2);
        for (int i = 0; i < parser.getChannels().length; i++) {
            System.out.println(i + ": " + parser.getChannels()[i].size());
        }
//        AudioController controller1;
//        AudioController controller2;
//        AudioController controller3;
//        if (args.length > 0) {
//            try {
//                controller1 = new AudioController(args[0]);
//                controller2 = new AudioController(args[1]);
//                controller3 = new AudioController(args[2]);
//            } catch (UnsupportedAudioFileException | IOException ex) {
//                System.out.println(ex.toString());
//                return;
//            }
//        } else {
//            return;
//        }
//        
//        Normalizer normalizer1 = new Normalizer(controller1.getAudioContainer(), -24);
//        Normalizer normalizer2 = new Normalizer(controller2.getAudioContainer(), -24);
//        Normalizer normalizer3 = new Normalizer(controller3.getAudioContainer(), -24);
//        normalizer1.process();
//        normalizer2.process();
//        normalizer3.process();
//        controller1.printAudioAnalysis();
//        controller2.printAudioAnalysis();
//        controller3.printAudioAnalysis();
//        Mixer mixer = new Mixer(controller1.getAudioContainer(), 
//                controller2.getAudioContainer(), 
//                controller3.getAudioContainer());
//        mixer.process();
//        MonoAudio mix = mixer.getMix();
//        AudioController mixed = new AudioController(mix);
//        mixed.printAudioAnalysis();
//        if (args.length > 3) {
//            mixed.writeToFile(args[3]);
//        }
    }
    
}
