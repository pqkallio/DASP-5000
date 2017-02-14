
package dasp5000.utils;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.DynamicArray;
import dasp5000.domain.WaveHeaderFields;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * This class builds a RIFF WAVE file's data as a byte array.
 * 
 * @author Petri Kallio
 */
public class RiffBuilder {
    private static final WaveHeaderFields[] STRING_FIELDS = {
        WaveHeaderFields.CHUNK_ID,
        WaveHeaderFields.FORMAT,
        WaveHeaderFields.SUBCHUNK_1_ID,
        WaveHeaderFields.SUBCHUNK_2_ID};
    
    /**
     * Creates a byte array that represents a RIFF WAVE file data.
     * 
     * @param audioContainer AudioContainer object
     * @return byte array
     */
    public static byte[] createRiffWaveFileAsByteArray(AudioContainer audioContainer) {
        int samples = audioContainer.getChannels()[0].size();
        int channels = audioContainer.getNumberOfChannels();
        int bytesPerSample = audioContainer.getBitsPerAudioSample() / 8;
        int audioDataLength = samples * channels * bytesPerSample;
        byte[] data = new byte[44 + audioDataLength];
        
        createHeader(data, audioContainer.getAudioHeader(), audioDataLength);
        encodeAudio(data, audioContainer);
        
        return data;
    }

    private static void createHeader(byte[] data, AudioHeader header, 
            int audioDataLength) {
        byte[] buffer = new byte[4];
        insertStringFieldsToData(data, STRING_FIELDS);
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.CHUNK_SIZE, 
                                 audioDataLength + 36);
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.SUBCHUNK_1_SIZE, 
                                 16);
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.AUDIO_FORMAT, 
                                 1);
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.NUM_CHANNELS, 
                                 header.getNumberOfChannels());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.NUM_CHANNELS, 
                                 header.getNumberOfChannels());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.SAMPLE_RATE, 
                                 header.getSampleRate());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.BYTE_RATE, 
                                 header.getByteRate());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.BLOCK_ALIGN, 
                                 header.getBlockAlign());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.BITS_PER_SAMPLE, 
                                 header.getBitsPerSample());
        insertIntegerFieldToData(data, buffer, 
                                 WaveHeaderFields.SUBCHUNK_2_SIZE, 
                                 audioDataLength);
    }

    private static void insertStringToData(byte[] data, 
            WaveHeaderFields headerField) {
        if (headerField.getString() == null) {
            throw new IllegalArgumentException("The WaveHeaderFields object did "
                    + "not contain a String.");
        }
        
        int dataIndex = headerField.getOffset();
        String string = headerField.getString();
        for (int i = 0; i < headerField.getByteCount(); i++) {
            data[dataIndex] = (byte)string.charAt(i);
            dataIndex++;
        }
    }

    private static void insertStringFieldsToData(byte[] data, 
            WaveHeaderFields... waveHeaderFields) {
        for (int i = 0; i < waveHeaderFields.length; i++) {
            insertStringToData(data, waveHeaderFields[i]);
        }
    }

    private static void insertIntegerFieldToData(byte[] data, byte[] buffer, 
            WaveHeaderFields headerField, int value) {
        ByteConverter.intToByteConversion(buffer, 
                                          value, 
                                          headerField.getByteCount(), 
                                          false);
        copyBufferToData(data, 
                         buffer, 
                         headerField.getOffset(), 
                         headerField.getByteCount());
    }

    private static void copyBufferToData(byte[] data, byte[] buffer, 
                                         int offset, int byteCount) {
        int dataIndex = offset;
        for (int i = 0; i < byteCount; i++) {
            data[dataIndex] = buffer[i];
            dataIndex++;
        }
    }

    private static void encodeAudio(byte[] data, AudioContainer audio) {
        int dataIndex = WaveHeaderFields.DATA.getOffset();
        int bytesPerSample = audio.getBitsPerAudioSample() / 8;
        byte[] buffer = new byte[bytesPerSample];
        DynamicArray<Integer>[] channels = audio.getChannels();
        int samplesPerChannel = channels[0].size();
        for (int i = 0; i < samplesPerChannel; i++) {
            for (int j = 0; j < audio.getNumberOfChannels(); j++) {
                ByteConverter.intToByteConversion(buffer, 
                                                  channels[j].get(i), 
                                                  bytesPerSample, 
                                                  false);
                copyBufferToData(data, buffer, dataIndex, bytesPerSample);
                dataIndex += bytesPerSample;
            }
        }
    }
}
