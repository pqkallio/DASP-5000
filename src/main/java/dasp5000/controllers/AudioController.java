
package dasp5000.controllers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.AudioContainer;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.domain.streamhandlers.AudioFileHandler;
import dasp5000.utils.ByteToWordConverter;
import dasp5000.utils.DecibelConverter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class to handle a single audio clip's life cycle.
 * @author Petri Kallio
 */
public class AudioController {
    private final String fileName;
    private final AudioContainer audioContainer;

    /**
     * Constructs a new AudioController object.
     * @param fileName The name of the audio file to be opened
     * @throws UnsupportedAudioFileException
     * @throws IOException 
     */
    public AudioController(String fileName) 
            throws UnsupportedAudioFileException, IOException {
        this.fileName = fileName;
        AudioInputStream audioInputStream = openAudioInputStream(this.fileName);
        
        this.audioContainer = new AudioContainer(audioInputStream.getFormat());
        processAudioBytes(this.audioContainer, audioInputStream);
        System.out.println(audioContainer.getWords());
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
    
    public void writeToFile(String outputFilePath) 
            throws UnsupportedAudioFileException, IOException {
        ByteToWordConverter converter 
                = new ByteToWordConverter(audioContainer.getBitDepth(), 
                                          audioContainer.isBigEndian());
        converter.setWords(audioContainer.getWords());
        byte[] wordsAsBytes = converter.convertWordsToBytes();
        ByteArrayInputStream byteArrayInputStream 
                = new ByteArrayInputStream(wordsAsBytes);
        AudioFormat audioFormat = new AudioFormat(audioContainer.getSampleRate(),
                audioContainer.getBitDepth(),
                audioContainer.getNumberOfChannels(), true,
                audioContainer.isBigEndian());
        AudioInputStream audioInputStream 
                = new AudioInputStream(byteArrayInputStream, audioFormat, 
                        wordsAsBytes.length);
        File outputFile = new File(outputFilePath);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
    } 
    
    /**
     * This method is purely for debugging purposes. It prints the minimum 
     * and peak sample values as well as the length of the audio clip.
     */
    public void printAudioAnalysis() {
        AudioAnalysis analysis = this.audioContainer.getAudioAnalysis();
        System.out.println("Minimum sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getMinimumSampleValue(), (int)Math.pow(2, this.audioContainer.getBitDepth())));
        System.out.println("Peak sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getPeakSampleValue(), (int)Math.pow(2, this.audioContainer.getBitDepth())));
        double time = 1.0 * analysis.getSamples() / this.audioContainer.getSampleRate();
        System.out.println("Audio clip length: " + (int)Math.floor(time / 60) + " minutes " + time % 60 + " seconds");
    }
}
