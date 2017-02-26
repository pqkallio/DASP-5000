
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audioprocessors.PhaseSwitcher;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Petri Kallio
 */
public class PhaseSwitcherListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;

    public PhaseSwitcherListener(Notifiable notifiable, DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < audioPanels.size(); i++) {
            AudioPanel ap = audioPanels.get(i);
            if (ap.isSelected()) {
                new PhaseSwitcher(audioPanels.get(i)
                        .getAudioController()
                        .getAudioContainer())
                        .process();
            }
        }
    }
}
