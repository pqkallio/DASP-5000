
package dasp5000.domain;

public class AudioHeader {
    private String format;
    private int numberOfChannels;
    private int sampleRate;
    private int byteRate;
    private int blockAlign;
    private int bitsPerSample;
    private int dataLengthInBytes;

    public AudioHeader() {
    }

    public AudioHeader(String format, 
            int numberOfChannels, int sampleRate, int byteRate, 
            int blockAlign, int bitsPerSample, int dataLengthInBytes) {
        this.format = format;
        this.numberOfChannels = numberOfChannels;
        this.sampleRate = sampleRate;
        this.byteRate = byteRate;
        this.blockAlign = blockAlign;
        this.bitsPerSample = bitsPerSample;
        this.dataLengthInBytes = dataLengthInBytes;
    }

    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    public void setBlockAlign(int blockAlign) {
        this.blockAlign = blockAlign;
    }

    public void setByteRate(int byteRate) {
        this.byteRate = byteRate;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public void setNumberOfChannels(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public void setDataLengthInBytes(int dataLengthInBytes) {
        this.dataLengthInBytes = dataLengthInBytes;
    }

    public int getBitsPerSample() {
        return bitsPerSample;
    }

    public int getBlockAlign() {
        return blockAlign;
    }

    public int getByteRate() {
        return byteRate;
    }

    public String getFormat() {
        return format;
    }

    public int getNumberOfChannels() {
        return numberOfChannels;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public int getDataLengthInBytes() {
        return dataLengthInBytes;
    }

}
