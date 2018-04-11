
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUI extends Application {
	private MenuBar menuBar;
	private ImageView image;
	private Text imagePath;
	private final FileChooser fileChooser = new FileChooser();

	private void initMenubar(Stage stage) {
		// Create File menu
		Menu fileMenu = new Menu("File");
		// Create Open Item in File menu
		MenuItem open = new MenuItem("Open");
		// open file picker on press
		open.setOnAction(e -> {
			File imageFile = fileChooser.showOpenDialog(stage);
			if (imageFile != null) {
				image.setImage(new Image(imageFile.toURI().toString()));
				imagePath.setText("Image path: " + imageFile.getAbsolutePath());
			}
		});
		// add items to File menu
		fileMenu.getItems().addAll(open);
		menuBar = new MenuBar(fileMenu);
	}

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title.
		primaryStage.setTitle("FX Picture Viewer");
		// Set the width.
		primaryStage.setWidth(500);
		// set the height
		primaryStage.setHeight(400);

		BorderPane mainLayout = new BorderPane();

		// Configure FileChooser to see .JPG images only
		fileChooser.setTitle("Choose Picture");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));

		// Create menu bar and menus
		initMenubar(primaryStage);

		imagePath = new Text("eifwiehwfj");

		image = new ImageView();
		image.setFitWidth(280);
		image.setPreserveRatio(true);
		
		HBox imageContainer = new HBox(image);
		imageContainer.setPadding(new Insets(20, 5, 20, 5));

		mainLayout.setTop(menuBar);
		mainLayout.setCenter(imageContainer);
		mainLayout.setBottom(imagePath);

		// Create a scene.
		Scene s = new Scene(mainLayout);
		// Set the scene.
		primaryStage.setScene(s);
		// Show application window
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		preparePrimaryStage(primaryStage);
	}

	public static void main(String[] args) {
		// Launch the application.
		launch();
	}
}
