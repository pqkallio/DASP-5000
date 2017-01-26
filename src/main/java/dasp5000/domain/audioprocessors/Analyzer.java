
package dasp5000.domain.audioprocessors;

public class Analyzer implements AudioProcessor {
    private long wordsRead;
    private int peakValue;
    private int minValue;
    private long squareSum;
    private int samples;
    
    public Analyzer() {
        this.wordsRead = 0;
        this.peakValue = 0;
        this.minValue = 32768;
        this.squareSum = 0;
        this.samples = 0;
    }
    
    @Override
    public void process(byte[] bytes) {
        byte firstVal = 0;
        for (int i = 0; i < bytes.length; i++) {
            if ((i + 1) % 2 == 0) {
                short word = twoBytesToShort(firstVal, bytes[i]);
                samples++;
                int val = Math.abs(word);
                if (val > this.peakValue) {
                    this.peakValue = val;
                }
                if (val < this.minValue) {
                    this.minValue = val;
                }
                this.squareSum += Math.pow(val, 2);
                this.wordsRead++;
            } else {
                firstVal = bytes[i];
            }
        }
    }

    public int getPeakValue() {
        return peakValue;
    }
    
    public int getMinValue() {
        return minValue;
    }
    
    public int getRMS() {
        return (int)(Math.sqrt(this.squareSum / this.wordsRead));
    }

    public int getSamples() {
        return samples;
    }

    private short twoBytesToShort(byte firstVal, byte secondVal) {
        return (short)((secondVal << 8) | (firstVal & 0xff));
    }
}
