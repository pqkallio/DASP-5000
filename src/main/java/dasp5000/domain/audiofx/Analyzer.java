
package dasp5000.domain.audiofx;

import javax.sound.sampled.AudioInputStream;

public class Analyzer implements AudioProcessor {
    private long bytesRead;
    private int maxVal;
    private int minVal;
    private long sum;
    
    public Analyzer() {
        this.bytesRead = 0;
        this.maxVal = 0;
        this.minVal = 32768;
    }
    
    @Override
    public void Process(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            int val = Math.abs(bytes[i]);
            if (val > this.maxVal) {
                this.maxVal = bytes[i];
            }
            if (val < this.minVal) {
                this.minVal = bytes[i];
            }
            this.sum += val;
            this.bytesRead++;
        }
    }   
}
