
package dasp5000.domain.audioprocessors;


/**
 * An interface that each audio processing class must implement.
 * 
 * @author Petri Kallio
 */
public interface AudioProcessor {
    
    /**
     * Processes the audio data.
     */
    public abstract void process();
}
