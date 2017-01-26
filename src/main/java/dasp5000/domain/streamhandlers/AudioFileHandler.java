
package dasp5000.domain.streamhandlers;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioFileHandler {
    private String fileName;
    private File inputFile;

    /**
     * Receives the path of the audio file to be opened and creates 
     * a new AudioFileHandler object.
     * 
     * @param fileName The file to be opened.
     * @throws IllegalArgumentException 
     */
    public AudioFileHandler(String fileName) throws IllegalArgumentException {
        if (fileName == null || fileName.isEmpty()) {
            throw new IllegalArgumentException("File name not given.");
        }
        this.fileName = fileName;
        this.inputFile = new File(this.fileName);
    }
    
    /**
     * Get the AudioInputStream associated with the audio file.
     * 
     * @return
     * @throws UnsupportedAudioFileException
     * @throws IOException 
     */
    public AudioInputStream getAudioInputStream() throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(inputFile);
        } catch (IOException | UnsupportedAudioFileException ex) {
            System.out.println(ex.toString());
        }
        return audioInputStream;
    }
}
