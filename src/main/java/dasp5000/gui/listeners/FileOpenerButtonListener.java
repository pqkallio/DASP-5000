
package dasp5000.gui.listeners;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DynamicArray;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Petri Kallio
 */
public class FileOpenerButtonListener implements ActionListener {
    private DynamicArray<AudioController> controllers;
    private Notifiable notifiable;

    public FileOpenerButtonListener(Notifiable notifiable,
            DynamicArray<AudioController> controllers) {
        this.controllers = controllers;
        this.notifiable = notifiable;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        JFileChooser fileChooser;
        File currentDirectory = notifiable.getCurrentDirectory();
        if (currentDirectory != null) {
            fileChooser = new JFileChooser(currentDirectory);
        } else {
            fileChooser = new JFileChooser();
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("wav files", "wav");
        fileChooser.setFileFilter(filter);
        int open = fileChooser.showOpenDialog(fileChooser);
        
        if (open == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            notifiable.setCurrentDirectory(fileChooser.getCurrentDirectory());
            try {
                controllers.add(new AudioController(file));
                notifiable.audioControllerAdded();
            } catch (IOException ex) {
                this.notifiable.notify(ex);
            } catch (UnsupportedAudioFileException ex) {
                this.notifiable.notify(ex);
            }
        }
    }
}
