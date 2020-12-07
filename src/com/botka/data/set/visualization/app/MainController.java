/*
 * File name:  MainController.java
 *
 * Programmer : Jake Botka
 *
 * Date: Aug 2, 2020
 *
 */
package com.botka.data.set.visualization.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

import com.botka.data.set.visualization.app.javafx.components.FileChooserComponent;
import com.botka.data.set.visualization.app.javafx.components.NodeControls;
import com.botka.data.set.visualization.app.javafx.handlers.StageScaling;
import com.botka.data.set.visualization.app.javafx.handlers.button.ExitButtonHandler;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.AboutMenuItem;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.DataPointerViewMenuItem;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.DataViewMenuItem;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.LoadDataMenuItem;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.StartMenuItem;
import com.botka.data.set.visualization.app.javafx.handlers.menu.items.StopMenuItem;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.com.botka.data.set.visualization.api.IRunOnMainThread;
import main.com.botka.data.set.visualization.api.data.DataPeekListener;
import main.com.botka.data.set.visualization.api.data.DataSet;
import main.com.botka.data.set.visualization.api.readers.FileReader;
import main.com.botka.data.set.visualization.api.render.engine.RenderEngine;
import main.com.botka.data.set.visualization.api.sort.BubbleSort;
import main.com.botka.data.set.visualization.api.sort.IFinishedListener;
import main.com.botka.data.set.visualization.api.step.StepOperation;
import main.com.botka.data.set.visualization.api.util.ArrayUtil;
import main.com.botka.data.set.visualization.api.visualizer.JavaFXVisualizer;
import main.com.botka.data.set.visualization.api.visualizer.Visualizer;

/**
 * <insert class description here>
 *
 * @author Jake Botka
 *
 */
public class MainController implements IRunOnMainThread, IFinishedListener,
		DataPeekListener<Number>, ControllerTransferCallback {

	private final FileChooserComponent FILE_SYSTEM_INTERFACE = new FileChooserComponent();
	private Stage mStage;
	private Scene mScene;
	private ArrayList<NodeControls> mNodeContolItems;
	private Visualizer mVisualizer;

	@FXML
	VBox mainNode;
	@FXML
	VBox mainDetailsView;
	@FXML
	ScrollPane scrollPane;
	@FXML
	SplitPane paneDivider;
	@FXML
	Canvas canvas;

	@FXML
	Button collapseBtn;
	@FXML
	MenuItem exitBtn;
	@FXML
	MenuItem aboutMenuItem;
	@FXML
	MenuItem startMenuItem;
	@FXML
	MenuItem stopMenuItem;
	@FXML
	MenuItem pauseMenuItem;
	@FXML
	MenuItem resumeMenuItem;
	@FXML
	MenuItem fileLoadMenuItem;
	@FXML
	MenuItem pointerMenuItem;
	@FXML
	MenuItem dataViewMenuItem;

	@FXML
	public void initialize() throws InterruptedException {
		FILE_SYSTEM_INTERFACE.addFileFilter(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

		exitBtn.setOnAction((EventHandler<ActionEvent>) new ExitButtonHandler());
		this.mNodeContolItems = new ArrayList(0);
		resumeMenuItem.setDisable(true);

		// collapse
		// Image image = new
		// Image(getClass().getResource("collapse-arrow.png").toExternalForm());

		collapse(scrollPane);
		collapseBtn.applyCss();
		// collapse(collapseBtn);
		// paneDivider.setDividerPosition(0, 1.0);

	}

	public void collapse(Node node) {
		if (node != null) {
			node.setVisible(false);
			node.setManaged(false);
		}
	}

	public void uncollapse(Node node) {
		if (node != null) {
			node.setVisible(true);
			node.setManaged(true);
		}
	}

	/**
	 * Inits after the setParams method is called
	 */
	private void init() {
		final StageScaling stageScaling = new StageScaling();
		DataSet<Double> dataSet = new DataSet();

		// insert data here start
		Random ran = new Random();
		for (int i = 0; i < 10; i++) // generates random data
		{
			dataSet.add(ran.nextDouble() * 100);
		}
		// insert data here end

		mVisualizer = new JavaFXVisualizer(dataSet, this.mStage, this.mScene, canvas);
		StepOperation stepOp = new BubbleSort(dataSet, this);
		RenderEngine engine = new RenderEngine(mVisualizer, stepOp, 10);
		this.mVisualizer.registerOnDataPeekCallback(this);
		AppDriver.MANAGER.setMainThreadCallback(this);

		AboutMenuItem aboutMenuItem = new AboutMenuItem(this.mStage,
				(ControllerTransferCallback) this, this.aboutMenuItem, null);
		StartMenuItem startMenuItem = new StartMenuItem(this.mStage,
				(ControllerTransferCallback) this, this.startMenuItem, null, engine);
		StopMenuItem stopMenuItem = new StopMenuItem(this.mStage, (ControllerTransferCallback) this,
				this.stopMenuItem, null, engine);
		LoadDataMenuItem loadFileMenuItem = new LoadDataMenuItem(this.mStage,
				(ControllerTransferCallback) this, this.fileLoadMenuItem, null,
				FILE_SYSTEM_INTERFACE);
		DataViewMenuItem dataViewMenuItem = new DataViewMenuItem(this.mStage, (ControllerTransferCallback) this,
				this.dataViewMenuItem, null);
		DataPointerViewMenuItem dataPointerViewMenuItem = new DataPointerViewMenuItem(this.mStage, (ControllerTransferCallback) this,
				this.pointerMenuItem, null);

		this.mNodeContolItems.add(startMenuItem);
		this.mNodeContolItems.add(stopMenuItem);

		mVisualizer.init();
		engine.init();
		this.mStage.heightProperty().addListener(stageScaling.getHeightScaling());
		this.mScene.widthProperty().addListener(stageScaling.getWidthScaling());

		/*
		 * File file = FILE_SYSTEM_INTERFACE.accessFiles(this.mStage); String fileS =
		 * "File: "; fileS = file != null ? fileS + file.getAbsolutePath() : fileS +
		 * "NULL"; ConsoleLogger.Logger.log(getClass(), fileS, true);
		 */

	}

	/**
	 * ObservableValue
	 * 
	 * @param stage
	 * @param scene
	 */
	public void setParams(Stage stage, Scene scene) {
		this.mStage = stage;
		this.mScene = scene;
		this.init();

	}

	/**
	 * Runs code on main thread. Ussually called from a thread that is not the main thread or the application thread.
	 */
	@Override
	public void runOnMainThread(Runnable run) {
		Platform.runLater(run); // javafx implementation of communicating to main thread

	}

	/**
	 * Callback on when the visalization is vinished.
	 */
	@Override
	public void onFinished() {
		if (mVisualizer != null) {
			mVisualizer.onFinished();
		}

	}

	/**
	 * Peak at a data value at a certain index of the dataset.
	 */
	@Override
	public void onPeak(int index, Number data) {
		String value = Double.toString(data.doubleValue());
		mainDetailsView.getChildren()
				.add(new Label("Index: " + String.valueOf(index) + " Value: " + value));
		scrollPane.setVvalue(scrollPane.getVmax());

	}

	@Override
	public void onTransfer(Object data, Class type) {
		if (data != null && type != null) {

			System.out.println(type.toString());
			if (type.equals(File.class)) {
				File transferFile = null;
				transferFile = (File) data;
				this.insertNewData(transferFile);
			}
		}

	}

	/**
	 * 
	 * @param file
	 */
	public void insertNewData(File file) {
		DataSet set = null;
		Comparable[] arr = null;
		try {
			FileReader fileReader = new FileReader(file); // my API not java.io
			double[] dataArr = fileReader.readAllDoubles();
			arr = (Comparable[]) ArrayUtil.converToWrapperArray(dataArr);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (arr != null)
			set = new DataSet<>(arr);
		if (set != null) {
			this.mVisualizer.insertData(set);
			System.out.println("Inserted data");
			System.out
					.println(this.mVisualizer.isInitialized() + "," + this.mVisualizer.isRunning());
			if (this.mVisualizer.isInitialized() && this.mVisualizer.isRunning() == false)
				this.mVisualizer.onRender();
		}
	}

}
