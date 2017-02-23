
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.AudioHeader;
import dasp5000.domain.DFT;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.FFT;
import dasp5000.domain.LoudnessSample;
import dasp5000.domain.SpectrumAnalysisSample;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.domain.audioprocessors.Gate;
import dasp5000.domain.audioprocessors.MixerFromAbstract;
import dasp5000.domain.audioprocessors.NormalizerFromAbstract;
import dasp5000.domain.audioprocessors.PhaseSwitcherFromAbstract;
import dasp5000.domain.audioprocessors.ReverserFromAbstract;
import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
import dasp5000.gui.GraphicalUI;
import dasp5000.utils.RiffBuilder;
import java.io.File;
import java.io.IOException;
import java.util.Random;
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
//        GraphicalUI gui = new GraphicalUI();
//        SwingUtilities.invokeLater(gui);
        
        for (int audioLength = 0; audioLength <= 10000; audioLength += 1000) {
            AudioHeader header = new AudioHeader("WAVE", 1, 44100, 16, 4, 44100, audioLength);
            AudioContainer container = new AudioContainer(header);
            DynamicArray<Integer> array = new DynamicArray<>(Integer.class);
            int maxSample = (int)Math.pow(2, 16);
            for (int i = 0; i < audioLength; i++) {
                int sample = new Random().nextInt(maxSample) - (maxSample / 2); 
                array.add(sample);
            }
            container.setChannels(new DynamicArray[] {array});
            long[] times = new long[1000];
            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                Analyzer.analyse(container);
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("SAMPLES: " + audioLength);
            
            System.out.println("Analyzer avg: " + average(times));

            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                new Gate(container, -6, 100, 1000, 500).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Gate avg: " + average(times));

            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                new MixerFromAbstract(container).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Mixer avg: " + average(times));

            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                new NormalizerFromAbstract(container, -6).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Normalizer avg: " + average(times));

            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                new PhaseSwitcherFromAbstract(container).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Phase switch avg: " + average(times));

            for (int i = 0; i < 1000; i++) {
                long now = System.currentTimeMillis();
                new ReverserFromAbstract(container).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Reverser avg: " + average(times));
        }
        
        for (int audioLength = 88200; audioLength <= 88200 * 10; audioLength += 88200) {
            AudioHeader header = new AudioHeader("WAVE", 1, 44100, 16, 4, 44100, audioLength);
            AudioContainer container = new AudioContainer(header);
            DynamicArray<Integer> array = new DynamicArray<>(Integer.class);
            int maxSample = (int)Math.pow(2, 16);
            for (int i = 0; i < audioLength; i++) {
                int sample = new Random().nextInt(maxSample) - (maxSample / 2); 
                array.add(sample);
            }
            container.setChannels(new DynamicArray[] {array});
            long[] times = new long[10];
            System.out.println("SAMPLES: " + audioLength);
            for (int i = 0; i < 10; i++) {
                long now = System.currentTimeMillis();
                new SpectrumAnalyzer(container).process();
                times[i] = System.currentTimeMillis() - now;
            }

            System.out.println("Spectrum analyser avg: " + average(times));
            System.out.println("");
        }
        
//        AudioController controller = new AudioController(new File(ClassLoader.getSystemResource("pt/110.wav").getPath()));
//        SpectrumAnalyzer sa = new SpectrumAnalyzer(controller.getAudioContainer());
//        sa.process();
//        SpectrumAnalysisSample[] analysis = sa.getAnalysis();
        
//        for (int i = 0; i < analysis.length; i++) {
//            System.out.println(analysis[i].getSampleStart() + ":");
//            for (int j = 0; j < analysis[i].getSamples().length; j++) {
//                LoudnessSample ls = analysis[i].getSamples()[j];
//                System.out.print(ls.getFrequency() + "\t\t");
//                for (int k = 0; k < ls.getMagnitude().length; k++) {
//                    System.out.print(ls.getMagnitude()[k] + "\t");
//                }
//                System.out.println("");
//            }
//        }
    }

    private static double average(long[] times) {
        long sum = 0;
        for (int i = 1; i < times.length; i++) {
            sum += times[i];
        }
        
        return 1.0 * sum / (times.length - 1);
    }
}
