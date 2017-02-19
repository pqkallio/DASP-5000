
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audioprocessors.Gate;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.Notifiable;
import dasp5000.utils.DecibelConverter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Petri Kallio
 */
public class GateListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;
    private static final String THRESHOLD = "Threshold (dBFS)";
    private static final String ATTACK = "Attack (ms)";
    private static final String SUSTAIN = "Sustain (ms)";
    private static final String RELEASE = "Release (ms)";
    private JFormattedTextField thresholdField;
    private JFormattedTextField attackField;
    private JFormattedTextField sustainField;
    private JFormattedTextField releaseField;
    private JComponent[] inputs;

    public GateListener(Notifiable notifiable, 
            DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
        this.thresholdField = new JFormattedTextField(new Double(0));
        this.attackField = new JFormattedTextField(new Integer(0));
        this.sustainField = new JFormattedTextField(new Integer(0));
        this.releaseField = new JFormattedTextField(new Integer(0));
        this.inputs = new JComponent[] {new JLabel("file"),
                                        new JLabel(THRESHOLD), thresholdField,
                                        new JLabel(ATTACK), attackField,
                                        new JLabel(SUSTAIN), sustainField,
                                        new JLabel(RELEASE), releaseField};
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
                    int max = (int)Math.pow(2, ap.getAudioController().getAudioContainer().getBitsPerAudioSample());
                    double samplesPerMs = 1.0 * ap.getAudioController().getAudioContainer().getSampleRate() / 1000;
                    int thresholdSample = DecibelConverter.dBFSToSampleValue(Double.parseDouble(thresholdField.getText()), max);
                    int attackSample = (int)(samplesPerMs * Integer.parseInt(attackField.getText()));
                    int sustainSample = (int)(samplesPerMs * Integer.parseInt(sustainField.getText()));
                    int releaseSample = (int)(samplesPerMs * Integer.parseInt(releaseField.getText()));
                    Gate gate = new Gate(ap.getAudioController().getAudioContainer(), 
                            thresholdSample, attackSample, sustainSample, releaseSample);
                    gate.process();
                }
            }
        }
    }
    
}
