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

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.bl.dpt.qa.DissimilarV2;
import uk.bl.dpt.qa.gui.CheckResult.ManualCheck;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;


/**
 * Class for DissimilarGUI
 * @author wpalmer
 *
 */
public class DissimilarGUIThread {

	private static Logger gLogger = LogManager.getLogger();
	
	/**
	 * Initialise class
	 */
	public DissimilarGUIThread() {
		gLogger.trace("Initialising DissimilarGUIThread");
	}
	
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // generated code (see below for handlers)
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="buttonFail"
    private Button buttonFail; // Value injected by FXMLLoader

    @FXML // fx:id="buttonNext"
    private Button buttonNext; // Value injected by FXMLLoader

    @FXML // fx:id="buttonOK"
    private Button buttonOK; // Value injected by FXMLLoader

    @FXML // fx:id="buttonPrevious"
    private Button buttonPrevious; // Value injected by FXMLLoader

    @FXML // fx:id="buttonToggleSSIMHeatmap"
    private Button buttonToggleSSIMHeatmap; // Value injected by FXMLLoader

    @FXML // fx:id="buttonZoomToFit"
    private Button buttonZoomToFit; // Value injected by FXMLLoader

    @FXML // fx:id="checkboxAdvance"
    private CheckBox checkboxAdvance; // Value injected by FXMLLoader

    @FXML // fx:id="checkboxGenerateOnLoad"
    private CheckBox checkboxGenerateOnLoad; // Value injected by FXMLLoader

    @FXML // fx:id="checkboxPrecacheNext"
    private CheckBox checkboxPrecacheNext; // Value injected by FXMLLoader

    @FXML // fx:id="groupLeft"
    private Group groupLeft; // Value injected by FXMLLoader

    @FXML // fx:id="groupRight"
    private Group groupRight; // Value injected by FXMLLoader

    @FXML // fx:id="imageLeft"
    private ImageView imageLeft; // Value injected by FXMLLoader

    @FXML // fx:id="imageLeftGrid"
    private GridPane imageLeftGrid; // Value injected by FXMLLoader

    @FXML // fx:id="imageLeftLabel"
    private Label imageLeftLabel; // Value injected by FXMLLoader

    @FXML // fx:id="imageRight"
    private ImageView imageRight; // Value injected by FXMLLoader

    @FXML // fx:id="imageRightGrid"
    private GridPane imageRightGrid; // Value injected by FXMLLoader

    @FXML // fx:id="imageRightLabel"
    private Label imageRightLabel; // Value injected by FXMLLoader

    @FXML // fx:id="labelCheckStatus"
    private Label labelCheckStatus; // Value injected by FXMLLoader

    @FXML // fx:id="labelImageNumber"
    private Label labelImageNumber; // Value injected by FXMLLoader

    @FXML // fx:id="mainPane"
    private GridPane mainPane; // Value injected by FXMLLoader

    @FXML // fx:id="menuBar"
    private MenuBar menuBar; // Value injected by FXMLLoader

    @FXML // fx:id="menuEdit"
    private Menu menuEdit; // Value injected by FXMLLoader

    @FXML // fx:id="menuEditDelete"
    private MenuItem menuEditDelete; // Value injected by FXMLLoader

    @FXML // fx:id="menuFile"
    private Menu menuFile; // Value injected by FXMLLoader

    @FXML // fx:id="menuFileClose"
    private MenuItem menuFileClose; // Value injected by FXMLLoader

    @FXML // fx:id="menuHelp"
    private Menu menuHelp; // Value injected by FXMLLoader

    @FXML // fx:id="menuHelpAbout"
    private MenuItem menuHelpAbout; // Value injected by FXMLLoader

    @FXML // fx:id="menuOpen"
    private MenuItem menuOpen; // Value injected by FXMLLoader

    @FXML // fx:id="menuSave"
    private MenuItem menuSave; // Value injected by FXMLLoader

    @FXML // fx:id="progressIndicator"
    private ProgressIndicator progressIndicator; // Value injected by FXMLLoader

    @FXML // fx:id="psnrLabel"
    private Label psnrLabel; // Value injected by FXMLLoader

    @FXML // fx:id="psnrLabelValue"
    private Label psnrLabelValue; // Value injected by FXMLLoader

    @FXML // fx:id="psnrPane"
    private VBox psnrPane; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPaneLeft"
    private ScrollPane scrollPaneLeft; // Value injected by FXMLLoader

    @FXML // fx:id="scrollPaneRight"
    private ScrollPane scrollPaneRight; // Value injected by FXMLLoader

    @FXML // fx:id="ssimLabel"
    private Label ssimLabel; // Value injected by FXMLLoader

    @FXML // fx:id="ssimLabelValue"
    private Label ssimLabelValue; // Value injected by FXMLLoader

    @FXML // fx:id="ssimMinLabel"
    private Label ssimMinLabel; // Value injected by FXMLLoader

    @FXML // fx:id="ssimMinPane"
    private VBox ssimMinPane; // Value injected by FXMLLoader

    @FXML // fx:id="ssimMinValue"
    private Label ssimMinValue; // Value injected by FXMLLoader

    @FXML // fx:id="ssimPane"
    private VBox ssimPane; // Value injected by FXMLLoader

    @FXML // fx:id="ssimVarianceLabel"
    private Label ssimVarianceLabel; // Value injected by FXMLLoader

    @FXML // fx:id="ssimVariancePane"
    private VBox ssimVariancePane; // Value injected by FXMLLoader

    @FXML // fx:id="ssimVarianceValue"
    private Label ssimVarianceValue; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert buttonFail != null : "fx:id=\"buttonFail\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert buttonNext != null : "fx:id=\"buttonNext\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert buttonOK != null : "fx:id=\"buttonOK\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert buttonPrevious != null : "fx:id=\"buttonPrevious\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert buttonToggleSSIMHeatmap != null : "fx:id=\"buttonToggleSSIMHeatmap\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert buttonZoomToFit != null : "fx:id=\"buttonZoomToFit\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert checkboxAdvance != null : "fx:id=\"checkboxAdvance\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert checkboxGenerateOnLoad != null : "fx:id=\"checkboxGenerateOnLoad\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert checkboxPrecacheNext != null : "fx:id=\"checkboxPrecacheNext\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert groupLeft != null : "fx:id=\"groupLeft\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert groupRight != null : "fx:id=\"groupRight\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageLeft != null : "fx:id=\"imageLeft\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageLeftGrid != null : "fx:id=\"imageLeftGrid\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageLeftLabel != null : "fx:id=\"imageLeftLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageRight != null : "fx:id=\"imageRight\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageRightGrid != null : "fx:id=\"imageRightGrid\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert imageRightLabel != null : "fx:id=\"imageRightLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert labelCheckStatus != null : "fx:id=\"labelCheckStatus\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert labelImageNumber != null : "fx:id=\"labelImageNumber\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuBar != null : "fx:id=\"menuBar\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuEdit != null : "fx:id=\"menuEdit\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuEditDelete != null : "fx:id=\"menuEditDelete\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuFile != null : "fx:id=\"menuFile\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuFileClose != null : "fx:id=\"menuFileClose\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuHelp != null : "fx:id=\"menuHelp\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuHelpAbout != null : "fx:id=\"menuHelpAbout\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuOpen != null : "fx:id=\"menuOpen\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert menuSave != null : "fx:id=\"menuSave\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert progressIndicator != null : "fx:id=\"progressIndicator\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert psnrLabel != null : "fx:id=\"psnrLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert psnrLabelValue != null : "fx:id=\"psnrLabelValue\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert psnrPane != null : "fx:id=\"psnrPane\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert scrollPaneLeft != null : "fx:id=\"scrollPaneLeft\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert scrollPaneRight != null : "fx:id=\"scrollPaneRight\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimLabel != null : "fx:id=\"ssimLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimLabelValue != null : "fx:id=\"ssimLabelValue\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimMinLabel != null : "fx:id=\"ssimMinLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimMinPane != null : "fx:id=\"ssimMinPane\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimMinValue != null : "fx:id=\"ssimMinValue\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimPane != null : "fx:id=\"ssimPane\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimVarianceLabel != null : "fx:id=\"ssimVarianceLabel\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimVariancePane != null : "fx:id=\"ssimVariancePane\" was not injected: check your FXML file 'dissimilar3.fxml'.";
        assert ssimVarianceValue != null : "fx:id=\"ssimVarianceValue\" was not injected: check your FXML file 'dissimilar3.fxml'.";

        // Initialize your logic here: all @FXML variables will have been injected

        //set the max widths of the images here
        scrollPaneLeft.prefWidthProperty().bind(imageLeftGrid.widthProperty());
        scrollPaneLeft.prefHeightProperty().bind(imageLeftGrid.heightProperty());
        scrollPaneLeft.maxWidthProperty().bind(imageLeftGrid.widthProperty());
        scrollPaneLeft.maxHeightProperty().bind(imageLeftGrid.heightProperty());
        scrollPaneRight.prefWidthProperty().bind(imageRightGrid.widthProperty());
        scrollPaneRight.prefHeightProperty().bind(imageRightGrid.heightProperty());
        scrollPaneRight.maxWidthProperty().bind(imageRightGrid.widthProperty());
        scrollPaneRight.maxHeightProperty().bind(imageRightGrid.heightProperty());
        scrollPaneLeft.vvalueProperty().bindBidirectional(scrollPaneRight.vvalueProperty());
        scrollPaneLeft.hvalueProperty().bindBidirectional(scrollPaneRight.hvalueProperty());
        scrollPaneLeft.setPannable(true);
        scrollPaneRight.setPannable(scrollPaneLeft.isPannable());

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // global variables/constants
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private boolean gHeatmapEnabled = false;
    private BufferedImage gHeatmap = null;
    private Image gHeatmapImage = null;
    private Image gRightImageSave = null;
    private boolean gHeatmapGenerated = false;
    private List<CheckResult> gResults = null;
    private int gCurrentRecord = 0;

    private int gPrecached = -1;
    private boolean gArePrecachingNow = false;
    private Image gPrecachedLeft = null;
    private Image gPrecachedRight = null;
    
    private boolean gResizeCodeHooked = false;
    
    private final int gFadeDelayMS = 500;
    private final int gThreadSleepMS = 200;
    /**
     * Minimum height for window
     */
    public static final int gMinHeight = 700;
    /**
     * Minimum width for window
     */
    public static final int gMinWidth = 800;
    
    /**
     * Re-initialise variables
     */
    void init() {
    	gHeatmapEnabled = false;
        gHeatmap = null;
        gHeatmapImage = null;
        gRightImageSave = null;
        gHeatmapGenerated = false;
        gResults = null;
        gCurrentRecord = 0;
        gPrecached = -1;
        gArePrecachingNow = false;
        gPrecachedLeft = null;
        gPrecachedRight = null;
    	imageLeft.setScaleX(1);
    	imageLeft.setScaleY(1);
    	imageRight.setScaleX(1);
    	imageRight.setScaleY(1);
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // handlers
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Handler for MenuItem[fx:id="menuFileClose"] onAction
    @FXML
    void close(ActionEvent event) {
        // handle the event here
    	System.exit(0);
    }
        
    // Handler for Button[fx:id="buttonToggleSSIMHeatmap"] onAction
    @FXML
    void toggleSSIMHeatmap(ActionEvent event) {
        // handle the event here
    	internalToggleHeatmap(!gHeatmapEnabled, true);
    }
    
    // Handler for Button[fx:id="buttonNext"] onAction
    @FXML
    void nextPressed(ActionEvent event) {
    	internalDisplayPair(++gCurrentRecord);
    }

    // Handler for Button[fx:id="buttonOK"] onAction
    @FXML
    void okPressed(ActionEvent event) {
        gResults.get(gCurrentRecord).setManualCheck(ManualCheck.OK);
        //System.out.println(CheckResult.csvHeader);
        gLogger.trace(gResults.get(gCurrentRecord).toCSV());
        internalUpdateManualCheckLabel(gCurrentRecord);
        if(checkboxAdvance.selectedProperty().get()) {
        	internalDisplayPair(++gCurrentRecord);
        }
    }
    
    // Handler for Button[fx:id="buttonFail"] onAction
    @FXML
    void failPressed(ActionEvent event) {
    	gResults.get(gCurrentRecord).setManualCheck(ManualCheck.FAIL);
        //System.out.println(CheckResult.csvHeader);
    	gLogger.trace(gResults.get(gCurrentRecord).toCSV());
        internalUpdateManualCheckLabel(gCurrentRecord);
        if(checkboxAdvance.selectedProperty().get()) {
        	internalDisplayPair(++gCurrentRecord);
        }
    }

    // Handler for Button[fx:id="buttonPrevious"] onAction
    @FXML
    void previousPressed(ActionEvent event) {
    	internalDisplayPair(--gCurrentRecord);
    }
    
    // Handler for MenuItem[fx:id="menuOpen"] onAction
    @FXML
    void openFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose an input CSV file...");
        chooser.getExtensionFilters().add(new ExtensionFilter("CSV file with input pairs", "*.csv"));
        chooser.setInitialDirectory(new File("f:/eclipse-workspace/dissimilar/"));
        Window window = mainPane.getScene().getWindow();
        File inputFile = chooser.showOpenDialog(window);
        if(inputFile!=null&&inputFile.exists()) {
        	internalOpenFile(inputFile);
        }
    }

    // Handler for MenuItem[fx:id="menuSave"] onAction
    @FXML
    void saveResults(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
    	chooser.setTitle("Choose an output CSV file...");
    	chooser.getExtensionFilters().add(new ExtensionFilter("CSV file", "*.csv"));
    	Window window = mainPane.getScene().getWindow();
    	File outputFile = chooser.showOpenDialog(window);
    	if(outputFile!=null) {
    		//internalSaveFile(outputFile);//overwrite?
    		gLogger.trace("FIXME: file saving");
    	} else {
    		internalSaveFile(outputFile);
    	}
    }
    
    @FXML
    void mouseScroll(ScrollEvent event) {

    	event.consume();
    	final double diff = 0.1;

    	//scroll pane locations
    	final double h = scrollPaneLeft.getHvalue();
    	final double v = scrollPaneLeft.getVvalue();
    	
    	final double min = 400;
    	
    	if(event.getDeltaY()>0&&((imageLeft.getBoundsInParent().getWidth()<min)||
    			imageLeft.getBoundsInParent().getHeight()<min)) {
    		return;
    	}
    	
    	if(event.getDeltaY()<0) {
    		imageLeft.setScaleX(imageLeft.getScaleX()+diff);
    		imageLeft.setScaleY(imageLeft.getScaleY()+diff);
    	} else {
    		imageLeft.setScaleX(imageLeft.getScaleX()-diff);
    		imageLeft.setScaleY(imageLeft.getScaleY()-diff);
    	}
		imageRight.setScaleX(imageLeft.getScaleX());
		imageRight.setScaleY(imageLeft.getScaleY());

		//just set the same ratio of scroll - this should work out mostly ok
       	scrollPaneLeft.setHvalue(h);
       	scrollPaneLeft.setVvalue(v);
       	
    }

    // Handler for Button[fx:id="buttonZoomToFit"] onAction
    @FXML
    void zoomToFit(ActionEvent event) {
    	internalImageZoomToFit();
    	//gLogger.trace("Fullscreen: "+((Stage)mainPane.getScene().getWindow()).isFullScreen());
    	//hook in to resize code to enable call to zoom to fit
    	if(!gResizeCodeHooked) {
    		internalHookResize();
    	}
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // mostly solely GUI methods
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Reset ImageView size so all of an image is shown
     */
    private void internalImageZoomToFit() {

    	final double scaleX = scrollPaneLeft.getViewportBounds().getWidth()/imageLeft.getImage().getWidth();
    	final double scaleY = scrollPaneLeft.getViewportBounds().getHeight()/imageLeft.getImage().getHeight();
    	
    	//System.out.println(scrollPaneLeft.getViewportBounds().getWidth()+" "+imageLeft.getImage().getWidth());
    	
    	//System.out.println(scaleX+" "+scaleY);
    	
    	if(scaleX<scaleY) {
    		imageLeft.setScaleX(scaleX);
    		imageLeft.setScaleY(scaleX);
    		imageRight.setScaleX(scaleX);
    		imageRight.setScaleY(scaleX);
    	} else {
    		imageLeft.setScaleX(scaleY);
    		imageLeft.setScaleY(scaleY);
    		imageRight.setScaleX(scaleY);
    		imageRight.setScaleY(scaleY);
    	}
    	
    }
    
    /**
     * Hook the resize of the panel
     */
    private void internalHookResize() {
    	gLogger.trace("Hooking maximise (this code does not work): "+mainPane.getScene().getWindow().getClass().getSimpleName());
		//urgh
		if(mainPane.getScene().getWindow() instanceof Stage) {
			Stage stage = ((Stage)mainPane.getScene().getWindow()); 
			stage.fullScreenProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> pValue,
						final Boolean pOld, final Boolean pNew) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							gLogger.trace("Maximised: "+pOld+" -> "+pNew);
							internalImageZoomToFit();
						}});
				}});
			
			gLogger.trace("Hooking resize (this code does not work)");
			ChangeListener<Number> listener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
					internalImageZoomToFit();
					System.out.println(arg1.intValue()+" -> "+arg2.intValue());
				}
			};
			
			stage.widthProperty().addListener(listener);
			stage.heightProperty().addListener(listener);			
			
			//mainPane.widthProperty().addListener(listener);
			//mainPane.heightProperty().addListener(listener);
		}
		
		
		
		gResizeCodeHooked = true;
    }
    
    /**
     * Convenience method for disabling/enabling GUI - user controls should be added here
     * @param pDisable true = disable interface, false = enable interface
     */
    private void internalDisableInterface(boolean pDisable) {
    	buttonNext.setDisable(pDisable);
    	buttonPrevious.setDisable(pDisable);
    	buttonOK.setDisable(pDisable);
    	buttonFail.setDisable(pDisable);
    	buttonToggleSSIMHeatmap.setDisable(pDisable);
    	checkboxAdvance.setDisable(pDisable);
    	checkboxGenerateOnLoad.setDisable(pDisable);
    	checkboxPrecacheNext.setDisable(pDisable);
    	imageLeft.setDisable(pDisable);
    	imageRight.setDisable(pDisable);
    	scrollPaneLeft.setDisable(pDisable);
    	scrollPaneRight.setDisable(pDisable);
    	buttonZoomToFit.setDisable(pDisable);
    }
    
    /**
     * Convenience method to run before a GUI update thread
     */
    private void internalBeforeGUIThread() {
    	internalDisableInterface(true);
		progressIndicator.setVisible(true);
		progressIndicator.setOpacity(1);
    }
    
    /**
     * Convenience method to run after a GUI update thread (i.e. fade progress indicator)
     */
    private void internalAfterGUIThread() {
    	internalDisableInterface(false);
    	Platform.runLater(new Runnable() {
			//@Override
			public void run() {
				FadeTransition fade = new FadeTransition(Duration.millis(gFadeDelayMS), progressIndicator);
				fade.setFromValue(1);
				fade.setToValue(0);
				fade.play();
			}
		});
    }
    
    /**
     * Toggle heatmap visibility
     * @param pEnabled whether heatmap should be enabled
     * @param pUpdateImage whether or not to update the heatmap image
     */
    private void internalToggleHeatmap(boolean pEnabled, boolean pUpdateImage) {
    	String enabled = "-fx-color: green;";
    	String disabled = "";
    	gHeatmapEnabled = pEnabled;
    	if(gHeatmapEnabled) {
    		disabled = buttonToggleSSIMHeatmap.getStyle();
    		buttonToggleSSIMHeatmap.setStyle(enabled);
    		if(pUpdateImage) internalOverlayHeatmap();
    	} else {
    		buttonToggleSSIMHeatmap.setStyle(disabled);
    		if(pUpdateImage) {
    			imageRight.setImage(gRightImageSave);
    		}
    	}
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // internal methods (trying to keep javafx out of here if poss)
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Save the results to pFile
     * @param pFile file to save results to
     */
    private void internalSaveFile(File pFile) {
    	//print to console anyway for the moment
    	System.out.println(CheckResult.csvHeader);
    	for(CheckResult result:gResults) {
    		System.out.println(result.toCSV());
    	}
    	if(null!=pFile) {
    		//save to file
    		
    	}
    }
    
    /**
     * Open input csv file
     * @param pFile file to load from
     */
    private void internalOpenFile(File pFile) {
    	init();
        gResults = Collections.synchronizedList(new LinkedList<CheckResult>());
        try {
			BufferedReader buf = new BufferedReader(new FileReader(pFile));
			while(buf.ready()) {
				CheckResult result = new CheckResult();
				String[] line = buf.readLine().split(",");
				if(line==null||line.length!=2) {
					continue;
				}
				result.setFileOne(new File(line[0]));
				result.setFileTwo(new File(line[1]));
				gResults.add(result);
			}
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //initialise the results array to hold the correct number of results
        
        if(checkboxGenerateOnLoad.selectedProperty().get()) {
        	//start a background thread to calculate all the values
        	internalStartBackgroundLoadThread();
        }
        
        //load the first image
        internalDisplayPair(gCurrentRecord);
    }
    
    /**
     * Start a background thread to pre-process all input pairs
     */
    private void internalStartBackgroundLoadThread() {
    	Task<Integer> task = new ProcessingLoadTask(gResults, false, false, false);
		Thread loader = new Thread(task);
		loader.setDaemon(true);
		loader.start();
    }

 
    /**
     * Main display method - calculates next record to display, calculates it if necessary and
     * displays it
     * @param pRecord record to display
     */
    private void internalDisplayPair(final int pRecord) {
    	
    	if(pRecord>=gResults.size()) {
    		gCurrentRecord=gResults.size()-1;
    		gLogger.trace("No next!");
    		return;
    	} 
    	
    	if(pRecord<0) {
    		gCurrentRecord = 0;
    		gLogger.trace("No prev!");
    		return;
    	}

    	//set the image number
    	labelImageNumber.setText("Pair "+(pRecord+1+" of "+gResults.size()));
    	internalUpdateManualCheckLabel(pRecord);
    	gHeatmap = null;
    	gHeatmapGenerated = false;
    	gRightImageSave = null;
    	gHeatmapImage = null;
    	internalToggleHeatmap(false, false);
    	
    	boolean exit = false;
    	
    	if(pRecord==gPrecached) {
    		gLogger.trace("Displaying precached pair");
    		internalDisplayPrecachedPair(pRecord);
    		exit = true;
    	}
    	
    	if(checkboxPrecacheNext.selectedProperty().get()) {
    		gLogger.trace("Generating next precached pair");
    		internalPrecachePair(pRecord+1);
    	}
    	
    	if(exit) {
    //		internalImageZoomToFit();
    		return;
    	}
    	
    	if(gResults.get(pRecord).processed) {
    		//i.e. we have already calculated this!
    		gLogger.trace("Reloading: "+gResults.get(pRecord).toCSV());
    		internalReloadRecord(pRecord);    		
    	} else {
    		
    		if(gResults.get(pRecord).isProcessing) {
    			gLogger.trace("Waiting for processing... "+gResults.get(pRecord).toCSV());

    			internalBeforeGUIThread();

    			progressIndicator.progressProperty().unbind();
				progressIndicator.setProgress(0);
				try {
					while(!gResults.get(pRecord).processed) {
						gLogger.trace("Waiting for: "+gResults.get(pRecord).toCSV());
						Thread.sleep(gThreadSleepMS);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				internalAfterGUIThread();

				internalReloadRecord(pRecord); 
			}
    		
    		if(gResults.get(pRecord).claimForProcessing()) {
    			gLogger.trace("Processing: "+gResults.get(pRecord).toCSV());
    			internalCalcAndDisplayRecord(pRecord);
    		}        	
        	
    	}
//    	internalImageZoomToFit();
    }
    
    /**
     * Pre-cache the next set of images and calculate if necessary
     * @param pRecord
     */
    private void internalPrecachePair(final int pRecord) {
    	if(pRecord>=gResults.size()) {
    		gLogger.trace("Precache: No next!");
    		return;
    	} 
    	
    	if(pRecord<0) {
    		gLogger.trace("Precache: No prev!");
    		return;
    	}
    	
    	gLogger.trace("Precaching record: "+pRecord);
    	
    	gArePrecachingNow = true;
    	boolean justLoad = false;
    	
    	if(!gResults.get(pRecord).processed) {
    		if(gResults.get(pRecord).claimForProcessing()) {
    			List<CheckResult> single = new LinkedList<CheckResult>();
    			single.add(gResults.get(pRecord));
    			Task<Integer> task = new ProcessingLoadTask(single, false, true, true);
    			Thread loader = new Thread(task);
    			loader.setDaemon(true);
    			//FIXME: hack here to set precached id
				gPrecached = pRecord;
    			loader.start();
				return;
    		} else {
    			//enter busy-wait loop? - just load images and presume calcs will be done(?)
    			justLoad = true;
    		}
    	}
    	
    	if(gResults.get(pRecord).processed|justLoad) {
    		Task<Integer> task = new Task<Integer>() {
    			@Override
    			protected Integer call() throws Exception {
    				gLogger.trace("just loading images for precache (no calcs)");
    				final Image left = SwingFXUtils.toFXImage(internalLoadImage(gResults.get(pRecord).getFileOne()), null);
    				final Image right = SwingFXUtils.toFXImage(internalLoadImage(gResults.get(pRecord).getFileTwo()), null);
    				Platform.runLater(new Runnable() {
    					//@Override
    					public void run() {
    						gPrecachedLeft = left;
    						gPrecachedRight = right;
    	    				gPrecached = pRecord;
    	    		    	gArePrecachingNow = false;
    					}
    				});
    		    	gLogger.trace("Precaching done for record "+pRecord);
    				return null;
    			}
    		};
     		Thread loader = new Thread(task);
    		loader.setDaemon(true);
    		loader.start();
    		return;
    	}
    	
    	gLogger.trace("Precaching incomplete for record "+pRecord);
    	gArePrecachingNow = false;
    	gPrecached = -1;
	}
    
    /**
     * Pre-cache the next set of images and calculate if necessary
     * @param pRecord
     */
    private void internalDisplayPrecachedPair(int pRecord) {
    	if(pRecord>=gResults.size()) {
    		gCurrentRecord=gResults.size()-1;
    		gLogger.trace("No next!");
    		return;
    	} 
    	
    	if(pRecord<0) {
    		gCurrentRecord = 0;
    		gLogger.trace("No prev!");
    		return;
    	}

    	if(pRecord == gPrecached) {
    		if(gArePrecachingNow) {
    			//wait until we aren't precaching
    			try {
					while(!gArePrecachingNow) {
						gLogger.trace("Waiting for preacache operation to finish: "+gResults.get(pRecord).toCSV());
						Thread.sleep(gThreadSleepMS);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		gLogger.trace("Displaying precached record: "+pRecord);

    		if(gPrecachedLeft == null) gLogger.trace("gPrecachedLeft == null");
    		if(gPrecachedRight == null) gLogger.trace("gPrecachedRight == null");
    		
    		internalDisableInterface(true);
    		imageLeft.setImage(gPrecachedLeft);
    		imageRight.setImage(gPrecachedRight);
    		imageLeftLabel.setText(gResults.get(pRecord).getFileOne().getName());
    		imageRightLabel.setText(gResults.get(pRecord).getFileTwo().getName());
    		psnrLabelValue.setText(gResults.get(pRecord).getPsnr()+"");
    		ssimLabelValue.setText(gResults.get(pRecord).getSsimMean()+"");
    		internalSetSSIMVariance(gResults.get(pRecord).getSsimVariance());
    		ssimMinValue.setText(gResults.get(pRecord).getSsimMin()+"");
    		internalUpdateManualCheckLabel(pRecord);
    		internalDisableInterface(false);
    		internalImageZoomToFit();
    	}
    	
	}

    private void internalSetSSIMVariance(double pVar) {
    	ssimVarianceValue.setText(new DecimalFormat("0.0000000").format(pVar)+"");
    }
    
	/**
     * Calculate and display a record
     * @param pRecord record to calculate and display
     */
    private void internalCalcAndDisplayRecord(final int pRecord) {
    	List<CheckResult> single = new LinkedList<CheckResult>();
    	single.add(gResults.get(pRecord));
    	//this is a hack - create a list with only one entry for processing
    	//third param is true to ignore isProcessing checks as the record
    	//should be claimed for us by this point
		Task<Integer> task = new ProcessingLoadTask(single, true, true, false);
		progressIndicator.progressProperty().bind(task.progressProperty());
		imageLeftLabel.setText(gResults.get(pRecord).getFileOne().getName());
		imageRightLabel.setText(gResults.get(pRecord).getFileTwo().getName());
		Thread loader = new Thread(task);
		loader.setDaemon(true);
		loader.start();
    }
    
    /**
     * Update GUI from manual check entry
     * @param pRecord record to use
     */
    private void internalUpdateManualCheckLabel(int pRecord) {
    	if(!gResults.get(pRecord).processed) {
        	//set an empty text
        	labelCheckStatus.setStyle("");
           	labelCheckStatus.setText("");    	
           	return;
    	}
    	if(gResults.get(pRecord).getManualCheck()==ManualCheck.OK) {
    		labelCheckStatus.setStyle(buttonOK.getStyle());
        	labelCheckStatus.setText("OK");    	
    		return;
    	}
    	if(gResults.get(pRecord).getManualCheck()==ManualCheck.FAIL) {
    		labelCheckStatus.setStyle(buttonFail.getStyle());
        	labelCheckStatus.setText("FAIL");    	
    		return;
    	}
    	//set an empty text
    	labelCheckStatus.setStyle("");
       	labelCheckStatus.setText("");    	
    }

    /**
     * Reload a record (no calculations)
     * @param pRecord
     */
    private void internalReloadRecord(final int pRecord) {
    	gLogger.trace("Reloading record: "+pRecord);
    	
    	Task<Integer> task = new Task<Integer>() {

			@Override
			protected Integer call() throws Exception {
				int max = 2;
				int count = 0;

				Platform.runLater(new Runnable() {
					//@Override
					public void run() {
						internalBeforeGUIThread();
						imageLeft.setImage(null);
						imageRight.setImage(null);
						imageLeftLabel.setText(gResults.get(pRecord).getFileOne().getName());
						imageRightLabel.setText(gResults.get(pRecord).getFileTwo().getName());
    	    			psnrLabelValue.setText(gResults.get(pRecord).getPsnr()+"");
    	    			ssimLabelValue.setText(gResults.get(pRecord).getSsimMean()+"");
    	        		internalSetSSIMVariance(gResults.get(pRecord).getSsimVariance());
    	    			ssimMinValue.setText(gResults.get(pRecord).getSsimMin()+"");
    	    	        internalUpdateManualCheckLabel(pRecord);
					}
				});

    			final BufferedImage one = internalLoadImage(gResults.get(pRecord).getFileOne());
    			Platform.runLater(new Runnable() {
    				//@Override
    				public void run() {
    					imageLeft.setImage(SwingFXUtils.toFXImage(one, null));
    				}
    			});
    			updateProgress(++count, max);
    			final BufferedImage two = internalLoadImage(gResults.get(pRecord).getFileTwo());
    			Platform.runLater(new Runnable() {
    				//@Override
    				public void run() {
    					imageRight.setImage(SwingFXUtils.toFXImage(two, null));
    					internalImageZoomToFit();
    				}
    			});
    			updateProgress(++count, max);
				
    			internalAfterGUIThread();
    			
				return null;
			}
    		
    	};
    	progressIndicator.progressProperty().bind(task.progressProperty());
    	Thread loader = new Thread(task);
		loader.setDaemon(true);
		loader.start();
    	
    	internalDisableInterface(false);
    }

  
    /**
     * Load an image 
     * @param pImage image to load
     * @return loaded image
     */
    private BufferedImage internalLoadImage(File pImage) {
    	try {
    		if(pImage.getName().toLowerCase().endsWith(".jp2")) {
    			//load a jp2?
    			//FIXME: use openjpeg?
    			//this uses jai (jj2000)
    			//BufferedImage image = ImageIO.read(pImage);
    			BufferedImage image = null;//OpenJPEGLoader.loadJP2(pImage);
    			return image;
    		}
			return Imaging.getBufferedImage(pImage);
		} catch (IOException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		} catch (ImageReadException e) {
			return null;
		}
    }
    
    /**
     * overlay heatmap on image
     */
    private void internalOverlayHeatmap() {
    	
    	gRightImageSave = imageRight.getImage();

    	if(!gHeatmapGenerated) {

    		internalBeforeGUIThread();
			
    		//threaded load so GUI doesn't hang
        	Task<Integer> task = new Task<Integer>() {

				@Override
				protected Integer call() throws Exception {
					BufferedImage image = SwingFXUtils.fromFXImage(imageRight.getImage(), null);

		    		if(gHeatmap == null) {
		    			//re-generate heatmap (must be on a re-load)
		    			try {
							gHeatmap = Imaging.getBufferedImage(gResults.get(gCurrentRecord).getHeatmapTemp());
						} catch (IOException | ImageReadException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		    		}
		    		
		    		for(int y=0;y<image.getHeight();y++) {    		
		    			for(int x=0;x<image.getWidth();x++) {
		    				int rgb = image.getRGB(x, y);

		    				Color heatmapColor = new Color(gHeatmap.getRGB((x/DissimilarV2.SSIMWINDOWSIZE), (y/DissimilarV2.SSIMWINDOWSIZE)));
		    				int heatmapPixel = heatmapColor.getGreen();//&maxPixelValue;
		    				if(heatmapColor.getGreen()!=heatmapColor.getBlue()&&heatmapColor.getBlue()!=heatmapColor.getRed()) {
		    					gLogger.error("Heatmap error (should not happen)");
		    				}

		    				double factor = 1-(((double)heatmapPixel/255));

		    				Color col = new Color(rgb);
		    				int red = (int) (factor*col.getRed());
		    				int green = (int) (factor*col.getGreen());
		    				int blue = (int) (factor*col.getBlue());

		    				if(red<0) red = 0;
		    				if(green<0) green = 0;
		    				if(blue<0) blue = 0;
		    				col = new Color(red, green, blue);

		    				image.setRGB(x, y, col.getRGB());
		    			}
		    		}

		    		gHeatmapImage = SwingFXUtils.toFXImage(image, null);
		    		gHeatmapGenerated = true;
		    		
		    		Platform.runLater(new Runnable() {
		    			//@Override
		    			public void run() {
		    				imageRight.setImage(gHeatmapImage);
		    			}
		    		});
		    		
		    		internalAfterGUIThread();
		    		
		    		return 1;
				}
   
        	};
        	
        	progressIndicator.progressProperty().bind(task.progressProperty());
        	Thread loader = new Thread(task);
    		loader.setDaemon(true);
    		loader.start();
    		
    	} else {
    		imageRight.setImage(gHeatmapImage);
    	}
    	
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // internal classes
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Processes an input pair, updating values in referenced list
     * NOTE: list should be synchronized for multi-threading
     * i.e. gResults = Collections.synchronizedList(new LinkedList<CheckResult>());
     * @author wpalmer
     *
     */
    private class ProcessingLoadTask extends Task<Integer> {
    	
    	private List<CheckResult> taskResults = null;
    	
    	private boolean updateGUI = false;
    	private boolean ignoreClaims = false;
    	private boolean populatePrecache = false;
    	
    	/**
    	 * Create a new ProcessingLoadTask
    	 * @param pResults list of objects to process
    	 * @param pUpdateGUI whether or not to update the GUI
    	 * @param pIgnoreClaims whether or not to ignore CheckResult.isProcessing (be careful)
    	 * @param pPopulatePrecache whether or not to populate the global precache images
    	 */
    	public ProcessingLoadTask(List<CheckResult> pResults, boolean pUpdateGUI, boolean pIgnoreClaims, boolean pPopulatePrecache) {
    		taskResults = pResults;
    		updateGUI = pUpdateGUI;
    		ignoreClaims = pIgnoreClaims;
    		populatePrecache = pPopulatePrecache;
    	}
    	
		@Override
		protected Integer call() throws Exception {

			gLogger.trace("LoadTask calculating: "+taskResults.size()+" entries");
			
			for(CheckResult result:taskResults) {

				//some other process had claimed this record for processing
				if(!ignoreClaims&!result.claimForProcessing()) {
					gLogger.trace("LoadTask Skipping: "+result.toCSV());
					continue;
				} else {
					gLogger.trace("LoadTask Processing: "+result.toCSV());
				}
				
				result.isProcessing = true;
				
				if(updateGUI) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							internalBeforeGUIThread();
							//filenames are set in calling method due to issues accessing members here
							imageLeft.setImage(null);
							imageRight.setImage(null);
							String calculating = "Working";
							psnrLabelValue.setText(calculating);
							ssimLabelValue.setText(calculating);
							ssimVarianceValue.setText(calculating);
							ssimMinValue.setText(calculating);
						}
					});
				}

				//if any more updateProgress() calls are added then increase this value!!!
				int maxProgress = 8;
				int progress = 0;

				final BufferedImage imageOne = internalLoadImage(result.getFileOne());
				updateProgress(++progress, maxProgress);

				if(updateGUI) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							if(imageOne!=null) imageLeft.setImage(SwingFXUtils.toFXImage(imageOne, null));
						}
					});
				}
				
				if(populatePrecache) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							gPrecachedLeft = SwingFXUtils.toFXImage(imageOne, null);
						}
					});
				}

				final BufferedImage imageTwo = internalLoadImage(result.getFileTwo());    	
				updateProgress(++progress, maxProgress);

				if(imageOne!=null&imageTwo!=null) {
					//	    		System.out.println("Loaded ok");
				} else {
					gLogger.error("Loading error: "+result.toCSV());
					//re-enable GUI here
					if(updateGUI) {
						internalAfterGUIThread();
					}
					return null;
				}

				final boolean greyscale = (imageOne.getType()==BufferedImage.TYPE_BYTE_GRAY||imageOne.getType()==BufferedImage.TYPE_USHORT_GRAY);

				if(updateGUI) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							imageRight.setImage(SwingFXUtils.toFXImage(imageTwo, null));
						}
					});
				}
				
				if(populatePrecache) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							gPrecachedRight = SwingFXUtils.toFXImage(imageTwo, null);
						}
					});
				}

				int[] oneA = imageOne.getRGB(0, 0, imageOne.getWidth(), imageOne.getHeight(), null, 0, imageOne.getWidth());
				updateProgress(++progress, maxProgress);
				int[] twoA = imageTwo.getRGB(0, 0, imageTwo.getWidth(), imageTwo.getHeight(), null, 0, imageTwo.getWidth());
				updateProgress(++progress, maxProgress);
				final int width = imageOne.getWidth();
				final int height = imageOne.getHeight();

				//calculate psnr and ssim
				final double psnr = DissimilarV2.calcPSNR(oneA, twoA, greyscale);
				result.setPsnr(psnr);

				if(updateGUI) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							psnrLabelValue.setText(psnr+"");
						}
					});
				}
				updateProgress(++progress, maxProgress);

				List<Double> ssimMinimum = new LinkedList<Double>();
				List<Double> ssimVariance = new LinkedList<Double>();
				final File tempHeatmap = File.createTempFile(result.getFileOne().getName()+"-"+result.getFileTwo().getName()+"-", ".png");
				final double ssim = DissimilarV2.calcSSIM(oneA, twoA, width, height, greyscale, tempHeatmap.getAbsolutePath(), ssimMinimum, ssimVariance);
				tempHeatmap.deleteOnExit();
				result.setHeatmapTemp(tempHeatmap);
				result.setSsimMean(ssim);
				final double ssimVar = ssimVariance.get(0);
				result.setSsimVariance(ssimVar);
				final double ssimMin = ssimMinimum.get(0);
				result.setSsimMin(ssimMin);
				updateProgress(++progress, maxProgress);

				if(updateGUI) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							try {
								gHeatmap = Imaging.getBufferedImage(tempHeatmap);
							} catch (IOException | ImageReadException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ssimLabelValue.setText(ssim+"");
							internalSetSSIMVariance(ssimVar);
							ssimMinValue.setText(ssimMin+"");
							internalDisableInterface(false);
						}
					});
				}
				updateProgress(++progress, maxProgress);

				//try and free some memory
				oneA = null;
				twoA = null;
				System.gc();

				updateProgress(++progress, maxProgress);

				if(updateGUI) {
					internalAfterGUIThread();
					internalImageZoomToFit();
				}

				if(populatePrecache) {
					Platform.runLater(new Runnable() {
						//@Override
						public void run() {
							gArePrecachingNow = false;
					    	gLogger.trace("Precaching done (incl calcs) for record "+gPrecached);
						}
					});
				}
				
				result.processed = true;
				result.isProcessing = false;
				//System.out.println(result.toCSV());

			}
			return taskResults.size();
		}
    }
    
    
}
