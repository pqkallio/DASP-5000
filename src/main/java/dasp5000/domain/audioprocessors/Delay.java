
package dasp5000.domain.audioprocessors;

import dasp5000.domain.Queue;
import dasp5000.domain.audiocontainers.AudioContainer;

/**
 * Mixes the audio signal with the delay of the audio signal
 * 
 * @author Petri Kallio
 */
public class Delay extends AudioProcessor {
    private final Queue<Integer>[] mixingQueues;
    private final double decay;
    private final int delay;
    private final int times;
    private final int maxSample;
    private final int[] buffer;
    private final int samplesPerChannel;

    public Delay(AudioContainer audioContainer, double decay, int delay, int times) {
        super(audioContainer);
        int numChannels = audioContainer.getNumberOfChannels();
        this.samplesPerChannel = audioContainer.getSamplesPerChannel();
        this.mixingQueues = initQueues(numChannels, delay, samplesPerChannel);
        this.decay = decay;
        this.delay = delay;
        this.times = times;
        this.maxSample = (int)(Math.pow(2, audioContainer.getBitsPerAudioSample()) / 2);
        this.buffer = new int[2];
    }

    @Override
    protected void processSample(int sampleIndex, int channelIndex, int... samples) {
        this.buffer[0] = samples[0];
        this.buffer[1] = (int)(decay * mixingQueues[channelIndex].dequeue());
        int newSample = Mixer.mixSamples(buffer, maxSample);
        mixingQueues[channelIndex].enqueue(newSample);
        if (this.samplesPerChannel > sampleIndex) {
            super.audioContainers[0].getChannels()[channelIndex].replace(sampleIndex, newSample);
        } else {
            super.audioContainers[0].getChannels()[channelIndex].add(newSample);
        }
    }

    @Override
    protected void analyseAudio() {
        Analyzer.analyse(super.audioContainers[0]);
    }

    @Override
    protected int samples() {
        return delay * times + samplesPerChannel; 
    }

    private Queue<Integer>[] initQueues(int numChannels, int delay, int samplesPerChannel) {
        int queueSize = Math.max(delay, samplesPerChannel);
        Queue<Integer>[] queues = new Queue[numChannels];
        for (int i = 0; i < queues.length; i++) {
            queues[i] = new Queue(Integer.class, queueSize);
        }
        for (int i = 0; i < delay; i++) {
            for (int j = 0; j < queues.length; j++) {
                queues[j].enqueue(0);
            }
        }
        return queues;
    }
}
