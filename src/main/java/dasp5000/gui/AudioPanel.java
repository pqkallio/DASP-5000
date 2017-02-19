
package dasp5000.gui;

import dasp5000.controllers.AudioController;
import dasp5000.domain.Duration;
import dasp5000.domain.audiocontainers.AudioContainer;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Petri Kallio
 */
public class AudioPanel extends JPanel {
    private final AudioController controller;
    private JCheckBox select;

    public AudioPanel(AudioController controller) {
        this.controller = controller;
        compose();
    }
    
    public AudioController getAudioController() {
        return this.controller;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void compose() {
        FlowLayout layout = new FlowLayout();
        this.setLayout(layout);
        AudioContainer ac = controller.getAudioContainer();
        Duration d = new Duration(ac.getSamplesPerChannel(), ac.getSampleRate());
        this.select = new JCheckBox();
        this.add(this.select);
        this.add(new JLabel(controller.getFileName()));
        this.add(new JLabel(d.toString()));
    }

    public boolean isSelected() {
        return select.isSelected();
    }
    
    public void setSelected(boolean selected) {
        this.select.setSelected(selected);
    }
}