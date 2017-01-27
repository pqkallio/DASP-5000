
package dasp5000.domain.audioprocessors;

public interface AudioProcessor {
    public abstract void process(byte[] byteArray, int n);
}
