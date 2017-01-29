
package dasp5000.domain.audioprocessors;

/**
 * An interface that each audio processing class must implement.
 * 
 * @author Petri Kallio
 */
public interface AudioProcessor {
    
    /**
     * Processes the data given as parameter.
     * 
     * @param byteArray
     * @param n 
     */
    public abstract void process(byte[] byteArray, int n);
}
