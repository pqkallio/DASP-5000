
package dasp5000.utils;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.WaveHeaderFields;
import dasp5000.domain.audiocontainers.AudioContainer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A class for parsing RIFF WAVE files' data.
 * 
 * @author Petri Kallio
 */
public class RiffParser {

    /**
     * Parses a given amount of bytes and inserts them into a buffer.
     * 
     * @param buffer the buffer that the bytes are inserted to
     * @param bytes the array of bytes that the bytes are read from
     * @param byteIndex the index from which the bytes are read
     * @param byteN the number of bytes to be read
     */
    private static void parseBytes(byte[] buffer, byte[] bytes, int byteIndex, 
            int byteN) {
        for (int i = 0; i < byteN; i++) {
            buffer[i] = bytes[byteIndex];
            byteIndex++;
        }
    }
    
    /**
     * Parses the data of the RIFF WAVE (.wav-file) given as parameter.
     * 
     * @param file the file to be parsed
     * @return AudioContainer object
     * @throws IOException if the file cannot be opened
     * @throws UnsupportedAudioFileException if the file is not parsable
     */
    public static AudioContainer parseFile(File file) throws IOException, UnsupportedAudioFileException {
        AudioContainer audioContainer = new AudioContainer();
        FileInputStream fis = new FileInputStream(file);
        byte[] bytes = new byte[44];
        int bytesRead = fis.read(bytes);
        AudioHeader header = new AudioHeader();
        if (bytesRead == 44) {
            parseHeader(bytes, header);
            audioContainer.setAudioHeader(header);
        } else {
            throw new UnsupportedAudioFileException("The audio file is malformed.");
        }
        byte[] data = new byte[header.getDataLengthInBytes()];
        bytesRead = fis.read(data);
        DynamicArray<Integer>[] channels;
        if (bytesRead != -1) {
            channels = parseData(data, header);
            audioContainer.setChannels(channels);
        } else {
            throw new UnsupportedAudioFileException("The audio file's data is invalid");
        }
        return audioContainer;
    }
    
    private static String parseString(byte[] buffer, byte[] bytes, 
            int offset, int byteCount) {
        readBytes(buffer, bytes, offset, byteCount);
        String string = "";
        for (int i = 0; i < byteCount; i++) {
            string += (char)buffer[i];
        }
        return string;
    }
    
    private static int parseInt(byte[] buffer, byte[] bytes, 
            int offset, int byteCount) {
        readBytes(buffer, bytes, offset, byteCount);
        int value = ByteConverter.byteToIntConversion(buffer, byteCount, false);
        return value;
    }

    private static void parseHeader(byte[] bytes, AudioHeader header) throws UnsupportedAudioFileException {
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

    private static void readBytes(byte[] buffer, byte[] bytes, int offset, int byteCount) {
        int j = offset;
        for (int i = 0; i < byteCount; i++) {
            buffer[i] = bytes[j];
            j++;
        }
    }

    private static void checkFormat(byte[] buffer, byte[] bytes) 
            throws UnsupportedAudioFileException {
        String chunkId = parseString(buffer, bytes, 
                WaveHeaderFields.CHUNK_ID.getOffset(), 
                WaveHeaderFields.CHUNK_ID.getByteCount());
        String format = parseString(buffer, bytes, 
                WaveHeaderFields.FORMAT.getOffset(), 
                WaveHeaderFields.FORMAT.getByteCount());
        if (!chunkId.equals(WaveHeaderFields.CHUNK_ID.getString())
                || !format.equals(WaveHeaderFields.FORMAT.getString())) {
            throw new UnsupportedAudioFileException("The file was not "
                    + "recognized as a RIFF WAVE audio file.");
        }
    }

    private static void checkCompression(byte[] buffer, byte[] bytes) 
            throws UnsupportedAudioFileException {
        int value = parseInt(buffer, bytes, 
                WaveHeaderFields.AUDIO_FORMAT.getOffset(), 
                WaveHeaderFields.AUDIO_FORMAT.getByteCount());
        if (value != 1) {
            throw new UnsupportedAudioFileException("Only uncompressed "
                    + "audio supported.");
        }
    }

    private static DynamicArray<Integer>[] parseData(byte[] data, AudioHeader header) {
        DynamicArray<Integer>[] channels = initializeArrays(header);
        int bytesPerSample = header.getBitsPerSample() / 8;
        byte[] buffer = new byte[bytesPerSample];
        for (int i = 0; i < data.length; i += bytesPerSample) {
            parseBytes(buffer, data, i, bytesPerSample);
            int sample = ByteConverter.byteToIntConversion(buffer, 
                    bytesPerSample, false);
            channels[(i % (bytesPerSample * channels.length)) / bytesPerSample]
                    .add(sample);
        }
        return channels;
    }

    private static DynamicArray<Integer>[] initializeArrays(AudioHeader header) {
        DynamicArray<Integer>[] array 
                = new DynamicArray[header.getNumberOfChannels()];
        
        for (int i = 0; i < array.length; i++) {
            array[i] = new DynamicArray<>(Integer.class);
        }
        
        return array;
    }
}
