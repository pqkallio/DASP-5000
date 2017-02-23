
package dasp5000;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DFT;
import dasp5000.domain.FFT;
import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
import dasp5000.gui.GraphicalUI;
import java.io.File;
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
//        GraphicalUI gui = new GraphicalUI();
//        SwingUtilities.invokeLater(gui);
        AudioController controller = new AudioController(new File("/home/pqkallio/wavtest/sine/mixed_sine2.wav"));
        System.out.println("channels: " + controller.getAudioContainer().getNumberOfChannels());
        SpectrumAnalyzer sa = new SpectrumAnalyzer(controller.getAudioContainer());
        sa.process();
        double[][] re = sa.getChannelData();
        double[][] im = sa.getImaginaryData();
        for (int i = 0; i < re[0].length; i += sa.getWindowSize()) {
            System.out.println("\n*****\n*****\n" + i + "\n*****\n*****\n");
            for (int j = i; j < i + sa.getWindowSize() / 2; j++) {
                for (int k = 0; k < re.length; k++) {
                    double freq = ((j - i) * 44100 / 2) / (sa.getWindowSize() / 2);
                    double magnitude = Math.sqrt(re[k][j] * re[k][j] + im[k][j] * im[k][j]);
                    if (magnitude > 5000) {
                        if (k == 0) {
                            System.out.print("L: ");
                        } else {
                            System.out.print("R: ");
                        }
                        System.out.println(freq + "\t" + magnitude);
                    }
                }
            }
        }
//        double[][] analysis = DFT.transform(samples);
//        for (int i = 0; i < analysis[0].length; i++) {
//            if (analysis[1][i] > -30) {
//                System.out.println(analysis[0][i] + " / " + analysis[1][i] + " / " + analysis[2][i]);
//            }
//        }

//        int N = (int)Math.pow(2, 15);
//        FFT fft = new FFT(N);
//        fft.fft(samples, empty);
//        for (int i = 0; i < samples.length / 2; i++) {
//            double magnitude = Math.sqrt(samples[i] * samples[i] + empty[i] * empty[i]);
//            if (magnitude > 100) {
//                System.out.println(((i * 44100 / 2) / (N / 2)) + " / " + samples[i] + " / " + empty[i] + " / " + magnitude);
//            }
//        }
//        
//        double[] window = fft.getWindow();
//        double[] re = new double[N];
//        double[] im = new double[N];
//        
//        re[0] = 1;
//        im[0] = 0;
//        
//        for (int i = 1; i < N; i++) {
//            re[i] = im[i] = 0;
//        }
//        beforeAfter(fft, re, im);
//        
//        for (int i = 0; i < N; i++) {
//            re[i] = Math.pow(-1, i);
//            im[i] = 0;
//        }
//        beforeAfter(fft, re, im);
//        
//        for (int i = 0; i < N; i++) {
//            re[i] = Math.cos(2 * Math.PI * i / N);
//            im[i] = 0;
//        }
//        beforeAfter(fft, re, im);
//        
//        for (int i = 0; i < N; i++) {
//            re[i] = i;
//            im[i] = 0;
//        }
//        beforeAfter(fft, re, im);
//        
//        long time = System.currentTimeMillis();
//        double iter = 30000;
//        
//        for (int i = 0; i < iter; i++) {
//            fft.fft(re, im);
//        }
//        
//        time = System.currentTimeMillis() - time;
//        System.out.println("Averaged " + (time / iter) + " ms per iteration");
//    }
//
//    private static void beforeAfter(FFT fft, double[] re, double[] im) {
//        System.out.println("Before: ");
//        printReIm(re, im);
//        fft.fft(re, im);
//        System.out.println("After: ");
//        printReIm(re, im);
//    }
//
//    private static void printReIm(double[] re, double[] im) {
//        System.out.print("Re: [");
//        for (int i = 0; i < re.length; i++) {
//            System.out.print(((int)(re[i] * 1000 / 1000.0)) + " ");
//        }
//        System.out.print("]\nIm: [");
//        for (int i = 0; i < im.length; i++) {
//            System.out.print(((int)(im[i] * 1000 / 1000.0)) + " ");
//        }
//        System.out.println("]");
//    }

    }
}
