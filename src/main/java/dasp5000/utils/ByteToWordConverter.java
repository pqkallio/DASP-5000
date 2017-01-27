package dasp5000.utils;

import dasp5000.domain.DynamicArray;

public class ByteToWordConverter {
    private DynamicArray<Integer> words;
    private final int bytesPerWord;
    private final boolean bigEndian;

    public ByteToWordConverter(int bitsPerWord, boolean bigEndian) {
        this.bytesPerWord = bitsPerWord / 8;
        this.words = new DynamicArray<>(Integer.class);
        this.bigEndian = bigEndian;
    }
    
    private int byteToWordConversion(byte[] bytes) {
        int word = bytes[0];
        for (int i = 1; i < bytesPerWord; i++) {
            word = (word << 8) | (bytes[i] & 0xff);
        }
        return word;
    }
    
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
            this.words.add(byteToWordConversion(toBeConverted));
        }
    }
    
    public byte[] convertWordsToBytes()

    public DynamicArray<Integer> getWords() {
        return words;
    }
    
    public void setWords(DynamicArray<Integer> words) {
        this.words = words;
    }
}
