
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audioprocessors.Delay;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author pqkallio
 */
public class DelayListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;
    private static final String REPEAT = "Repeat";
    private static final String DECAY = "Decay (0-100%)";
    private static final String DELAY = "Delay (ms)";
    private JFormattedTextField repeatField;
    private JFormattedTextField decayField;
    private JFormattedTextField delayField;
    private JComponent[] inputs;
    
    public DelayListener(Notifiable notifiable, 
            DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
        this.repeatField = new JFormattedTextField(0);
        this.decayField = new JFormattedTextField(0);
        this.delayField = new JFormattedTextField(0);
        this.inputs = new JComponent[] {new JLabel("file"),
                                        new JLabel(REPEAT), repeatField,
                                        new JLabel(DELAY), delayField,
                                        new JLabel(DECAY), decayField};
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < audioPanels.size(); i++) {
            AudioPanel ap = audioPanels.get(i);
            if (ap.isSelected()) {
                JLabel file = (JLabel)inputs[0];
                file.setText(audioPanels.get(i).getAudioController().getFileName());
                int result = JOptionPane.showConfirmDialog(null, inputs, "Gate", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    double samplesPerMs = 1.0 * ap.getAudioController().getAudioContainer().getSampleRate() / 1000;
                    int delay = (int)(samplesPerMs * Integer.parseInt(delayField.getText()));
                    double decay = 1.0 * Integer.parseInt(decayField.getText()) / 100;
                    int repeat = Integer.parseInt(repeatField.getText());
                    Delay dly = new Delay(ap.getAudioController().getAudioContainer(), decay, delay, repeat);
                    dly.process();
                }
            }
        }
    }
    
}
