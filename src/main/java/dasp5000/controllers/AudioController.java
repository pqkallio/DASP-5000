
package dasp5000.controllers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.AudioContainer;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.domain.streamhandlers.AudioFileHandler;
import dasp5000.utils.ByteToWordConverter;
import dasp5000.utils.DecibelConverter;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioController {
    private final String fileName;
    private final AudioContainer audioContainer;

    public AudioController(String fileName) 
            throws UnsupportedAudioFileException, IOException {
        this.fileName = fileName;
        AudioInputStream audioInputStream = openAudioInputStream(this.fileName);
        
        this.audioContainer = new AudioContainer(audioInputStream.getFormat());
        processAudioBytes(this.audioContainer, audioInputStream);
        AudioAnalysis analysis = Analyzer.analyse(this.audioContainer.getWords());
        this.audioContainer.setAudioAnalysis(analysis);
    }
    
    private AudioInputStream openAudioInputStream(String fileName) 
            throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream 
                    = new AudioFileHandler(fileName).getAudioInputStream();
        } catch (UnsupportedAudioFileException|IOException ex) {
            if (ex.getClass().equals(UnsupportedAudioFileException.class)) {
                throw new UnsupportedAudioFileException();
            } else {
                throw new IOException();
            }
        }
        return audioInputStream;
    }

    private void processAudioBytes(AudioContainer audioContainer, 
            AudioInputStream audioInputStream) {
        ByteToWordConverter converter 
                = new ByteToWordConverter(audioContainer.getBitDepth(), 
                                          audioContainer.isBigEndian());
        int numBytes = 1024;
        byte[] audioBytes = new byte[numBytes];
        try {
            int numBytesRead = 0;
            while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
                converter.insertBytesToWordArray(audioBytes, numBytesRead);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        audioContainer.setWords(converter.getWords());
    }
    
    public void printAudioAnalysis() {
        AudioAnalysis analysis = this.audioContainer.getAudioAnalysis();
        System.out.println("Minimum sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getMinimumSampleValue(), (int)Math.pow(2, this.audioContainer.getBitDepth())));
        System.out.println("Peak sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getPeakSampleValue(), (int)Math.pow(2, this.audioContainer.getBitDepth())));
        System.out.println("Audio clip length: " + 1.0 * analysis.getSamples() / this.audioContainer.getSampleRate() + " seconds");
    }
}
