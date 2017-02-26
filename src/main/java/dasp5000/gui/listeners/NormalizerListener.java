
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audioprocessors.Gate;
import dasp5000.domain.audioprocessors.Normalizer;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import dasp5000.utils.DecibelConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Petri Kallio
 */
public class NormalizerListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;
    private static final String LEVEL = "Level (dBFS)";
    private JFormattedTextField levelField;
    private JComponent[] inputs;

    public NormalizerListener(Notifiable notifiable, DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
        this.levelField = new JFormattedTextField(new Double(0));
        this.inputs = new JComponent[] {new JLabel(LEVEL), this.levelField};
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < audioPanels.size(); i++) {
            AudioPanel ap = audioPanels.get(i);
            if (ap.isSelected()) {
                JLabel file = (JLabel)inputs[0];
                file.setText(audioPanels.get(i).getAudioController().getFileName());
                int result = JOptionPane.showConfirmDialog(null, inputs, "Normalize", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION) {
                    int max = (int)Math.pow(2, ap.getAudioController().getAudioContainer().getBitsPerAudioSample());
                    double levelSample = Double.parseDouble(levelField.getText());
                    Normalizer norm = new Normalizer(ap.getAudioController().getAudioContainer(), levelSample);
                    norm.process();
                }
            }
        }
    }    
}
