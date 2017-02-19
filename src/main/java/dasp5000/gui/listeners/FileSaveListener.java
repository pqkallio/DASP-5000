
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Petri Kallio
 */
public class FileSaveListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;

    public FileSaveListener(Notifiable notifiable, DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < audioPanels.size(); i++) {
            if (audioPanels.get(i).isSelected()) {
                JFileChooser fileChooser;
                File currentDirectory = notifiable.getCurrentDirectory();
                if (currentDirectory != null) {
                    fileChooser = new JFileChooser(currentDirectory);
                } else {
                    fileChooser = new JFileChooser();
                }
                FileNameExtensionFilter filter = new FileNameExtensionFilter("wav files", "wav");
                fileChooser.setFileFilter(filter);
                
                int save = fileChooser.showSaveDialog(fileChooser);
                
                if (save == JFileChooser.APPROVE_OPTION) {
                    try {
                        String fileName = fileChooser.getSelectedFile().getPath();
                        if (!fileName.endsWith(".wav")) {
                            fileName += ".wav";
                        }
                        audioPanels.get(i).getAudioController().writeToFile(fileName);
                        
                    } catch (IOException ex) {
                        notifiable.notify(ex);
                    }
                }
                audioPanels.get(i).setSelected(false);
            }
        }
    }
}
