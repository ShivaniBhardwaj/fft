package imageFFT;


import java.awt.Color;
import java.awt.image.BufferedImage;

public class Filter {
	
	//gray scale
	public Picture colorToGrey(Picture pic){
		
		for(int j = 0 ; j< pic.width(); j = j+1)
		{
			for(int i = 0; i< pic.height(); i++)
			{	
				Color col = pic.get(j, i);  
				int r = col.getRed();
				int g = col.getGreen();
				int b = col.getBlue();
				int grey = (r+g+b)/3;
				Color c = new Color(grey,grey,grey); 
				pic.set(j, i, c);
			}
		}
		
		return pic;
	}
	
	//Negative
	public Picture PictureNegative(Picture pic){
		
		int w1 = pic.width();
	    int h1 = pic.height();
	    // int value[][] = new int[w1][h1];
	    //BufferedImage gray = new BufferedImage(w1, h1, 1);
	    int  alpha, r, g, b;
	    Color value;
	    for (int i = 0; i < w1; i++) {
	        for (int j = 0; j < h1; j++) {
	            value = pic.get(i, j); // store value
	            alpha = value.getAlpha();
	            r = 255 - value.getRed();
	            g = 255 - value.getGreen();
	            b = 255 - value.getBlue();

	            value = new Color(r, g, b/*, alpha*/);
	            pic.set(i, j, value);
	            //gray.setRGB(i, j, value);
	        }
	    }
	    return pic;
		}
	
	    public Picture processSharpen(Picture p)
	    {
	    	int [][] kernel = {
					{0,-1,0},
					{-1,5,-1},
					{0,-1,0}
				   };
	    	
	    	int height = p.height();
	    	int width = p.width();
	    	int red, green, blue,  x, y;
	    	Picture image = new Picture(width, height);
	    	System.out.println(width+ "  "+ height);
	    	int w = width-2;
	    	int h = height-2;
	    	for(int i = 1; i < w ; i++)
	    	{
	    		for(int j = 1; j < h ; j++)
	    		{	red = 0;
	    			green = 0;
	    			blue = 0;
	    			x = 0;
	    			y = 0;
	    			for(int l = 0; l < kernel.length; l++)
	    			{
	    				for(int m = 0; m < kernel[0].length; m++)
	    				{
	    					if(l == 0)
	    						x = i - 1;
	    					else if(l == 1)
	    						x = i;
	    					else if(l == 2)
	    						x = i + 1;
	    					if(m == 0)
	    						y = j - 1;
	    					else if(m == 1)
	    						y = j;
	    					else if(m == 2)
	    						y = j + 1;
	    					red+=(kernel[l][m] * p.get(x, y).getRed());
	    					green+=(kernel[l][m] * p.get(x, y).getGreen());
	    					blue+=(kernel[l][m] * p.get(x, y).getBlue());
	    					
	    				}
	    			}
	    			if(red>255){
	    				//System.out.print(red);
	    				red=255;
	    				
	    			}
	    			if(green>255){
	    				//System.out.print(green);
	    				green=255;
	    				
	    			}
	    			if(blue>255){
	    				//System.out.print(blue);
	    				blue=255;
	    			}
	    			if(red<0){
	    				//System.out.print(red);
	    				red=0;
	    			}
	    			if(green<0){
	    				//System.out.print(green);
	    				green=0;
	    			}
	    			if(blue<0){
	    				//System.out.print(blue);
	    				blue=0;
	    			}
	    			
	    			try{
	    			image.set(i, j, new Color(red, green, blue));
	    			}catch(Exception e){
	    				System.out.println(e);
	    			}
	    		}
	    	}
	    	return image;
	    }
	    public void overLay(Picture p1, Picture p2){
			  
			  /*Picture p1 = new Picture("egyptian lotus flower.jpg");
			  Picture p2 = new Picture("index.jpg");*/
			  
			  // create the new image, canvas size is the max. of both image sizes
		    	int w = Math.max(p1.width(), p2.width());
		    	int h = Math.max(p1.height(),p2.height());
		    
		    	int w1 = p1.width();
		    	int h1 = p1.height();
//		    	System.out.println(h1+"  "+w1);
		    	
		    	Picture combined = new Picture(w,h);//, BufferedImage.TYPE_INT_ARGB);
		    	p2.show();
		    	int w2= p2.width();
		    	int h2 = p2.height();
		    	 int alphaa =0 ;
		    	 int r =0;
		    	 int g=0;
		    	 int b=0;
		    	 System.out.println(w1+"  "+h1);
		    	 System.out.println(w+"  "+h);
		    	for(int i =1; i<w1-1;i++){
		    		for(int j =1; j<h1-1; j++){
		    			if(w2>i && h2>j){
		    				//System.out.println(i+"  "+j);
		    				r = (p1.get(i, j).getRed() + p2.get(i, j).getRed()) >> 1;
	                        g = (p1.get(i, j).getGreen() + p2.get(i, j).getGreen()) >> 1;
	                        b = (p1.get(i, j).getBlue() + p2.get(i, j).getBlue()) >> 1;
	                        alphaa = 255;//Math.min(255, p1.get(i, j).getAlpha() + p2.get(i, j).getAlpha()  - (p1.get(i, j).getAlpha()  * p2.get(i, j).getAlpha() ) / 255);
	                        System.out.println(alphaa);
		    			
		    			//Color c = p2.get(i, j);
		    			combined.set(i, j, new Color(p1.get(i, j).getRed() ,p1.get(i, j).getGreen(),p1.get(i, j).getBlue()));
		    			combined.set(i, j, new Color(p2.get(i, j).getRed() ,p2.get(i, j).getGreen(),p2.get(i, j).getBlue()));
		    			}
		    			else
		    				combined.set(i, j, new Color(p1.get(i, j).getRed() ,p1.get(i, j).getGreen(),p1.get(i, j).getBlue()));
		    		}
		    	}
		    	combined.show();
		    	
		    }
	    public void composite(Picture p1, Picture p2){
			  
			  /*Picture p1 = new Picture("egyptian lotus flower.jpg");
			  Picture p2 = new Picture("index.jpg");*/
			  
			  // create the new image, canvas size is the max. of both image sizes
		    	int w = Math.max(p1.width(), p2.width());
		    	int h = Math.max(p1.height(),p2.height());
		    
		    	int w1 = p1.width();
		    	int h1 = p1.height();
//		    	System.out.println(h1+"  "+w1);
		    	
		    	Picture combined = new Picture(w,h);//, BufferedImage.TYPE_INT_ARGB);
		    	p2.show();
		    	int w2= p2.width();
		    	int h2 = p2.height();
		    	 int alphaa =0 ;
		    	 int r =0;
		    	 int g=0;
		    	 int b=0;
		    	 System.out.println(w1+"  "+h1);
		    	 System.out.println(w+"  "+h);
		    	for(int i =1; i<w1-1;i++){
		    		for(int j =1; j<h1-1; j++){
		    			if(w2>i && h2>j){
		    				//System.out.println(i+"  "+j);
		    				r = (p1.get(i, j).getRed() + p2.get(i, j).getRed()) >> 1;
	                        g = (p1.get(i, j).getGreen() + p2.get(i, j).getGreen()) >> 1;
	                        b = (p1.get(i, j).getBlue() + p2.get(i, j).getBlue()) >> 1;
	                        alphaa = 255;//Math.min(255, p1.get(i, j).getAlpha() + p2.get(i, j).getAlpha()  - (p1.get(i, j).getAlpha()  * p2.get(i, j).getAlpha() ) / 255);
	                        System.out.println(alphaa);
		    			
		    			//Color c = p2.get(i, j);
		    			combined.set(i, j, new Color(r,g,b,alphaa));
		    			}
		    		}
		    	}
		    	combined.show();
		    	
		    }
	    public Picture Smoothen(Picture pic ) {
	    	//BufferedImage ret = cloneImage(image);
	    	int height = pic.height();
	    	int width = pic.width();
	    	for (int x = 0; x < width; x++) {
	    	    for (int y = 0; y < height; y++) {
	    		Color level = new Color(255,255,255);
	    		if ((x > 0) && (x < (width - 1)) && (y > 0) && (y < (height - 1))) {
	    		    //int sumX = 0;
	    		    int sumY = 0;
	    		    int r= 0;
	    		    int g =0;
	    		    int b =0;
	    		    for (int i = -1; i < 2; i++) {
		    			for (int j = -1; j < 2; j++) {
		    			    //sumX += (image.getRGB(x+i, y+j));
		    			     Color c = pic.get(x+i, y+j);//int rgb =image.getRGB(x+i, y+j));
		    			     r += c.getRed();
		    			     g += c.getGreen();
		    			     b += c.getBlue(); 
		    			  
		    			     sumY++;
		    			    //System.out.println(sumX + " " + sumY);
		    			}
	    		    }
	    		    
	    		    r = r/sumY;
	    		    if (r < 0) {
	        			r = 0;
	        			//System.out.print("r <0");
	        			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	        		    } else if (r > 255) {
	        			r = 255;
	        			//System.out.print("r > 255");
	        			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	        		    }
	        		    //r = 255 - r;
	        		   g = g/sumY;
	        		if (g < 0) {
	        			//System.out.print("g < 0");
	        			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	            			g = 0;
	            		    } else if (g > 255) {
	            			g = 255;
	            			//System.out.print("g > 255");
	            			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	            		    }
	            		   // g = 255 - g;
	            
	            		    b =b/sumY;
	                if (b < 0) {
	                			b = 0;
	                			//System.out.print("b =0");
	                			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	                		    } else if (b > 255) {
	                			b = 255;
	                			//System.out.print("b > 255");
	                			//System.out.println(r+ " " + g + " " + b + " " + sumY);
	                		    }
	                		   // b = 255 - r;
	    		    level = new Color( r,g,b);
		    		//ret.setRGB(x, y, (level));;//sumX/sumY;//Math.abs(sumX) + Math.abs(sumY);
	    		  //level = 0x00000000 | b<<3  ;

	    		}
	    		pic.set(x, y, level) ;
	    	    }
	    	}
	    	return pic;
	       }

	    
	       private static int[][] sobelx = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
	       private static int[][] sobely = {{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};

	    public  int lum(int r, int g, int b) {
	    	//int px = (r + r + r + b + g + g + g + g) >> 3;
		//return new Color((px >> 16) & 0xFF, (px >> 8) & 0xFF, px & 0xFF);
	    	return (r + r + r + b + g + g + g + g) >> 3;
	    }

	    public  int rgb_to_luminance(Color rgb) {
		int r = rgb.getRed();
		int g = rgb.getGreen();
		int b = rgb.getBlue();
		//System.out.println(r + ", " + g + ", " + b);
		return lum(r, g, b);
	    }

	    public  Color level_to_greyscale(int level) {
		int px =  (level << 16) | (level << 8) | level;
		return new Color((px >> 16) & 0xFF, (px >> 8) & 0xFF, px & 0xFF);
	    }

	    public Picture sobelEdgeDetection(Picture picOrg) {
	   	Picture pic = new Picture(picOrg.width(), picOrg.height());
		//BufferedImage ret = cloneImage(image);
		int width = pic.width();
		int height = pic.height();
		for (int x = 0; x < width; x++) {
		    for (int y = 0; y < height; y++) {
			int level = 255;
			if ((x > 0) && (x < (width - 1)) && (y > 0) && (y < (height - 1))) {
			    int sumX = 0;
			    int sumY = 0;
			    for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
				    sumX += rgb_to_luminance(picOrg.get(x+i, y+j)) * sobelx[i+1][j+1];
				    sumY += rgb_to_luminance(picOrg.get(x+i, y+j)) * sobely[i+1][j+1];
				    //System.out.println(sumX + " " + sumY);
				}
			    }
			    level = Math.abs(sumX) + Math.abs(sumY);
			    if (level < 0) {
				level = 0;
			    } else if (level > 255) {
				level = 255;
			    }
			    level = 255 - level;
			}
			pic.set(x, y, level_to_greyscale(level));
			//ret.
		    }
		}
		//show();
		
		return pic;
	    }

	    
}
