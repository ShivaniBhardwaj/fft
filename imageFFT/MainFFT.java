package imageFFT;              

import java.awt.Color;

public class MainFFT {
	
public static void main(String[] args) {
		

		Picture picSource = new Picture(512,512); 
		Picture picTarget = new Picture(512,512);
		picSource = picSource.setImage(228,228, 128,64, 225);
		picTarget = picTarget.setImage(0,0, 64,32, 225);
		picSource.show();
		picTarget.show();
		
		
		FFT fft = new FFT();
		System.out.println(fft.is_power_of_2(512));
		
		fft.setWidth(512);
		fft.setHeight(512);
		
		Complex[][] srcComplex = fft.fft2D(fft.getComplex(picSource), false); 
				
		Complex[][] srcTarget  = fft.fft2D(fft.getComplex(picTarget), false);
		
		Complex[][] proComplex = new Complex[512][512];
		int max = 0;
		for(int i = 0; i<512; i++){
			for(int j = 0; j<512; j++){
				proComplex[i][j] = srcComplex[i][j].times(srcTarget[i][j].conjugate());
							
			}
		}
		
		Complex[][] inverstFFT = fft.fft2D(proComplex, true);
		
		for(int i = 0; i<512; i++){
			for(int j = 0; j<512; j++){
				if(max< inverstFFT[i][j].re() )
					max = (int)(Math.round(inverstFFT[i][j].re()));
			}
		}
		
		System.out.println(max);
		Picture picResult = new Picture(512,512);
		for(int i = 0; i<512; i++){
			for(int j = 0; j<512; j++){
				int color = (int)(Math.round( (inverstFFT[i][j].re()/max )*255));
				System.out.print(Math.round( inverstFFT[i][j].re())+ "  ");
				if(color>255)
					System.out.print(color+ "  "+i);
				
				
				picResult.set(i, j, new Color(color, color, color));
			}
		}
		
		picResult.show();
		
		
		
		

	}

}
