import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GUI extends Application {
	private TextField email;
	private TextArea address;
	private Button close;
	private String fileName = "src/contacts.csv";

	private ArrayList<String> readAddressBook() throws IOException {
		// Read the address book csv files in a new ArrayList of String
		return Files.lines(Paths.get(fileName)).collect(Collectors.toCollection(ArrayList::new));
	}

	private void preparePrimaryStage(Stage primaryStage) throws IOException {
		// Set the title
		primaryStage.setTitle("Contact Manager");
		// Set the width.
		primaryStage.setWidth(500);
		// set the height
		primaryStage.setHeight(350);
		
		VBox mainLayout = new VBox(15);
		mainLayout.setPadding(new Insets(15, 15, 15, 15));

		HBox hBox = new HBox(15);

		// Create a VBox layout
		VBox vBox1 = new VBox(5);

		Text contactNames = new Text("Contact names: ");

		// Read the address book
		ArrayList<String> addressBook = readAddressBook();

		// Create the contacts ListView
		ListView<String> listView = new ListView<>();
		listView.setOnMouseClicked(e -> {
			String[] tmp = addressBook.get(listView.getSelectionModel().getSelectedIndex()).split(":");
			email.setText(tmp[1]);
			address.setText(tmp[2]);
		});
		
		// Loop on every contact and add it to the address book
		for (String contact : addressBook) {
			listView.getItems().add(contact.split(":")[0]);
		}
		
		// Add the contacts as sorted ascendingly
		listView.setItems(listView.getItems().sorted());
		
		// Add contacts list to vBox
		vBox1.getChildren().addAll(contactNames, listView);
		
		hBox.getChildren().add(vBox1);
		
		// Create a VBox layout
		VBox vBox2 = new VBox(5);

		// Create email address text
		Text emailText = new Text("Email address: ");
		// Create from TextField
		email = new TextField();
		// Create address text
		Text addressText = new Text("Address: ");
		// Create address TextField
		address = new TextArea();

		vBox2.getChildren().addAll(emailText, email, addressText, address);
		
		hBox.getChildren().add(vBox2);
		
		mainLayout.getChildren().add(hBox);

		HBox buttonsRow = new HBox();
		// Set the HBox to have right alignment for the buttons to be on the right
		buttonsRow.setAlignment(Pos.CENTER_RIGHT);
		// Create close Button
		close = new Button("Close");
		close.setPrefWidth(90);;
		close.setOnAction(e -> {
			Platform.exit();
		});
		// Add buttons to HBox
		buttonsRow.getChildren().add(close);
		// Add HBox to VBox
		mainLayout.getChildren().add(buttonsRow);

		// Create a scene.
		Scene s = new Scene(mainLayout);
		// Set the scene.
		primaryStage.setScene(s);
		// Show application window
		primaryStage.show();
	}

	public void start(Stage primaryStage) {
		try {
			preparePrimaryStage(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Launch the application.
		launch();
	}
}
