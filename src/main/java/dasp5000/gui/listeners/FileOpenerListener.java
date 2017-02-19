
package dasp5000.gui.listeners;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DynamicArray;
import dasp5000.gui.AudioPanel;
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
public class FileOpenerListener implements ActionListener {
    private DynamicArray<AudioPanel> audioPanels;
    private Notifiable notifiable;

    public FileOpenerListener(Notifiable notifiable,
            DynamicArray<AudioPanel> audioPanels) {
        this.audioPanels = audioPanels;
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
                AudioController ac = new AudioController(file);
                audioPanels.add(new AudioPanel(ac));
                notifiable.audioPanelAdded();
            } catch (IOException ex) {
                this.notifiable.notify(ex);
            } catch (UnsupportedAudioFileException ex) {
                this.notifiable.notify(ex);
            }
        }
    }
}
