
package dasp5000.gui.listeners;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.Mixer;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Petri Kallio
 */
public class MixerListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;

    public MixerListener(Notifiable notifiable, DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        DynamicArray<AudioPanel> selected = new DynamicArray<>(AudioPanel.class);
        for (int i = 0; i < audioPanels.size(); i++) {
            AudioPanel ap = audioPanels.get(i);
            if (ap.isSelected()) {
                selected.add(ap);
            }
        }
        AudioContainer[] containers = new AudioContainer[selected.size()];
        for (int i = 0; i < selected.size(); i++) {
            containers[i] = selected.get(i).getAudioController().getAudioContainer();
        }
        
        Mixer mixer = new Mixer(containers);
        mixer.process();
        AudioContainer mix = mixer.getMix();
        AudioController controller = new AudioController(mix);
        AudioPanel panel = new AudioPanel(controller);
        audioPanels.add(panel);
        notifiable.audioPanelAdded();
    }
    
}
