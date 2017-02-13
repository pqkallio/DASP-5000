
package dasp5000.utils;

import dasp5000.domain.AudioHeader;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * This class builds a RIFF WAVE file's data as a byte array.
 * 
 * @author Petri Kallio
 */
public class RiffBuilder {
    
    /**
     * Creates a byte array that represents a RIFF WAVE file data.
     * 
     * @param audioContainer AudioContainer object
     * @return byte array
     */
    public static byte[] createRiffWaveFileAsByteArray(AudioContainer audioContainer) {
        int audioDataLength = audioContainer.getChannels()[0].size() 
                * audioContainer.getNumberOfChannels() 
                * (audioContainer.getBitsPerAudioSample() / 8);
        byte[] data = new byte[44 + audioDataLength];
        createHeader(data, audioContainer.getAudioHeader());
        
        return data;
    }

    private static void createHeader(byte[] data, AudioHeader audioHeader) {
        int dataIndex = 0;
        byte[] buffer = new byte[4];
        String chunkId = "RIFF";
        for (int i = 0; i < chunkId.length(); i++) {
            data[dataIndex] = (byte)chunkId.charAt(i);
            dataIndex++;
        }
        ByteConverter.intToByteConversion(buffer, data.length - 8, 4, false);
        for (int i = 0; i < 4; i++) {
            data[dataIndex] = buffer[i];
            dataIndex++;
        }
    }
}
