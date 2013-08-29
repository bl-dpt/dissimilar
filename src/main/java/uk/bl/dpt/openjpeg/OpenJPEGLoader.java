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
package uk.bl.dpt.openjpeg;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * A class that uses OpenJPEG to load jp2 files
 * This does it the easiest way; by using binaries.  Not JNI (yet) - waiting for
 * library to mature
 * @author wpalmer
 *
 */
@SuppressWarnings("unused")
public class OpenJPEGLoader {

	private OpenJPEGLoader() {}
	private static Logger logger = LogManager.getLogger();

	////////////////////////////////////////////////////////////////////////////////////////////
	// Constants
	////////////////////////////////////////////////////////////////////////////////////////////	

	//2.0.0-cc is rev 1283c6a125a29c16790a175dad7984d4bd9633ea from codeccentral on github
	private final static String VERSION_200CC = "2.0.0-cc";
	private final static String DLL_OPENJPEG_JNI_200CC = "openjpegjni";
	private final static String DLL_OPENJPEG_MAIN_200CC = "openjp2";
	private final static String EXE_DECOMPRESS_200CC = "opj_decompress";

	private final static String VERSION_151 = "1.5.1";
	private final static String DLL_OPENJPEG_JNI_151 = "openjpegjni";
	private final static String DLL_OPENJPEG_MAIN_151 = "openjpeg";
	private final static String EXE_DECOMPRESS_151 = "j2k_to_image";

	private final static boolean gUseExecutable = true;
	
	private final static String VERSION = VERSION_151;
	private final static String DLL_OPENJPEG_JNI = DLL_OPENJPEG_JNI_151;
	private final static String DLL_OPENJPEG_MAIN = DLL_OPENJPEG_MAIN_151;
	private final static String EXE_DECOMPRESS = EXE_DECOMPRESS_151;

//	private final static String VERSION = VERSION_200CC;
//	private final static String DLL_OPENJPEG_JNI = DLL_OPENJPEG_JNI_200CC;
//	private final static String DLL_OPENJPEG_MAIN = DLL_OPENJPEG_MAIN_200CC;
//	private final static String EXE_DECOMPRESS = EXE_DECOMPRESS_200CC;	
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// Global variables
	////////////////////////////////////////////////////////////////////////////////////////////	
		
	private static String BITS = "";
	private static File OPENJPEGLIB = null;
	private static File OPENJPEGJNI = null;
	private static File OPENJPEGEXE = null;
	private static boolean INITIALISED = false;

	////////////////////////////////////////////////////////////////////////////////////////////
	// Utility methods
	////////////////////////////////////////////////////////////////////////////////////////////	
	
	private static void copyStreamToFile(InputStream pInputStream, File pFile) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(pInputStream);
		FileOutputStream fos = new FileOutputStream(pFile.getAbsolutePath());
		byte[] buffer = new byte[32768];
		int bytesRead = 0;
		while(bis.available()>0) {
			bytesRead = bis.read(buffer);
			fos.write(buffer, 0, bytesRead);
		}
		bis.close();
		fos.close();
		pFile.deleteOnExit();
	}
	
	/**
	 * Initialise an OpenJPEGLoader
	 */
	private static void init() {
		if(INITIALISED) return;
		
		logger.trace("OpenJPEGLoader initialising...");
		if(gUseExecutable) {
			initExecutable();
		} 
		
		INITIALISED = true;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// Public methods
	////////////////////////////////////////////////////////////////////////////////////////////	
	
	/**
	 * Method to load a JP2 file using OpenJPEG 2.0.0 from CodecCentral
	 * @param pFile jp2 file to load
	 * @return decoded buffered image from file
	 */
	public static BufferedImage loadJP2(File pFile) {
		init();
		if(gUseExecutable) {
			return loadJP2_Executable(pFile);
		}
		return null;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////
	// OpenJPEG-executable methods
	////////////////////////////////////////////////////////////////////////////////////////////	
	
	private static File loadExeFromResource(String resource) throws IOException {
		logger.trace("Attempting to extract executable from jar: "+resource);
		String temp = System.getProperty("java.io.tmpdir");
		String ext = resource.substring(resource.lastIndexOf("."));
		String name = new File(resource).getName();
		name = name.substring(0, name.lastIndexOf("."));
		File libDir = File.createTempFile("dissimilar_"+name+"_", ext+".dir", new File(temp));
		libDir.mkdirs();
		libDir.deleteOnExit();
		File lib = new File(libDir.getAbsolutePath()+"/"+new File(resource).getName());
		//TODO: fail if we are not running on linux-x86_64
		InputStream libInputStream = OpenJPEGLoader.class.getClassLoader().getResourceAsStream(resource);
		copyStreamToFile(libInputStream, lib);
		logger.trace("Extracted executable from jar: "+resource+" -> "+lib.getAbsolutePath());
		return lib;
	}
	
	private static boolean loadExeFromFile(File resource) {
		if(resource.exists()) {
			logger.trace("Using executable from path: "+resource.getAbsolutePath());
			return true;
		} else {
			logger.fatal("Cannot find executable: "+resource.getAbsolutePath());
		}
		return false;
	}

	private static void initExecutable() {
		logger.trace("OpenJPEGLoader initialising executable...");
		BITS = System.getProperty("sun.arch.data.model");
		String os = System.getProperty("os.name").toLowerCase();
		String exe = "";
		String lib = "";
		if(os.contains("linux")) {
			os="linux-x86_";
			exe=EXE_DECOMPRESS;
			logger.error("Linux support not yet checked");
		}
		if(os.contains("windows")) {
			os="win";
			exe = "lib/"+VERSION+"/"+os+BITS+"/"+EXE_DECOMPRESS+".exe";
			lib = "lib/"+VERSION+"/"+os+BITS+"/"+DLL_OPENJPEG_MAIN+".dll";
		}
		try {
			OPENJPEGEXE=loadExeFromResource(exe);
			//copy exe to directory
			//OPENJPEGLIB=loadLibFromResource(lib, OPENJPEGEXE.getParentFile().getAbsolutePath());
		} catch(Exception e) {
			//try and load a system library
			String DIR = "src/main/resources/";
			if(loadExeFromFile(new File(exe))) {
				OPENJPEGEXE=new File(exe);
			} else {
				if(loadExeFromFile(new File(DIR+exe))) {
					OPENJPEGEXE=new File(DIR+exe);
				}
			}
		}
		logger.trace("OpenJPEG exe: "+OPENJPEGEXE.getAbsolutePath());
	}
	
	/**
	 * Method to load a JP2 file using OpenJPEG executable
	 * @param pFile jp2 file to load
	 * @return decoded buffered image from file
	 */
	private static BufferedImage loadJP2_Executable(File pFile) {
		logger.trace("executable decoder: "+pFile.getAbsolutePath());
		File tempOutput = null;
		try {
			tempOutput = File.createTempFile("dissimilar_"+pFile.getName()+"_", ".tif");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//FIXME: null check?
		tempOutput.deleteOnExit();
		
		List<String> commandLine = new LinkedList<String>();
		commandLine.add(OPENJPEGEXE.getAbsolutePath());
		commandLine.add("-i");
		commandLine.add(pFile.getAbsolutePath());
		commandLine.add("-o");
		commandLine.add(tempOutput.getAbsolutePath());
		
		logger.trace("running: "+commandLine.toString());
		
		ToolRunner runner = new ToolRunner(true);
		int exitCode = 0;
		try {
			exitCode = runner.runCommand(commandLine);
			logger.trace("exit code: "+exitCode);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(exitCode!=0) {
			//some error
			BufferedReader log = runner.getStdout();
			try {
				while(log.ready()) {
					logger.error("log: "+log.readLine());
				}
			} catch(IOException e) {
				
			}
		} else {
			try {
				BufferedImage image = Imaging.getBufferedImage(tempOutput);
				//force a delete
				tempOutput.delete();
				return image;
			} catch (ImageReadException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

	////////////////////////////////////////////////////////////////////////////////////////////
	// OpenJPEG JNI methods
	////////////////////////////////////////////////////////////////////////////////////////////	
	
	/* nothing here */
	
}
