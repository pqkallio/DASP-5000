
package dasp5000.domain.audiofx;

import java.io.OutputStream;
import javax.sound.sampled.AudioInputStream;

public interface AudioProcessor {
    public abstract void Process(byte[] byteArray);
}
