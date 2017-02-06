package dasp5000.utils;

import dasp5000.domain.DynamicArray;

/**
 * A class used to convert a audio file's data bytes into processable words.
 * N.B.! The term 'word' is loosely used here and can actually mean a byte, a word 
 * or a double word depending on the bit depth of the encoded audio data.
 * 
 * @author Petri Kallio
 */
public class ByteConverter {

    /**
     * Parses an array of bytes into a integer.
     * 
     * @param bytes the array of bytes to be parsed
     * @param bytesN the number of bytes to be parsed
     * @param bigEndian true is big-endian encoding, false if little-endian
     * @return the bytes parsed as an integer
     */
    public static int byteToIntConversion(byte[] bytes, int bytesN, 
            boolean bigEndian) {
        if (bytesN < 1) {
            throw new IllegalArgumentException("The number of bytes must be "
                    + "equal to or greater than 1");
        }
        
        if (bigEndian) {
            return parseBigEndian(bytes, bytesN);
        } else {
            return parseLittleEndian(bytes, bytesN);
        }
    }
    
    private static int parseBigEndian(byte[] bytes, int bytesN) {
        int value = bytes[0];
        if (bytesN > 1) {
            for (int i = 1; i < bytesN; i++) {
                value = (value << 8) | (bytes[i] & 0xff);
            }
        }
        return value;
    }

    private static int parseLittleEndian(byte[] bytes, int bytesN) {
        int value = bytes[bytesN - 1];
        if (bytesN > 1) {
            for (int i = bytesN - 2; i > -1; i--) {
                value = (value << 8) | (bytes[i] & 0xff);
            }
        }
        
        return value;
    }
    
    private DynamicArray<Integer> words;
    private final int bytesPerWord;
    private final boolean bigEndian;

    /**
     * Creates a new ByteToWordConverter object.
     * 
     * @param bitsPerWord The amount of bits per word
     * @param bigEndian True if bytes are encoded as big-endian, false if little-endian
     */
    public ByteConverter(int bitsPerWord, boolean bigEndian) {
        this.bytesPerWord = bitsPerWord / 8;
        this.words = new DynamicArray<>(Integer.class);
        this.bigEndian = bigEndian;
    }
    
    /**
     * Parses bytes as an integer and returns it.
     * 
     * @param bytes the bytes to be parsed
     * @return the parsed bytes as an integer
     */
    public int byteToIntegerConversion(byte[] bytes) {
        int word = bytes[0];
        for (int i = 1; i < bytesPerWord; i++) {
            word = (word << 8) | (bytes[i] & 0xff);
        }
        return word;
    }
    
    /**
     * Converts the amount of bytes given in the array to words and inserts them 
     * into a DynamicArray for later use.
     * 
     * @param bytes The bytes to be processed
     * @param byteAmount The amount of bytes to process
     */
    public void insertBytesToWordArray(byte[] bytes, int byteAmount) {
        byte[] toBeConverted = new byte[this.bytesPerWord];
        int j, k;
        for (int i = 0; i < byteAmount; i += this.bytesPerWord) {
            k = i;
            if (bigEndian) {
                j = 0;
                while (j < this.bytesPerWord) {
                    toBeConverted[j] = bytes[k];
                    j++;
                    k++;
                }
            } else {
                j = this.bytesPerWord - 1;
                while (j > -1) {
                    toBeConverted[j] = bytes[k];
                    j--;
                    k++;
                }
            }
            this.words.add(byteToIntegerConversion(toBeConverted));
        }
    }
    
    /**
     * Converts the given words into bytes and returns them as an array.
     * 
     * @return a byte array
     */
    public byte[] convertWordsToBytes() {
        byte[] bytes = new byte[words.size() * bytesPerWord];
        int byteIndex = 0;
        for (int i = 0; i < words.size(); i++) {
            byte[] wordInBytes = wordToBytes(words.get(i));
            int wordInBytesIndex = 0;
            while (wordInBytesIndex < bytesPerWord) {
                bytes[byteIndex] = wordInBytes[wordInBytesIndex];
                wordInBytesIndex++;
                byteIndex++;
            }
        }
        return bytes;
    }

    /**
     * Get the words that are composed from the converted bytes.
     * 
     * @return a DynamicArray of integers
     */
    public DynamicArray<Integer> getWords() {
        return words;
    }
    
    /**
     * Set the DynamicArray containing the words to convert to bytes.
     * 
     * @param words a DynamicArray of integers
     */
    public void setWords(DynamicArray<Integer> words) {
        this.words = words;
    }

    private byte[] wordToBytes(Integer word) {
        byte[] wordInBytes = new byte[bytesPerWord];
        int max = 0xFFFFFFFF;
        // this branch works incorrectly
        if (bigEndian) {
            for (int i = 0; i < bytesPerWord; i++) {
                wordInBytes[i] = (byte)((word >> (8 * i)) & max);
            }
        } else {
            for (int i = bytesPerWord - 1; i > -1; i--) {
                wordInBytes[i] = (byte)((word >> (8 * i)) & max);
            }
        }
        return wordInBytes;
    }
}
