
package dasp5000.controllers;

import dasp5000.domain.AudioAnalysis;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Analyzer;
import dasp5000.utils.DecibelConverter;
import dasp5000.utils.RiffBuilder;
import dasp5000.utils.RiffParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
     * @throws FileNotFoundException if the file can not be opened
     */
    public AudioController(String fileName) 
            throws UnsupportedAudioFileException, 
                   FileNotFoundException, IOException {
        this.fileName = fileName;
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        this.audioContainer = RiffParser.parseFile(file);
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

    public AudioController(File file) throws IOException, UnsupportedAudioFileException {
        this.fileName = file.getPath();
        this.audioContainer = RiffParser.parseFile(file);
        Analyzer.analyse(audioContainer);
    }
    
    /**
     * Write the data of the AudioContainer object into a audio file. The file's 
     * name into which the data is to be written is given as parameter.
     * 
     * @param outputFilePath the new audio file's path
     * @throws FileNotFoundException if the file cannot be found
     * @throws IOException if the file cannot be created
     */
    public void writeToFile(String outputFilePath) throws FileNotFoundException, IOException 
             {
        byte[] data = RiffBuilder.createRiffWaveFileAsByteArray(audioContainer);
        File outputFile = new File(outputFilePath);
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(data);
            fos.close();
        }
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
    
    public String getFileName() {
        return fileName;
    }
}
