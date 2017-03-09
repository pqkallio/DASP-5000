
package dasp5000.gui.listeners;

import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
import dasp5000.gui.AudioPanel;
import dasp5000.gui.LoudnessAnalysis;
import dasp5000.gui.Notifiable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Petri Kallio
 */
public class LoudnessAnalysisListener implements ActionListener {
    private Notifiable notifiable;
    private DynamicArray<AudioPanel> audioPanels;

    public LoudnessAnalysisListener(Notifiable notifiable, DynamicArray<AudioPanel> audioPanels) {
        this.notifiable = notifiable;
        this.audioPanels = audioPanels;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        for (int i = 0; i < audioPanels.size(); i++) {
            if (audioPanels.get(i).isSelected()) {
                AudioContainer ac = audioPanels.get(i).getAudioController().getAudioContainer();
                SpectrumAnalyzer analysis = new SpectrumAnalyzer(ac);
                long start = System.currentTimeMillis();
                analysis.process();
                System.out.println("analysis complete at " + (System.currentTimeMillis() - start) + " ms");
                new LoudnessAnalysis(analysis, audioPanels.get(i).getAudioController().getFileName());
            }
        }
    }
    
}
