
package dasp5000.controllers;

import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.domain.audioprocessors.AudioProcessor;
import dasp5000.domain.streamhandlers.AudioFileHandler;
import dasp5000.utils.DecibelConverter;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController {
    private String fileName;
    private AudioInputStream audioInputStream;

    public AudioController(String fileName) {
        this.fileName = fileName;
        try {
            this.audioInputStream = new AudioFileHandler(fileName).getAudioInputStream();
        } catch (UnsupportedAudioFileException|IOException ex) {
            System.out.println(ex.toString());
        }
        System.out.println(this.audioInputStream.getFormat());
        System.out.println(this.audioInputStream.getFrameLength());
        System.out.println(this.audioInputStream.getFormat().getChannels());
        System.out.println(this.audioInputStream.getFormat().getSampleRate());
        System.out.println(this.audioInputStream.getFormat().getSampleSizeInBits());
        System.out.println(this.audioInputStream.getFormat().getEncoding());
        System.out.println(this.audioInputStream.getFormat().isBigEndian());
    }
    
    public AudioInputStream process(String effect) throws IOException {
        int numBytes = 1024;
        byte[] audioBytes = new byte[numBytes];
        if (effect.equals("analyze")) {
            Analyzer analyzer = new Analyzer();
            try {
                int numBytesRead = 0;
                while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
                    analyzer.process(audioBytes);
                }
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            System.out.println("Min value: " + analyzer.getMinValue());
            System.out.println("Peak value: " + DecibelConverter.sampleValueToDecibels(analyzer.getPeakValue(), 63556));
            System.out.println("RMS value: " + DecibelConverter.sampleValueToDecibels(analyzer.getRMS(), 63556));
            System.out.println("Samples: " + analyzer.getSamples());
        }
        
        return audioInputStream;
    }
}
