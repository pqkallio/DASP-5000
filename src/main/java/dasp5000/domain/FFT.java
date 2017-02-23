
package dasp5000.domain;

/**
 *
 * @author Petri Kallio
 */
public class FFT {
    private int windowsize;
    private int m;
    private double[] cos;
    private double[] sin;
    private double window[];
    
    public FFT(int windowsize) {
        this.windowsize = windowsize;
        this.m = (int)(Math.log(windowsize) / Math.log(2));
        
        if (windowsize != (1 << m)) {
            throw new IllegalArgumentException();
        }
        
        this.cos = new double[windowsize / 2];
        this.sin = new double[windowsize / 2];
        
        for (int i = 0; i < windowsize / 2; i++) {
            cos[i] = Math.cos(-2 * Math.PI * i / windowsize);
            sin[i] = Math.sin(-2 * Math.PI * i / windowsize);
        }
        
        makeWindow();
    }

    private void makeWindow() {
        this.window = new double[this.windowsize];
        for (int i = 0; i < window.length; i++) {
            window[i] = 0.42 - 0.5 * Math.cos(2 * Math.PI * i / (windowsize - 1)) 
                    + 0.08 * Math.cos(4 * Math.PI * i / (windowsize - 1));
        }
    }

    public double[] getWindow() {
        return window;
    }
    
    public void fft(double[] re, double[] im, int from) {
        int i, j, k, n1, n2, a;
        double c, s, e, t1, t2;
        
        j = 0;
        n2 = windowsize / 2;
        for (i = 1; i < windowsize - 1; i++) {
            n1 = n2;
            while (j >= n1) {
                j = j - n1;
                n1 = n1 / 2;
            }
            j = j + n1;
            
            if (i < j) {
                t1 = re[i + from];
                re[i + from] = re[j + from];
                re[j + from] = t1;
                t1 = im[i + from];
                im[i + from] = im[j + from];
                im[j + from] = t1;
            }
        }
        
        n1 = 0;
        n2 = 1;
        
        for (i = 0; i < m; i++) {
            n1 = n2;
            n2 = n2 + n2;
            a = 0;
            
            for (j = 0; j < n1; j++) {
                c = cos[a];
                s = sin[a];
                a += 1 << (m - i - 1);
                
                for (k = j; k < windowsize; k = k + n2) {
                    t1 = c * re[k + n1 + from] - s * im[k + n1 + from];
                    t2 = s * re[k + n1 + from] + c * im[k + n1 + from];
                    re[k + n1 + from] = re[k + from] - t1;
                    im[k + n1 + from] = im[k + from] - t2;
                    re[k + from] = re[k + from] + t1;
                    im[k + from] = im[k + from] + t2;
                }
            }
        }
    }
}
