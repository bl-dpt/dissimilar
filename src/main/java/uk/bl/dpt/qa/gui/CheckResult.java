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
package uk.bl.dpt.qa.gui;

import java.io.File;

/**
 * Wrapper class for a set of results from two input files
 * @author wpalmer
 *
 */
public class CheckResult {

	/**
	 * Values to indicate whether or not the pair has been checked manually or not
	 * @author wpalmer
	 *
	 */
	public enum ManualCheck {
		/**
		 * Unknown if checked
		 */
		UNKNOWN, 
		/**
		 * Explicitly passed a manual check
		 */
		OK, 
		/**
		 * Explicitly failed a manual check
		 */
		FAIL;
	}
	
	private double gSsimMean = 0;
	private double gSsimMin = 0;
	private double gSsimVariance = 0;
	private double gPsnr = 0;
	private File gFileOne = null;
	private File gFileTwo = null;
	private ManualCheck gManualCheck = ManualCheck.UNKNOWN;
	private File gHeatmapTemp = null;
	/**
	 * Whether or not this pair has been processed
	 */
	public boolean processed = false;
	/**
	 * Is this record currently being processed?
	 */
	public boolean isProcessing = false;
	
	/**
	 * Claim this CheckReuslt for processing - if it is already processed or in progress then return false
	 * to indicate that
	 * @return true if processing of record not begun, false if record processed/processing
	 */
	public boolean claimForProcessing() {
		if(processed) return false;
		if(isProcessing) return false;
		isProcessing = true;
		return true;
	}
	
	/**
	 * Get heatmap temp file
	 * @return heatmap temp file
	 */
	public File getHeatmapTemp() {
		return gHeatmapTemp;
	}

	/**
	 * Set heatmap temp file
	 * @param gHeatmapTemp heatmap temp file
	 */
	public void setHeatmapTemp(File gHeatmapTemp) {
		this.gHeatmapTemp = gHeatmapTemp;
		this.gHeatmapTemp.deleteOnExit();
	}

	/**
	 * Get SSIM mean value
	 * @return SSIM mean value
	 */
	public double getSsimMean() {
		return gSsimMean;
	}

	/**
	 * Set SSIM mean value
	 * @param gSsimMean SSIM mean value
	 */
	public void setSsimMean(double gSsimMean) {
		this.gSsimMean = gSsimMean;
	}

	/**
	 * Set SSIM min value
	 * @return SSIM min value
	 */
	public double getSsimMin() {
		return gSsimMin;
	}

	/**
	 * Set SSIM min value
	 * @param gSsimMin SSIM min value
	 */
	public void setSsimMin(double gSsimMin) {
		this.gSsimMin = gSsimMin;
	}

	/**
	 * Get SSIM variance value
	 * @return SSIM variance value
	 */
	public double getSsimVariance() {
		return gSsimVariance;
	}

	/**
	 * Set SSIM variance value
	 * @param gSsimVariance SSIM variance value
	 */
	public void setSsimVariance(double gSsimVariance) {
		this.gSsimVariance = gSsimVariance;
	}

	/**
	 * Get PSNR value
	 * @return PSNR value
	 */
	public double getPsnr() {
		return gPsnr;
	}

	/**
	 * Set PSNR value
	 * @param gPsnr PSNR value
	 */
	public void setPsnr(double gPsnr) {
		this.gPsnr = gPsnr;
	}

	/**
	 * Get first file 
	 * @return first file
	 */
	public File getFileOne() {
		return gFileOne;
	}

	/**
	 * Set first file
	 * @param gFileOne first file
	 */
	public void setFileOne(File gFileOne) {
		this.gFileOne = gFileOne;
	}

	/**
	 * Get second file
	 * @return second file
	 */
	public File getFileTwo() {
		return gFileTwo;
	}

	/** 
	 * Set second file
	 * @param gFileTwo second file
	 */
	public void setFileTwo(File gFileTwo) {
		this.gFileTwo = gFileTwo;
	}

	/**
	 * Get manual check result
	 * @return manual check result
	 */
	public ManualCheck getManualCheck() {
		return gManualCheck;
	}

	/**
	 * Set manual check result
	 * @param gManualCheck manual check result
	 */
	public void setManualCheck(ManualCheck gManualCheck) {
		this.gManualCheck = gManualCheck;
	}

	/**
	 * Empty default constructor
	 */
	public CheckResult() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Default CSV file header for one of these records (change toCSV() in line with this)
	 */
	final public static String csvHeader = "FileOne, FileTwo, PSNR, SSIMMean, SSIMMin, SSIMVariance, ManualCheck, "; 
	
	/**
	 * Return this record as a CSV line
	 * @return this record as a CSV line
	 */
	public String toCSV() {
		String ret = "";
		ret += getFileOne().getAbsolutePath()+", ";
		ret += getFileTwo().getAbsolutePath()+", ";
		ret += getPsnr()+", ";
		ret += getSsimMean()+", ";
		ret += getSsimMin()+", ";
		ret += getSsimVariance()+", ";
		ret += getManualCheck()+", ";
		return ret;
	}

}
