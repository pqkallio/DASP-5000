
package dasp5000.utils;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.WaveHeaderFields;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RiffParser {

    private static int parseBytes(byte[] buffer, byte[] bytes, int byteIndex, 
            int byteN) {
        for (int i = 0; i < byteN; i++) {
            buffer[i] = bytes[byteIndex];
            byteIndex++;
        }
        
        return byteIndex;
    }

    private static String bufferToString(byte[] buffer, int length) {
        String string = "";
        for (int i = 0; i < length; i++) {
            string += (char)buffer[i];
        }
        return string;
    }
    
    private static final String TARGET_CHUNK_ID = "RIFF";
    private static final String TARGET_FORMAT = "WAVE";
    private AudioHeader header;
    private DynamicArray<Integer>[] channels;

    public RiffParser(File file) throws IOException, UnsupportedAudioFileException {
        this.header = new AudioHeader();
        parseFile(file);
    }
    
    public void parseFile(File file) throws IOException, UnsupportedAudioFileException {
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[44];
        int bytesRead = fis.read(bytes);
        if (bytesRead == 44) {
            parseHeader(bytes);
        } else {
            throw new UnsupportedAudioFileException("The audio file is malformed.");
        }
        byte[] data = new byte[header.getDataLengthInBytes()];
        bytesRead = fis.read(data);
        if (bytesRead != -1) {
            parseData(data);
        } else {
            throw new UnsupportedAudioFileException("The audio file's data is invalid");
        }
    }
    
    private String parseString(byte[] buffer, byte[] bytes, 
            int offset, int byteCount) {
        readBytes(buffer, bytes, offset, byteCount);
        String string = "";
        for (int i = 0; i < byteCount; i++) {
            string += (char)buffer[i];
        }
        return string;
    }
    
    private int parseInt(byte[] buffer, byte[] bytes, 
            int offset, int byteCount) {
        readBytes(buffer, bytes, offset, byteCount);
        int value = ByteConverter.byteToIntConversion(buffer, byteCount, false);
        return value;
    }

    private void parseHeader(byte[] bytes) throws UnsupportedAudioFileException {
        byte[] buffer = new byte[4];
        
        checkFormat(buffer, bytes);
        checkCompression(buffer, bytes);
        
        header.setBitsPerSample(parseInt(buffer, bytes, 
                WaveHeaderFields.BITS_PER_SAMPLE.getOffset(), 
                WaveHeaderFields.BITS_PER_SAMPLE.getByteCount()));
        header.setBlockAlign(parseInt(buffer, bytes, 
                WaveHeaderFields.BLOCK_ALIGN.getOffset(), 
                WaveHeaderFields.BLOCK_ALIGN.getByteCount()));
        header.setByteRate(parseInt(buffer, bytes, 
                WaveHeaderFields.BYTE_RATE.getOffset(), 
                WaveHeaderFields.BYTE_RATE.getByteCount()));
        header.setFormat(parseString(buffer, bytes, 
                WaveHeaderFields.FORMAT.getOffset(), 
                WaveHeaderFields.FORMAT.getByteCount()));
        header.setNumberOfChannels(parseInt(buffer, bytes, 
                WaveHeaderFields.NUM_CHANNELS.getOffset(), 
                WaveHeaderFields.NUM_CHANNELS.getByteCount()));
        header.setSampleRate(parseInt(buffer, bytes, 
                WaveHeaderFields.SAMPLE_RATE.getOffset(), 
                WaveHeaderFields.SAMPLE_RATE.getByteCount()));
        header.setDataLengthInBytes(parseInt(buffer, bytes, 
                WaveHeaderFields.SUBCHUNK_2_SIZE.getOffset(), 
                WaveHeaderFields.SUBCHUNK_2_SIZE.getByteCount()));
    }

    private void readBytes(byte[] buffer, byte[] bytes, int offset, int byteCount) {
        int j = offset;
        for (int i = 0; i < byteCount; i++) {
            buffer[i] = bytes[j];
            j++;
        }
    }

    private void checkFormat(byte[] buffer, byte[] bytes) 
            throws UnsupportedAudioFileException {
        String chunkId = parseString(buffer, bytes, 
                WaveHeaderFields.CHUNK_ID.getOffset(), 
                WaveHeaderFields.CHUNK_ID.getByteCount());
        String format = parseString(buffer, bytes, 
                WaveHeaderFields.FORMAT.getOffset(), 
                WaveHeaderFields.FORMAT.getByteCount());
        if (!chunkId.equals(TARGET_CHUNK_ID) || !format.equals(TARGET_FORMAT)) {
            throw new UnsupportedAudioFileException("The file was not "
                    + "recognized as a RIFF WAVE audio file.");
        }
    }

    private void checkCompression(byte[] buffer, byte[] bytes) 
            throws UnsupportedAudioFileException {
        int value = parseInt(buffer, bytes, 
                WaveHeaderFields.AUDIO_FORMAT.getOffset(), 
                WaveHeaderFields.AUDIO_FORMAT.getByteCount());
        if (value != 1) {
            throw new UnsupportedAudioFileException("Only uncompressed "
                    + "audio supported.");
        }
    }

    private void parseData(byte[] data) {
        this.channels = initializeArrays();
        int bytesPerSample = header.getBitsPerSample() / 8;
        byte[] buffer = new byte[bytesPerSample];
        for (int i = 0; i < data.length; i += bytesPerSample) {
            parseBytes(buffer, data, i, bytesPerSample);
            int sample = ByteConverter.byteToIntConversion(buffer, 
                    bytesPerSample, false);
            channels[(i % (bytesPerSample * channels.length)) / bytesPerSample]
                    .add(sample);
        }
    }

    private DynamicArray<Integer>[] initializeArrays() {
        DynamicArray<Integer>[] array 
                = new DynamicArray[header.getNumberOfChannels()];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = new DynamicArray<>(Integer.class);
        }
        
        return array;
    }
}
