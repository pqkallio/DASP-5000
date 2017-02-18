
package dasp5000.gui;

import dasp5000.controllers.AudioController;
import dasp5000.domain.Duration;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.audiocontainers.AudioContainer;
import dasp5000.gui.listeners.FileOpenerButtonListener;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author Petri Kallio
 */
public class GraphicalUI implements Runnable, Notifiable {
    private JFrame frame;
    private final int FRAME_WIDTH = 660;
    private final int FRAME_HEIGHT = 550;
    private DynamicArray<AudioController> controllers;
    private File currentDirectory;
    private JLabel test;

    public GraphicalUI() {
        this.controllers = new DynamicArray<>(AudioController.class);
        this.currentDirectory = new File(System.getProperty("user.home"));
        this.test = new JLabel("");
        if (!this.currentDirectory.exists()) {
            this.currentDirectory = null;
        }
    }
    
    @Override
    public void run() {
        this.frame = new JFrame("DASP-5000");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));;
        this.frame.setResizable(true);
        createContent(this.frame.getContentPane());
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void createContent(Container contentPane) {
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        JButton openFile = new JButton("Open");
        openFile.addActionListener(new FileOpenerButtonListener(this, this.controllers));
        contentPane.setLayout(layout);
        contentPane.add(test);
        contentPane.add(openFile);
    }

    @Override
    public void notify(Exception ex) {
        JOptionPane.showMessageDialog(frame, ex.toString());
    }

    @Override
    public File getCurrentDirectory() {
        return this.currentDirectory;
    }

    @Override
    public void setCurrentDirectory(File file) {
        this.currentDirectory = file;
    }

    @Override
    public void audioControllerAdded() {
        AudioContainer ac = controllers.get(controllers.size() - 1).getAudioContainer();
        Duration d = new Duration(ac.getSamplesPerChannel(), ac.getSampleRate());
        String label = "Container added: " + d.getHours() + " h, " 
                + d.getMinutes() + " min, " + d.getSeconds() + " sec";
        this.test.setText(label);
    }
}
