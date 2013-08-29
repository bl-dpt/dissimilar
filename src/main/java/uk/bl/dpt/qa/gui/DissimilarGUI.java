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
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Main GUI method
 * @author wpalmer
 *
 */
public class DissimilarGUI extends Application {

	private static Logger logger = LogManager.getLogger();
	
	/**
	 * Start a new DissimilarGUI
	 */
	public DissimilarGUI() {}
	
	/**
	 * Main method
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		logger.trace("Launching GUI...");
		Application.launch(args);
	}

	@Override
	public void start(final Stage pPrimaryStage) throws Exception {
		logger.trace("Starting...");
		pPrimaryStage.setTitle("Dissimilar GUI");
		String DIR = "src/main/resources/";
		String FXML_LOC = "dissimilar3.fxml";
		URL fxml = null;
		fxml = getClass().getResource(FXML_LOC);
		if(fxml==null) {
			//try and load from a file
			if(new File(DIR+FXML_LOC).exists()) {
				logger.trace("FXML from file: "+DIR+FXML_LOC);
				fxml = new File(DIR+FXML_LOC).toURI().toURL();
			}
		} else {
			logger.trace("FXML from resource: "+FXML_LOC);
		}
		
		if(fxml!=null) {
			logger.trace("Loading fxml");
			Pane pane = (Pane)FXMLLoader.load(fxml);
			Scene scene = new Scene(pane);
			pPrimaryStage.setScene(scene);
			pPrimaryStage.show();
			
			//set (and enforce) minimum size
			pPrimaryStage.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> pValue,
						Number pOld, Number pNew) {
					final int minWidth = DissimilarGUIThread.gMinWidth;
					if(pNew.doubleValue()<minWidth) pPrimaryStage.setWidth(minWidth);				
				}});
			pPrimaryStage.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> pValue,
						Number pOld, Number pNew) {
					final double minHeight = DissimilarGUIThread.gMinHeight;
					if(pNew.doubleValue()<minHeight) pPrimaryStage.setHeight(minHeight);				
				}});
		}
		
	}

}
