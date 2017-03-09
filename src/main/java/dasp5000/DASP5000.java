
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.SpectrumAnalysisSample;
import dasp5000.domain.audioprocessors.Delay;
import dasp5000.domain.audioprocessors.Normalizer;
import dasp5000.domain.audioprocessors.Reverser;
import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
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
        
//        AudioController ac = new AudioController("/home/pqkallio/wavtest/sine/mixed_sine2.wav");
//        SpectrumAnalyzer sa = new SpectrumAnalyzer(ac.getAudioContainer());
//        sa.process();
//        double maxHz = 0;
//        double max = Double.NEGATIVE_INFINITY;
//        SpectrumAnalysisSample[] samples = sa.getAnalysis();
//        for (int i = 0; i < samples.length; i++) {
//            for (int j = 0; j < samples[i].getSamples().length; j++) {
//                double magnitude = samples[i].getSamples()[j].getMagnitude()[0];
//                if (magnitude > max) {
//                    maxHz = samples[i].getSamples()[j].getFrequency();
//                    max = magnitude;
//                }
//            }
//        }
//        System.out.println("max: " + maxHz + " / " + max);
//        Normalizer norm = new Normalizer(ac.getAudioContainer(), 0);
//        norm.process();
//        sa = new SpectrumAnalyzer(ac.getAudioContainer());
//        sa.process();
//        max = Double.NEGATIVE_INFINITY;
//        maxHz = 0;
//        samples = sa.getAnalysis();
//        for (int i = 0; i < samples.length; i++) {
//            for (int j = 0; j < samples[i].getSamples().length; j++) {
//                double magnitude = samples[i].getSamples()[j].getMagnitude()[0];
//                if (magnitude > max) {
//                    maxHz = samples[i].getSamples()[j].getFrequency();
//                    max = magnitude;
//                }
//            }
//        }
//        System.out.println("max: " + maxHz + " / " + max);
//        Delay dly = new Delay(ac.getAudioContainer(), 0.5, (int)(44100 * 1.5), 4);
//        dly.process();
//        ac.writeToFile("/home/pqkallio/wavtest/dly_test.wav");
    }
}
