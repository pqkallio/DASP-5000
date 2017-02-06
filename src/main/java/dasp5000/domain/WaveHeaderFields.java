
package dasp5000.domain;

/**
 * An enumerator that remembers a RIFF WAVE file's header fields' offset, 
 * byte length and endianness.
 *
 * @author Petri Kallio
 */
public enum WaveHeaderFields {
    CHUNK_ID(0, 4, true),
    CHUNK_SIZE(4, 4, false),
    FORMAT(8, 4, true),
    SUBCHUNK_1_ID(12, 4, true),
    SUBCHUNK_1_SIZE(16, 4, false),
    AUDIO_FORMAT(20, 2, false),
    NUM_CHANNELS(22, 2, false),
    SAMPLE_RATE(24, 4, false),
    BYTE_RATE(28, 4, false),
    BLOCK_ALIGN(32, 2, false),
    BITS_PER_SAMPLE(34, 2, false),
    SUBCHUNK_2_ID(36, 4, true),
    SUBCHUNK_2_SIZE(40, 4, false),
    DATA(44, -1, false);
    
    private final int offset;
    private final int byteCount;
    private final boolean bigEndian;

    private WaveHeaderFields(int offset, int byteCount, boolean bigEndian) {
        this.offset = offset;
        this.byteCount = byteCount;
        this.bigEndian = bigEndian;
    }

    /**
     * Get the length of the field in bytes.
     * Note! If the length to obtain is WaveHeaderFields.DATA, the length of the 
     * field is dynamic and must be parsed from WaveHeaderFields.SUBCHUNK_2_SIZE 
     * field's data.
     * 
     * @return the length of the field in bytes
     */
    public int getByteCount() {
        return byteCount;
    }

    /**
     * The byte count from which the field's data begins in the file.
     * 
     * @return byte offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Get the endianness of the field's data's encoding.
     * 
     * @return true if big-endian, false if little-endian
     */
    public boolean isBigEndian() {
        return bigEndian;
    }
}
