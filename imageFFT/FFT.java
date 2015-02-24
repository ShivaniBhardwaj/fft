package imageFFT;

import java.awt.Color;

public class FFT {
	
	private int width;
	private int height;
	
	public boolean is_power_of_2(int value) {
	    return ((value & -value) == value);
	}
	
	public  Complex[][] fft2D(Complex[][] Y, boolean isInverse){
		if(isInverse){
			for(int k = 0; k < Y.length; k++){
				Y[k] = fft( Y[k]);
			}
			Complex[][] Z = transpose(Y);
			for(int n = 0; n < Z.length; n++){
				Z[n] = fft(Z[n]);
			}
			return transpose(Z);
		}
		else{
			for(int k = 0; k < Y.length; k++){
				Y[k] = ifft( Y[k]);
			}
			Complex[][] Z = transpose(Y);
			for(int n = 0; n < Z.length; n++){
				Z[n] = ifft(Z[n]);
			}
			return transpose(Z);
		}
		//return transpose(Z);
	}
	
    // compute the inverse FFT of x[], assuming its length is a power of 2
    public Complex[] ifft(Complex[] x) {
        int N = x.length;
        Complex[] y = new Complex[N];

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i] = x[i].conjugate();
        }

        // compute forward FFT
        y = fft(y);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i] = y[i].conjugate();
        }

        // divide by N
        for (int i = 0; i < N; i++) {
            y[i] = y[i].times(1.0 / N);
        }

        return y;

    }
    
	public Complex[][] transpose(Complex[][] a) {
		Complex[][] t = new Complex[a[0].length][a.length];
		for (int i = 0; i < t.length; i++) {
			for (int j = 0; j < t[0].length; j++) {
				t[i][j] = a[j][i];
			}
		}
		return t;
	}
	
	// compute the FFT of x[], assuming its length is a power of 2
    public static Complex[] fft(Complex[] x) {
        int N = x.length;

        // base case
        if (N == 1) return new Complex[] { x[0] };

        // radix 2 Cooley-Tukey FFT
        if (N % 2 != 0) { throw new RuntimeException("N is not a power of 2"); }

        // fft of even terms
        Complex[] even = new Complex[N/2];
        for (int k = 0; k < N/2; k++) {
            even[k] = x[2*k];
        }
        Complex[] q = fft(even);

        // fft of odd terms
        Complex[] odd  = even;  // reuse the array
        for (int k = 0; k < N/2; k++) {
            odd[k] = x[2*k + 1];
        }
        Complex[] r = fft(odd);

        // combine
        Complex[] y = new Complex[N];
        for (int k = 0; k < N/2; k++) {
            double kth = -2 * k * Math.PI / N;
            Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[k]       = q[k].plus(wk.times(r[k]));
            y[k + N/2] = q[k].minus(wk.times(r[k]));
        }
        return y;
    }

	public Complex[][] getComplex(Picture pic){
		
		Complex picComplex[][] = new Complex[pic.width()][pic.height()];
		
		for (int i = 0; i< pic.width(); i++){
			for(int j = 0; j<pic.height(); j++){
				Color c = pic.get(i, j);
				double intgreyColor = (c.getBlue()+c.getGreen()+c.getRed())/3;
				picComplex[i][j] = new Complex(intgreyColor,0);
			}
		}
		
		return picComplex;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
