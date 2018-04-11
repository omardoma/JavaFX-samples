
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import java.io.*;

public class GUI extends Application {
	private MenuBar mBar;
	private TextArea txtMain;
	// String variable to store file name
	private String fileName = "";
	// Flag to indicate editing of the text area.
	boolean editFlag;

	public void start(Stage primaryStage) throws Exception {
		// Set the title.
		primaryStage.setTitle("FXEditor V1.0");
		// Set the width.
		primaryStage.setWidth(400);
		// set the height
		primaryStage.setHeight(300);
		// new
		Menu fileMnu = new Menu("File");
		MenuItem fileNewItem = new MenuItem("New");
		fileMnu.getItems().add(fileNewItem);

		// open
		MenuItem fileOpenItem = new MenuItem("Open");
		fileMnu.getItems().add(fileOpenItem);
		fileOpenItem.setOnAction(ae -> doFileOpen());

		// save
		MenuItem fileSaveItem = new MenuItem("Save");
		fileMnu.getItems().add(fileSaveItem);
		fileSaveItem.setOnAction(ae -> doFileSave());
		// save as
		MenuItem fileSaveAsItem = new MenuItem("Save As");
		fileMnu.getItems().add(fileSaveAsItem);
		fileSaveAsItem.setOnAction(ae -> doFileSaveAs());

		// print
		MenuItem printItem = new MenuItem("Print");
		fileMnu.getItems().add(printItem);
		// fileSaveAsItem.setOnAction(ae -> );

		// close
		MenuItem fileCloseItem = new MenuItem("Close");
		fileMnu.getItems().add(fileCloseItem);
		fileCloseItem.setOnAction(ae -> {

		});
		// Exit
		MenuItem fileExitItem = new MenuItem("Exit");
		fileMnu.getItems().add(fileExitItem);
		fileExitItem.setOnAction(ae -> Platform.exit());

		// The edit menu.
		Menu editMnu = new Menu("Edit");
		MenuItem editUndoItem = new MenuItem("Undo");
		editMnu.getItems().add(editUndoItem);
		editUndoItem.setOnAction(ae -> txtMain.undo());

		MenuItem editRedoItem = new MenuItem("Redo");
		editMnu.getItems().add(editRedoItem);
		editRedoItem.setOnAction(ae -> txtMain.redo());

		MenuItem editCutItem = new MenuItem("Cut");
		editMnu.getItems().add(editCutItem);
		editCutItem.setOnAction(ae -> txtMain.cut());

		MenuItem editCopyItem = new MenuItem("Copy");
		editMnu.getItems().add(editCopyItem);
		editCopyItem.setOnAction(ae -> txtMain.copy());

		MenuItem editPasteItem = new MenuItem("Paste");
		editMnu.getItems().add(editPasteItem);
		editPasteItem.setOnAction(ae -> txtMain.paste());

		MenuItem editSelectAllItem = new MenuItem("Select All");
		editMnu.getItems().add(editSelectAllItem);
		editSelectAllItem.setOnAction(ae -> txtMain.selectAll());

		MenuItem editDeleteItem = new MenuItem("Delete");
		editMnu.getItems().add(editDeleteItem);
		editDeleteItem.setOnAction(ae -> txtMain.deleteText(txtMain.getSelection()));

		// The help menu
		Menu helpMenu = new Menu("Help");

		// The options menu
		Menu optionsMenu = new Menu("Options");

		// The tools menu
		Menu toolsMenu = new Menu("Tools");

		MenuItem helpAboutItem = new MenuItem("About");
		helpAboutItem.setOnAction(ar -> showDialog(AlertType.INFORMATION, "About", "Ashokreddy Edara - 2944699"));
		helpMenu.getItems().add(helpAboutItem);

		// Initiate a new menu bar
		mBar = new MenuBar();

		// Add menus to the menu bar.
		mBar.getMenus().addAll(fileMnu, editMnu, optionsMenu, toolsMenu, helpMenu);

		// Create alayout.use border pane.
		BorderPane bp = new BorderPane();
		// Instantiate a new text area.
		txtMain = new TextArea();
		// Add components to the layout container.
		bp.setTop(mBar);
		bp.setCenter(txtMain);

		// Create a scene.
		Scene s = new Scene(bp);
		// Set the scene.
		primaryStage.setScene(s);
		primaryStage.show();
	}

	public void doFileSaveAs() {
		// create a file chooser dialog.
		FileChooser fChooser = new FileChooser();

		// Assign the file from the dialog.
		File fileToSave = fChooser.showSaveDialog(null);

		if (fileToSave != null) {
			// Assign the file name for future saves
			fileName = fileToSave.getName();
			try {
				FileOutputStream fos = new FileOutputStream(fileToSave, true);
				String text = txtMain.getText();
				// when writing a file, bytes are required.
				byte[] dataOut = text.getBytes();

				// write the bytes to the file
				fos.write(dataOut);

				// flush and close
				fos.flush();
				fos.close();
				// Reset the text edit flag
				editFlag = false;

			} catch (IOException ioe) {
				// provide some feedback
				System.err.println("Eror writing to file:");
				ioe.printStackTrace();
			}
		} else {
			// File was null do nothing.
		}
	}

	public void doFileSave() {
		// test if the file has a name.
		if (fileName.equals("")) {
			// The file has not been previously saved.
			doFileSaveAs();
		} else {
			try {
				// create a file
				File fileToSave = new File(fileName);
				FileOutputStream fos = new FileOutputStream(fileToSave, true);
				String text = txtMain.getText();
				// when writing a file, bytes are required.
				byte[] dataOut = text.getBytes();

				// write the bytes to the file
				fos.write(dataOut);

				// flush and close
				fos.flush();
				fos.close();

				// provide some feedback
				// Reset the text edit flag
				editFlag = false;

			} catch (IOException ioe) {
				System.err.println("Eror writing to file:");
				ioe.printStackTrace();
			}
		}
	}

	public void doFileOpen() {
		FileChooser fChooser = new FileChooser();
		// Assign a file with file chooser
		File fileToOpen = fChooser.showOpenDialog(null);
		// only open a file if the user confirms the dialog
		// and if a file is selected
		if (fileToOpen != null) {
			// open the file and show in the text area.
			try {
				StringBuilder stringBldr = new StringBuilder();
				BufferedReader bReader = new BufferedReader(new FileReader(fileToOpen));
				String text;

				// iterate through the file and read a line at a time.
				while ((text = bReader.readLine()) != null) {
					// append a line ending
					text = text + "\n";
					// Accumulate text into the string builder
					stringBldr.append(text);
				} // end of while
				txtMain.setText(stringBldr.toString());
				bReader.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}

		} else {
			// No file chosen do nothing
		}
	}

	private void showDialog(AlertType alertType, String title, String msg) {
		// Show an alert with a message
		Alert alert = new Alert(alertType, msg);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		// Launch the application.
		launch();
	}

}
