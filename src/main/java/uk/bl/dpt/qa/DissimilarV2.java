/*
 * Copyright 2013 The British Library/SCAPE Project Consortium
 * Author: William Palmer (William.Palmer@bl.uk)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package uk.bl.dpt.qa;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.Variance;

/*
 * NOTE: this currently uses a snapshot of commons-imaging to load tiff files
 * When using net.java.dev.jai-imageio:jai-imageio-core-standalone:1.2-pre-dr-b04-2013-04-23 
 * the tiff files aren't loaded properly - psnr calcs are different from imagemagick/commons-imaging 
 * loaded tiffs. 
 */

/**
 * Program/library to calculate SSIM and PSNR values between two images  
 * @author wpalmer
 */
public class DissimilarV2 {

	/**
	 * Default SSIM window size (8)
	 */
	public final static int SSIMWINDOWSIZE = 8;

	/**
	 * Version string (loaded from Maven properties in jar)
	 */
	private static String version = "NOVERSION";
	
	/**
	 * Calculate the SSIM value on a window of pixels
	 * @param pLumaOne luma values for first image
	 * @param pLumaTwo luma values for second image
	 * @param pWidth width of the two images
	 * @param pHeight height of the two images
	 * @param pWindowSize window size
	 * @param pStartX start x coordinate for the window
	 * @param pStartY start y coordinate for the window
	 * @return SSIM for the window
	 */
	private static double calcSSIMOnWindow(final double[] pLumaOne, final double[] pLumaTwo, final int pWidth,
			final int pHeight, final int pWindowSize, final int pStartX, final int pStartY) {
		
		final double k1 = 0.01;
		final double k2 = 0.03;
		final double L = Math.pow(2, 8)-1;//255 (at least for the moment all pixel values are 8-bit)
		final double c1 = Math.pow(k1*L, 2); 
		final double c2 = Math.pow(k2*L, 2); 

		final int windowWidth = ((pWindowSize+pStartX)>pWidth)?(pWidth-pStartX):(pWindowSize);
		final int windowHeight = ((pWindowSize+pStartY)>pHeight)?(pHeight-pStartY):(pWindowSize);
		
		if(windowWidth<=0||windowHeight<=0) {
			System.out.println(pWidth+" "+pStartX+" "+windowWidth+" "+windowHeight);
			System.out.println(pHeight+" "+pStartY+" "+windowWidth+" "+windowHeight);
		}
		
		//store a temporary copy of the pixels
		double[] pixelsOne = new double[windowWidth*windowHeight];
		double[] pixelsTwo = new double[windowWidth*windowHeight];
		
		for(int h=0;h<windowHeight;h++) {
			for(int w=0;w<windowWidth;w++) {
				pixelsOne[(h*windowWidth)+w] = pLumaOne[(pStartY+h)*pWidth+pStartX+w];
				pixelsTwo[(h*windowWidth)+w] = pLumaTwo[(pStartY+h)*pWidth+pStartX+w];
			}
		}		
		
		final double ux = new Mean().evaluate(pixelsOne, 0, pixelsOne.length);
		final double uy = new Mean().evaluate(pixelsTwo, 0, pixelsTwo.length);
		final double o2x = new Variance().evaluate(pixelsOne);
		final double o2y = new Variance().evaluate(pixelsTwo);
		final double oxy = new Covariance().covariance(pixelsOne, pixelsTwo);
		
		final double num = (2*ux*uy+c1)*(2*oxy+c2);
		final double den = (ux*ux+uy*uy+c1)*(o2x+o2y+c2);
		
		final double ssim = num/den;
		
		return ssim;
	}
	
	/**
	 * Calculate the SSIM; see http://en.wikipedia.org/wiki/Structural_similarity
	 * @param pOne array of integer pixel values for first image
	 * @param pTwo array of integer pixel values for second image
	 * @param pWidth width of the two images
	 * @param pHeight height of the two images
	 * @param pGreyscale if the images are greyscale
	 * @param pHeatMapFilename filename for the ssim heatmap image to be saved to (png)
	 * @param pMin list to hold return value for ssim-minimum
	 * @param pVariance list to hold return value for ssim-variance
	 * @return SSIM
	 */
	public static double calcSSIM(final int[] pOne, final int[] pTwo, final int pWidth, final int pHeight, final boolean pGreyscale, 
									final String pHeatMapFilename, List<Double> pMin, List<Double> pVariance) {

		if(!checkPair(pOne, pTwo)) return -1;

		double[] lumaOne = null; 
		double[] lumaTwo = null; 
		
		//if the image is greyscale then don't extract the luma
		if(pGreyscale) {
			System.out.println("=> Greyscale");
			lumaOne = new double[pOne.length];
			lumaTwo = new double[pTwo.length];
			for(int i=0;i<lumaOne.length;i++) {
				//all rgb values are the same
				lumaOne[i] = (pOne[i] >> 0 ) & 0xFF;
				lumaTwo[i] = (pTwo[i] >> 0 ) & 0xFF;
			}
		} else {
			lumaOne = calcLuma(pOne);
			lumaTwo = calcLuma(pTwo);			
		}

		final int windowSize = SSIMWINDOWSIZE;
		
		final int windowsH = (int)Math.ceil((double)pHeight/windowSize);
		final int windowsW = (int)Math.ceil((double)pWidth/windowSize);
		
		double[] mssim = new double[windowsH*windowsW]; 

		double mean = 0;
		double min = 1;
		
		for(int height=0;height<windowsH;height++) {
			for(int width=0;width<windowsW;width++) {
				final int window = (height*windowsW)+width; 
				mssim[window] = calcSSIMOnWindow(lumaOne, lumaTwo, pWidth, pHeight, windowSize, width*windowSize, height*windowSize);
				mean += mssim[window];
				if(mssim[window]<min) {
					min = mssim[window];
				}
			}
		}

		final double variance = new Variance().evaluate(mssim);
		
		mean /= (windowsH*windowsW);

		//if(variance>0.001) System.out.println("warning: high variance");
		
		if(null!=pHeatMapFilename) {
			dumpSSIMHeatMap(mssim, windowsH, windowsW, pHeatMapFilename);
		}
		
		if(null!=pMin) {
			pMin.add(0, new Double(min));
		}
		if(null!=pVariance) {
			pVariance.add(0, new Double(variance));
		}
		
		return mean;
	}

	/**
	 * Write an image showing the heatmap of ssim values, per window
	 * @param pValues sequence of SSIM values
	 * @param pHeight number of SSIM windows across
	 * @param pWidth number of SSIM windows down
	 * @param pFilename filename to save the image to (png)
	 */
	private static BufferedImage dumpSSIMHeatMap(final double[] pValues, final int pHeight, final int pWidth, final String pFilename) {
		BufferedImage heatMap = new BufferedImage(pWidth, pHeight, BufferedImage.TYPE_USHORT_GRAY);
		
		final int maxPixelValue = (int)Math.pow(2, heatMap.getColorModel().getPixelSize())-1;
		
		int pixel = 0;
		for(int height=0;height<pHeight;height++) {
			for(int width=0;width<pWidth;width++) {	
				pixel = ((int)(maxPixelValue * pValues[(height*pWidth)+width]));
				if(pixel<0) {
					pixel = 0;
				}
				heatMap.setRGB(width, height, pixel);
			}
		}
		
		//only write to file if filename is non-null
		if(pFilename!=null) {
			try {
				ImageIO.write(heatMap, "png", new File(pFilename));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		return heatMap;
	}

	/**
	 * Calculate the luma values for an image; see http://en.wikipedia.org/wiki/Luma_%28video%29
	 * @param pOne pixels to calculate luma for
	 * @return array of luma values for input pixels
	 */
	private static double[] calcLuma(final int[] pOne) {
		
		double[] ret = new double[pOne.length];
		
		int blue  = 0;
		int green = 0;
		int red   = 0;
		for(int i=0;i<ret.length;i++) {
			blue  = (pOne[i] >> 0 ) & 0xFF;
			green = (pOne[i] >> 8 ) & 0xFF;
			red   = (pOne[i] >> 16) & 0xFF;
			ret[i] = (0.2126*red+0.7152*green+0.0722*blue);
		}
		
		return ret;
	}

	/**
	 * Calculate the mean-squared-error between two images
	 * @param pOne first image to compare
	 * @param pTwo second image to compare
	 * @param pGreyscale whether the images are greyscale or not
	 * @return mean squared error
	 */
	private static double calcMSE(final int[] pOne, final int[] pTwo, final boolean pGreyscale) {
		
		//getRGB only returns 8 bits per component, so what about 16-bit images? 

		if(pOne.length!=pTwo.length) return -1;
		
		int channels = 3;
		if(pGreyscale) channels = 1;
		
		//FIXME: change to BigDecimal?
		double sumSquaredErrors = 0;
		
		int oneBlue  = 0;
		int oneGreen = 0;
		int oneRed   = 0;
		int twoBlue  = 0;
		int twoGreen = 0;
		int twoRed   = 0;
		
		for(int i=0;i<pOne.length;i++) {
			//see javadoc for Color class
			oneBlue  = (pOne[i] >> 0 ) & 0xFF;
			twoBlue  = (pTwo[i] >> 0 ) & 0xFF;
			//just use one channel - rgb should all be the same here (briefly tested)
			sumSquaredErrors += Math.pow((oneBlue-twoBlue),2);	
			if(!pGreyscale) {
				oneGreen = (pOne[i] >> 8 ) & 0xFF;
				oneRed   = (pOne[i] >> 16) & 0xFF;
				twoGreen = (pTwo[i] >> 8 ) & 0xFF;
				twoRed   = (pTwo[i] >> 16) & 0xFF;
				sumSquaredErrors += /*Math.pow((oneBlue-twoBlue),2)+*/Math.pow((oneGreen-twoGreen),2)+Math.pow((oneRed-twoRed),2);
			} else {
			
			}
		}
		
		//System.out.println("SSE: "+sumSquaredErrors);
		
		final double meanSquaredError = sumSquaredErrors/(pOne.length*channels);
		
		return meanSquaredError;
	}
	
	/**
	 * Check to see if the two images can be compared (in an image format migration sense i.e. they are the same size)
	 * The current tests here do not take in to account dimensions etc.
	 * @param pOne first image to check
	 * @param pTwo second image to check
	 * @return whether the images should be compared or not
	 */
	private static boolean checkPair(final int[] pOne, final int[] pTwo) {

		if(pOne.length!=pTwo.length) return false;
		
		//more checks?
		
		return true;
	}
	
	/**
	 * Print an error if one of the input files cannot be opened
	 * @param pOne first image file
	 * @param pReadOne if the file can be read or not
	 * @param pTriedTwo whether an attempt to open the second file has been made
	 * @param pTwo second image file
	 * @param pReadTwo if the file can be read or not
	 */
	private static void printError(final File pOne, final boolean pReadOne, final boolean pTriedTwo, final File pTwo, final boolean pReadTwo) {
		System.out.println("<dissimilar version=\""+version+"\">");
		System.out.println("     <error/>");
		System.out.println("     <file error=\""+!pReadOne+"\">"+pOne.getAbsolutePath()+"</file>");
		if(pTriedTwo) {
			System.out.println("     <file error=\""+!pReadTwo+"\">"+pTwo.getAbsolutePath()+"</file>");
		}
		System.out.println("</dissimilar>");
	}
	
	/**
	 * Calculate PSNR; see http://en.wikipedia.org/wiki/Peak_signal-to-noise_ratio
	 * @param pOne first image to compare
	 * @param pTwo second image to compare
	 * @param pGreyscale whether the images are greyscale or not
	 * @return calculated psnr
	 */
	public static double calcPSNR(final int[] pOne, final int[] pTwo, final boolean pGreyscale) {
		
		if(!checkPair(pOne, pTwo)) return -1;
		
		final double mse = calcMSE(pOne, pTwo, pGreyscale);
		
		//what to do if the bit depth of the images are different?
		
		//we are just using an 8-bit per channel representation at the moment
		final double maxPixelValue = Math.pow(2, 8)-1;
		
		final double psnr = 10*Math.log10((maxPixelValue*maxPixelValue)/mse);
		
		return psnr;
	}
	
	/**
	 * Calculate the PSNR between two files
	 * @param pOne first image to compare
	 * @param pTwo second image to compare
	 * @return calculated psnr
	 */
	public static double calcPSNR(final File pOne, final File pTwo) {
		BufferedImage imageOne = null;
		try {
			imageOne = Imaging.getBufferedImage(pOne);
		} catch (IOException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		} catch (NullPointerException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		} catch (ImageReadException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		}
		
		//getRGB only returns 8 bits per component, so what about 16-bit images? 
		final int[] oneA = imageOne.getRGB(0, 0, imageOne.getWidth(), imageOne.getHeight(), null, 0, imageOne.getWidth());
		final boolean greyscale = (imageOne.getType()==BufferedImage.TYPE_BYTE_GRAY||imageOne.getType()==BufferedImage.TYPE_USHORT_GRAY);
		imageOne = null;
		
		BufferedImage imageTwo = null;
		try {
			imageTwo = Imaging.getBufferedImage(pTwo);
		} catch (IOException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		} catch (NullPointerException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		} catch (ImageReadException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		}

		//getRGB only returns 8 bits per component, so what about 16-bit images? 
		final int[] twoA = imageTwo.getRGB(0, 0, imageTwo.getWidth(), imageTwo.getHeight(), null, 0, imageTwo.getWidth());
		imageTwo = null;
		
		final double psnr = calcPSNR(oneA, twoA, greyscale);		
		
		return psnr;
	}
	
	/**
	 * Calculate the SSIM between two files
	 * @param pOne first image to compare
	 * @param pTwo second image to compare
	 * @return calculated ssim
	 */
	public static double calcSSIM(final File pOne, final File pTwo) {
		return calcSSIM(pOne, pTwo, null, null, null);
	}
	
	/**
	 * Calculate the SSIM between two files
	 * @param pOne first image to compare
	 * @param pTwo second image to compare
	 * @param pHeatMapFilename ssim heat map image filename (can be null)
	 * @param pMin list for return value - ssim minimum (can be null)
	 * @param pVariance list for return value - ssim variance (can be null)
	 * @return calculated ssim
	 */
	public static double calcSSIM(final File pOne, final File pTwo, final String pHeatMapFilename, List<Double> pMin, List<Double> pVariance) {
		
		BufferedImage imageOne = null;
		try {
			imageOne = Imaging.getBufferedImage(pOne);
		} catch (IOException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		} catch (NullPointerException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		} catch (ImageReadException e) {
			printError(pOne, false, false, pTwo, false);
			return -1;
		}
		
		//getRGB only returns 8 bits per component, so what about 16-bit images? 
		final int[] oneA = imageOne.getRGB(0, 0, imageOne.getWidth(), imageOne.getHeight(), null, 0, imageOne.getWidth());
		final int width = imageOne.getWidth();
		final int height = imageOne.getHeight();
		final boolean greyscale = (imageOne.getType()==BufferedImage.TYPE_BYTE_GRAY||imageOne.getType()==BufferedImage.TYPE_USHORT_GRAY);
		imageOne = null;
		
		BufferedImage imageTwo = null;
		try {
			imageTwo = Imaging.getBufferedImage(pTwo);
		} catch (IOException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		} catch (NullPointerException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		} catch (ImageReadException e) {
			printError(pOne, true, true, pTwo, false);
			return -1;
		}

		//getRGB only returns 8 bits per component, so what about 16-bit images? 
		final int[] twoA = imageTwo.getRGB(0, 0, imageTwo.getWidth(), imageTwo.getHeight(), null, 0, imageTwo.getWidth());
		imageTwo = null;
		
		final double ssim = calcSSIM(oneA, twoA, width, height, greyscale, pHeatMapFilename, pMin, pVariance);		

		return ssim;
	}

	/**
	 * Compare two files, according to parameters passed via command line
	 * @param pOne first file to compare
	 * @param pTwo second file to compare
	 * @param pHeatMapImage file to save ssim heat map image to
	 * @param pCalcSSIM whether or not to calculate ssim
	 * @param pCalcPSNR whether or not to calculate psnr
	 */
	private static void compare(final File pOne, final File pTwo, final String pHeatMapImage, final boolean pCalcSSIM, final boolean pCalcPSNR) {
		
		//just load the images once and use the internal methods for calculating ssim/psnr
		long time = System.currentTimeMillis();
		BufferedImage imageOne = null;
		try {
			imageOne = Imaging.getBufferedImage(pOne);
		} catch (IOException e) {
			printError(pOne, false, false, pTwo, false);
			return;
		} catch (NullPointerException e) {
			printError(pOne, false, false, pTwo, false);
			return;
		} catch (ImageReadException e) {
			printError(pOne, false, false, pTwo, false);
			return;
		}
		final long oneLoadTime = System.currentTimeMillis()-time;
		//getRGB only returns 8 bits per component, so what about 16-bit images?
		final int[] oneA = imageOne.getRGB(0, 0, imageOne.getWidth(), imageOne.getHeight(), null, 0, imageOne.getWidth());
		final int width = imageOne.getWidth();
		final int height = imageOne.getHeight();
		final boolean greyscale = (imageOne.getType()==BufferedImage.TYPE_BYTE_GRAY||imageOne.getType()==BufferedImage.TYPE_USHORT_GRAY);
		imageOne = null;
		time = System.currentTimeMillis();
		BufferedImage imageTwo = null;
		try {
			imageTwo = Imaging.getBufferedImage(pTwo);
		} catch (IOException e) {
			printError(pOne, true, true, pTwo, false);
			return;
		} catch (NullPointerException e) {
			printError(pOne, true, true, pTwo, false);
			return;
		} catch (ImageReadException e) {
			printError(pOne, true, true, pTwo, false);
			return;
		}
		final long twoLoadTime = System.currentTimeMillis()-time;

		//getRGB only returns 8 bits per component, so what about 16-bit images?
		final int[] twoA = imageTwo.getRGB(0, 0, imageTwo.getWidth(), imageTwo.getHeight(), null, 0, imageTwo.getWidth());
		imageTwo = null;
		
		//calculate psnr if wanted
		time = System.currentTimeMillis();
		double psnr = 0;		
		long psnrCalc = 0;
		if(pCalcPSNR) {
			psnr = calcPSNR(oneA, twoA, greyscale);	
			psnrCalc = System.currentTimeMillis()-time;
		}
		
		//calculate ssim if wanted
		time = System.currentTimeMillis();
		List<Double> ssimMin = new LinkedList<Double>();
		List<Double> ssimVariance = new LinkedList<Double>();
		double ssim = 0;		
		long ssimCalc = 0;
		if(pCalcSSIM) {
			ssim = calcSSIM(oneA, twoA, width, height, greyscale, pHeatMapImage, ssimMin, ssimVariance);	
			ssimCalc = System.currentTimeMillis()-time;
		}

		System.out.println("<dissimilar version=\""+version+"\">");
		System.out.println("     <file loadTimeMS=\""+oneLoadTime+"\">"+pOne+"</file>");
		System.out.println("     <file loadTimeMS=\""+twoLoadTime+"\">"+pTwo+"</file>");
		if(pCalcSSIM) {
			System.out.println("     <ssim calcTimeMS=\""+ssimCalc+"\">");
			if(ssim>0) {
				System.out.println("          <mean>"+new DecimalFormat("0.0000000").format(ssim)+"</mean>");
				System.out.println("          <min>"+new DecimalFormat("0.0000000").format(ssimMin.get(0))+"</min>");
				System.out.println("          <variance>"+new DecimalFormat("0.0000000").format(ssimVariance.get(0))+"</variance>");
			} else {
				System.out.println("failed");
			}
			System.out.println("     </ssim>");
		}
		if(pCalcPSNR) {
			System.out.println("     <psnr calcTimeMS=\""+psnrCalc+"\">"+new DecimalFormat("0.0000").format(psnr)+"</psnr>");
		}
		System.out.println("</dissimilar>");
		
	}
	
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
	
		Properties mavenProps = new Properties();
		try {
			mavenProps.load(DissimilarV2.class.getClassLoader().getResourceAsStream("META-INF/maven/uk.bl.dpt.qa/dissimilar/pom.properties"));
			version = mavenProps.getProperty("version");
		} catch (Exception e) {
		}
		
		boolean calcPSNR = true;
		boolean calcSSIM = true;
		String heatMapImage = null;
		
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("m", "heatmap", true, "file to save the ssim heatmap to (png)");
		options.addOption("p", "psnr", false, "calculate just psnr");
		options.addOption("s", "ssim", false, "calculate just ssim");
		options.addOption("h", "help", false, "help text");

		CommandLine com = null;
		try {
			com = parser.parse(options, args);
		} catch (ParseException e) {
			HelpFormatter help = new HelpFormatter();
			help.printHelp("Dissimilar v"+version, options);
			return;
		}
		
		if(com.hasOption("help")) {
			HelpFormatter help = new HelpFormatter();
			help.printHelp("Dissimilar v"+version, options);
			return;			
		}
		
		if(com.hasOption("psnr")&com.hasOption("ssim")) {
			//do nothing - both on by default
		} else {
			if(com.hasOption("psnr")) {
				calcPSNR = true;
				calcSSIM = false;
			}
			if(com.hasOption("ssim")) {
				calcPSNR = false;
				calcSSIM = true;
			}
		}
		
		if(com.hasOption("heatmap")) {
			heatMapImage = com.getOptionValue("heatmap");
		}
		
		File one = new File(com.getArgs()[0]);
		File two = new File(com.getArgs()[1]);

		if(one.exists()&&two.exists()) {
			compare(one, two, heatMapImage, calcSSIM, calcPSNR);
		}

	}

}
