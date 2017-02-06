
package dasp5000.domain.streamhandlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class is used in opening a audio file.
 * 
 * @author pqkallio
 */
public class AudioFileHandler {
    private String fileName;
    private File inputFile;

    /**
     * Receives the path of the audio file to be opened and creates 
     * a new AudioFileHandler object.
     * 
     * @param fileName The file to be opened.
     * @throws IllegalArgumentException if the file name is not supplied
     * @throws java.io.FileNotFoundException if the file cannot be found
     */
    public AudioFileHandler(String fileName) throws IllegalArgumentException, FileNotFoundException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name not given.");
        }
        this.fileName = fileName;
        this.inputFile = new File(this.fileName);
        if (!this.inputFile.exists()) {
            throw new FileNotFoundException();
        }
    }
    
    /**
     * Get the AudioInputStream associated with the audio file.
     * 
     * @return AudioInputStream of the audio file
     * @throws UnsupportedAudioFileException if the audio format is not supported
     * @throws IOException if the AudioInputStream cannot be opened
     */
    public AudioInputStream getAudioInputStream() throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(inputFile);
        } catch (IOException | UnsupportedAudioFileException ex) {
            if (ex.getClass().equals(IOException.class)) {
                throw new IOException();
            } else {
                throw new UnsupportedAudioFileException();
            }
        }
        return audioInputStream;
    }
}
