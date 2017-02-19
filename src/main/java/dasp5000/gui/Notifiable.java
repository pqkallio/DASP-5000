
package dasp5000.gui;

import java.io.File;

/**
 *
 * @author Petri Kallio
 */
public interface Notifiable {
    public void notify(Exception ex);
    public void audioPanelAdded();
    public File getCurrentDirectory();
    public void setCurrentDirectory(File file);
}