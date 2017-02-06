
package dasp5000.controllers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audiocontainers.MonoAudio;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.domain.streamhandlers.AudioFileHandler;
import dasp5000.utils.ByteConverter;
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
     * Constructs a new AudioController object. The file of the parameter 
     * fileName is first opened and then an AudioContainer object is parsed from
     * the data of the opened audio file
     * 
     * @param fileName The name of the audio file to be opened
     * @throws UnsupportedAudioFileException if the file format is not supported
     * @throws IOException if the file can not be opened
     */
    public AudioController(String fileName) 
            throws UnsupportedAudioFileException, IOException {
        this.fileName = fileName;
        AudioInputStream audioInputStream = openAudioInputStream(this.fileName);
        
        this.audioContainer = new MonoAudio(audioInputStream.getFormat());
        processAudioBytes(this.audioContainer, audioInputStream);
        Analyzer.analyse(this.audioContainer);
    }
    
    /**
     * Create a new AudioController object. The parameter is a pre-existing 
     * AudioContainer object.
     * 
     * @param audioContainer the AudioContainer object to be controlled by this
     * controller
     */
    public AudioController(AudioContainer audioContainer) {
        this.audioContainer = audioContainer;
        this.fileName = null;
        if (this.audioContainer.getAudioAnalysis() == null) {
            Analyzer.analyse(this.audioContainer);
        }
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
        ByteConverter[] converters
                = createConverters(audioContainer);
        
        int numBytes = 1024;
        byte[] audioBytes = new byte[numBytes];
        try {
            int numBytesRead = 0;
            while ((numBytesRead = audioInputStream.read(audioBytes)) != -1) {
                converters[0].insertBytesToWordArray(audioBytes, numBytesRead);
            }
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }
        DynamicArray<Integer>[] channels = new DynamicArray[1];
        channels[0] = converters[0].getWords();
        audioContainer.setChannels(channels);
    }
    
    /**
     * Write the data of the AudioContainer object into a audio file. The file's 
     * name into which the data is to be written is given as parameter.
     * 
     * @param outputFilePath the new audio file's path
     * @throws UnsupportedAudioFileException if the AudioContainer's data is invalid
     * @throws IOException if the file cannot be created
     */
    public void writeToFile(String outputFilePath) 
            throws UnsupportedAudioFileException, IOException {
        ByteConverter converter 
                = new ByteConverter(audioContainer.getBitsPerAudioSample(), 
                                          audioContainer.isBigEndian());
        converter.setWords(audioContainer.getLeftChannel());
        byte[] wordsAsBytes = converter.convertWordsToBytes();
        ByteArrayInputStream byteArrayInputStream 
                = new ByteArrayInputStream(wordsAsBytes);
        AudioFormat audioFormat = new AudioFormat(audioContainer.getSampleRate(),
                audioContainer.getBitsPerAudioSample(),
                audioContainer.getNumberOfChannels(), true,
                audioContainer.isBigEndian());
        AudioInputStream audioInputStream 
                = new AudioInputStream(byteArrayInputStream, audioFormat, 
                        wordsAsBytes.length);
        File outputFile = new File(outputFilePath);
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outputFile);
    } 

    /**
     * Get the AudioContainer object that corresponds to the particular 
     * AudioController object.
     * 
     * @return AudioContainer object
     */
    public AudioContainer getAudioContainer() {
        return audioContainer;
    }
    
    /**
     * This method is purely for debugging purposes. It prints the minimum 
     * and peak sample values as well as the length of the audio clip.
     */
    public void printAudioAnalysis() {
        AudioAnalysis analysis = this.audioContainer.getAudioAnalysis();
        System.out.println("Minimum sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getMinimumSampleValue(), (int)Math.pow(2, this.audioContainer.getBitsPerAudioSample())));
        System.out.println("Peak sample value in dBFS: " + DecibelConverter.sampleValueToDecibels(analysis.getPeakSampleValue(), (int)Math.pow(2, this.audioContainer.getBitsPerAudioSample())));
        double time = 1.0 * analysis.getSamples() / this.audioContainer.getSampleRate();
        System.out.println("Audio clip length: " + (int)Math.floor(time / 60) + " minutes " + time % 60 + " seconds");
    }

    private ByteConverter[] createConverters(AudioContainer audioContainer) {
        ByteConverter[] converters 
                = new ByteConverter[audioContainer.getNumberOfChannels()];
        
        for (int i = 0; i < audioContainer.getNumberOfChannels(); i++) {
            converters[i] = new ByteConverter(audioContainer.getBitsPerAudioSample(), 
                                          audioContainer.isBigEndian());
        }
        
        return converters;
    }
}
