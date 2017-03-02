
package dasp5000.gui;

import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Petri Kallio
 */
public class LoudnessAnalysis {
    private SpectrumAnalyzer analyzer;
    private JFrame frame;

    public LoudnessAnalysis(SpectrumAnalyzer analyzer, String filename) {
        this.analyzer = analyzer;
        this.frame = new JFrame(filename);
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initPanel(frame.getContentPane());
        frame.revalidate();
        frame.pack();
        frame.setVisible(true);
    }

    private void initPanel(Container contentPane) {
        contentPane.setPreferredSize(new Dimension(1000, 500));
        JPanel analysis = createAnalysisPanel();
        JScrollPane scrollPane = new JScrollPane(analysis);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createAnalysisPanel() {
        JPanel analysisPanel = new JPanel();
        BufferedImage image = new BufferedImage(
                500, 
                1000, 
                BufferedImage.TYPE_INT_RGB);
        analysisPanel.setPreferredSize(
                new Dimension(analyzer.getSamplesPerChannel(), 
                        analyzer.getAnalysis()[0].getSamples().length));
        
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                image.getGraphics().setColor(Color.red);
                image.getGraphics().drawImage(image, i, j, null);
            }
        }
        analysisPanel.add(new JLabel(new ImageIcon(image)));
        return analysisPanel;
    }
}
