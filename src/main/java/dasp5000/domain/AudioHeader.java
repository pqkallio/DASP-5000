
package dasp5000.domain;

/**
 * This class contains the header information of an audio file
 * 
 * @author Petri Kallio
 */
public class AudioHeader {
    private String format;
    private int numberOfChannels;
    private int sampleRate;
    private int byteRate;
    private int blockAlign;
    private int bitsPerSample;
    private int dataLengthInBytes;

    /**
     * An empty constructor. Mainly to be used when you read data from a file 
     * and you want to set the values of the AudioHeader object one by one.
     * 
     */
    public AudioHeader() {
    }

    /**
     * Constructs a new AudioHeader object from the parameters. Mainly to be 
     * used when you're about to write audio data to a file and want to set 
     * all the parameters simultaneously.
     * 
     * @param format the audio format as a String (e.g. "WAVE")
     * @param numberOfChannels the number of audio channels
     * @param sampleRate the number of audio samples per second
     * @param byteRate the number of bytes per second (sample rate * (bits per sample / 8) * number of channels)
     * @param blockAlign the number of bytes per one sample for all channels (number of channels * (bits per sample / 8))
     * @param bitsPerSample the amount of bits used to save one audio sample
     * @param dataLengthInBytes the total amount of audio data
     */
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

    /**
     * Set the bit amount used to record an audio sample.
     * 
     * @param bitsPerSample bits per sample
     */
    public void setBitsPerSample(int bitsPerSample) {
        this.bitsPerSample = bitsPerSample;
    }

    /**
     * Set the amount of bytes used to record an audio sample for all channels.
     * 
     * @param blockAlign block align
     */
    public void setBlockAlign(int blockAlign) {
        this.blockAlign = blockAlign;
    }

    /**
     * Set the amount of bytes used to save one second of audio for all channels.
     * 
     * @param byteRate byte rate
     */
    public void setByteRate(int byteRate) {
        this.byteRate = byteRate;
    }

    /**
     * Set the audio format, for example "WAVE".
     * 
     * @param format the audio format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Set the number of channels that the audio data consists of.
     * 
     * @param numberOfChannels the number of channels in audio data
     */
    public void setNumberOfChannels(int numberOfChannels) {
        this.numberOfChannels = numberOfChannels;
    }

    /**
     * Set the amount of samples that a second of audio consists of.
     * 
     * @param sampleRate the sample rate
     */
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    /**
     * Set the total amount of bytes in audio data.
     * 
     * @param dataLengthInBytes the data length in bytes
     */
    public void setDataLengthInBytes(int dataLengthInBytes) {
        this.dataLengthInBytes = dataLengthInBytes;
    }

    /**
     * Get the bit amount used to record an audio sample.
     * 
     * @return the amount of bits per sample
     */
    public int getBitsPerSample() {
        return bitsPerSample;
    }

    /**
     * Get the amount of bytes used to record an audio sample for all channels.
     * 
     * @return block align
     */
    public int getBlockAlign() {
        return blockAlign;
    }

    /**
     * Get the amount of bytes used to save one second of audio for all channels.
     * 
     * @return byte rate
     */
    public int getByteRate() {
        return byteRate;
    }

    /**
     * Get the audio format, for example "WAVE".
     * 
     * @return the audio format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Get the number of channels that the audio data consists of.
     * 
     * @return the number of channels in audio data
     */
    public int getNumberOfChannels() {
        return numberOfChannels;
    }

    /**
     * Get the amount of samples that a second of audio consists of.
     * 
     * @return the sample rate
     */
    public int getSampleRate() {
        return sampleRate;
    }

    /**
     * Get the total amount of bytes in audio data.
     * 
     * @return the data length in bytes
     */
    public int getDataLengthInBytes() {
        return dataLengthInBytes;
    }

}
