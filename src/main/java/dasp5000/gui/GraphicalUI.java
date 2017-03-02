
package dasp5000.gui;

import dasp5000.controllers.AudioController;
import dasp5000.domain.DynamicArray;
import dasp5000.gui.listeners.DelayListener;
import dasp5000.gui.listeners.FileOpenerListener;
import dasp5000.gui.listeners.FileSaveListener;
import dasp5000.gui.listeners.GateListener;
import dasp5000.gui.listeners.MixerListener;
import dasp5000.gui.listeners.NormalizerListener;
import dasp5000.gui.listeners.PhaseSwitcherListener;
import dasp5000.gui.listeners.ReverserListener;
import java.awt.Container;
import java.awt.Dimension;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
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
    private DynamicArray<AudioPanel> audioPanels;
    private File currentDirectory;
    private JLabel test;

    public GraphicalUI() {
        this.controllers = new DynamicArray<>(AudioController.class);
        this.audioPanels = new DynamicArray<>(AudioPanel.class);
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
        JMenuBar menuBar = createMenuBar();
        frame.setJMenuBar(menuBar);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void createContent(Container contentPane) {
        BoxLayout layout = new BoxLayout(contentPane, BoxLayout.Y_AXIS);
        contentPane.setLayout(layout);
        contentPane.add(test);
    }

    @Override
    public void notify(Exception ex) {
        JOptionPane.showMessageDialog(frame, ex.toString(), "Error", 
                JOptionPane.ERROR_MESSAGE);
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
    public void audioPanelAdded() {
        AudioPanel ap = audioPanels.get(audioPanels.size() - 1);
        frame.add(ap);
        frame.revalidate();
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenuItem openFile = new JMenuItem("Open");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem delay = new JMenuItem("Delay");
        JMenuItem gate = new JMenuItem("Gate");
        JMenuItem reverser = new JMenuItem("Reverse");
        JMenuItem normalizer = new JMenuItem("Normalize");
        JMenuItem phaseSwitch = new JMenuItem("Phase switch");
        JMenuItem mixer = new JMenuItem("Mix");
        openFile.addActionListener(new FileOpenerListener(this, this.audioPanels));
        saveFile.addActionListener(new FileSaveListener(this, audioPanels));
        delay.addActionListener(new DelayListener(this, audioPanels));
        gate.addActionListener(new GateListener(this, audioPanels));
        reverser.addActionListener(new ReverserListener(this, audioPanels));
        normalizer.addActionListener(new NormalizerListener(this, audioPanels));
        phaseSwitch.addActionListener(new PhaseSwitcherListener(this, audioPanels));
        mixer.addActionListener(new MixerListener(this, audioPanels));
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        editMenu.add(delay);
        editMenu.add(gate);
        editMenu.add(mixer);
        editMenu.add(normalizer);
        editMenu.add(phaseSwitch);
        editMenu.add(reverser);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        return menuBar;
    }
}
