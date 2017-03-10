
package dasp5000.gui;

import dasp5000.domain.LoudnessSample;
import dasp5000.domain.SpectrumAnalysisSample;
import dasp5000.domain.audioprocessors.SpectrumAnalyzer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createAnalysisPanel() {
        int momentWidth = 50;
        int heightDiv = 5;
        int div = 1;
        SpectrumAnalysisSample[] samples = analyzer.getAnalysis();
        JPanel analysisPanel = new JPanel();
        BufferedImage image = new BufferedImage(
                samples.length * momentWidth, 
                samples[0].getSamples().length / heightDiv / div, 
                BufferedImage.TYPE_INT_RGB);
        analysisPanel.setPreferredSize(
                new Dimension(samples.length * momentWidth, 
                        samples[0].getSamples().length / heightDiv / div));
        Graphics2D g = image.createGraphics();
        int imageHeight = image.getHeight();
        for (int i = 0; i < samples.length; i++) {
            for (int j = 0; j < imageHeight; j++) {
                int x = momentWidth * i;
                int y = imageHeight - j;
                double mag = getMean(samples[i], j * heightDiv, heightDiv);
                Color color = getColor(mag);
                g.setColor(color);
                g.drawLine(x, y, x + momentWidth, y);
            }
        }
        analysisPanel.add(new JLabel(new ImageIcon(image)));
        return analysisPanel;
    }

    private double getMean(SpectrumAnalysisSample sample, int start, int length) {
        double sum = 0;
        LoudnessSample[] samples = sample.getSamples();
        for (int i = 0; i < length; i++) {
            sum += samples[i + start].getMagnitude()[0];
        }
        return sum / length;
    }
    
    private Color getColor(double magnitude) {
        int red = 0;
        int green = 0;
        int blue = 0;
        if (magnitude > -96) {
            if (magnitude < -71) {
                blue = Math.max(0, 255 + (int)((magnitude + 71) / 24 * 255));
            } else if (magnitude < -47) {
                blue = 255;
                red = 255 + (int)((magnitude + 47) / 24 * 255);
            } else if (magnitude < -23) {
                red = 255;
                blue = -1 * (int)((magnitude + 23) / 24 * 255);
            } else {
                red = 255;
                green = 255 + (int)(magnitude / 24 * 255);
            }
        }
        return new Color(red, green, blue);
    }
}
