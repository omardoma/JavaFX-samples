import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {
	private RadioButton truck;
	private RadioButton car;
	private TextField make;
	private TextField model;
	private ChoiceBox<String> colour;
	private TextField regNum;
	private TextArea details;
	private Button create;
	private Button showDetails;
	private Button quit;
	private Vehicle vehicle;

	private void preparePrimaryStage(Stage primaryStage) {
		// Set the title
		primaryStage.setTitle("Vehicles");
		// Set the width.
		primaryStage.setWidth(500);
		// set the height
		primaryStage.setHeight(450);

		VBox mainLayout = new VBox(15);
		mainLayout.setPadding(new Insets(15, 15, 15, 15));

		HBox row1 = new HBox(30);
		Text typeText = new Text("Type: ");
		// Create the options radio buttons
		final ToggleGroup group = new ToggleGroup();
		truck = new RadioButton("Truck");
		truck.setToggleGroup(group);
		truck.setSelected(true);
		car = new RadioButton("Car");
		car.setToggleGroup(group);
		car.setPadding(new Insets(0, 0, 0, 100));
		row1.getChildren().addAll(typeText, truck, car);

		HBox row2 = new HBox(35);
		// Create make text
		Text makeText = new Text("Make: ");
		// Create make Text TextField
		make = new TextField();
		row2.getChildren().addAll(makeText, make);

		HBox row3 = new HBox(30);
		// Create model text
		Text modelText = new Text("Model: ");
		// Create model TextField
		model = new TextField();
		row3.getChildren().addAll(modelText, model);

		HBox row4 = new HBox(27.5);
		// Create colour text
		Text colourText = new Text("Colour: ");
		// Create colour choice box
		colour = new ChoiceBox<>();
		colour.setPrefWidth(90);
		colour.getItems().addAll("Red", "Green", "Blue", "Black", "White");
		colour.getSelectionModel().selectFirst();
		row4.getChildren().addAll(colourText, colour);

		HBox row5 = new HBox(12.5);
		// Create regNum text
		Text regNumText = new Text("Reg Num: ");
		// Create regNum TextField
		regNum = new TextField();
		regNum.textProperty().addListener(new ChangeListener<String>() {

			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.trim().equals("")) {
					create.setDisable(true);
				} else {
					create.setDisable(false);
				}
			}
		});
		row5.getChildren().addAll(regNumText, regNum);

		HBox row6 = new HBox();
		// Create close Button
		create = new Button("Create");
		create.setDisable(true);
		create.setOnAction(e -> {
			if (truck.isSelected()) {
				vehicle = new Truck(regNum.getText());
			} else {
				vehicle = new Car(regNum.getText());
			}
			vehicle.setMake(make.getText());
			vehicle.setModel(model.getText());
			vehicle.setColour(colour.getSelectionModel().getSelectedItem());
		});
		// Add buttons to HBox
		row6.getChildren().add(create);

		HBox row7 = new HBox();
		details = new TextArea();
		details.setWrapText(true);
		details.setEditable(false);
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(details);
		scrollPane.setFitToWidth(true);
		row7.getChildren().add(scrollPane);

		HBox buttonsRow = new HBox(5);
		// Set the HBox to have right alignment for the buttons to be on the right
		buttonsRow.setAlignment(Pos.CENTER_RIGHT);
		showDetails = new Button("Show Details");
		showDetails.setOnAction(e -> {
			if (vehicle != null) {
				details.setText((vehicle instanceof Car ? "Car Details:\n" : "Truck Details: \n") + vehicle.getRegNum()
						+ "\n" + vehicle.getMake() + "\n" + vehicle.getModel() + "\n" + vehicle.getColour());
			}
		});
		// Create quit Button
		quit = new Button("Quit");
		quit.setPrefWidth(70);
		quit.setOnAction(e -> {
			Platform.exit();
		});
		// Add buttons to HBox
		buttonsRow.getChildren().addAll(showDetails, quit);

		mainLayout.getChildren().addAll(row1, row2, row3, row4, row5, row6, row7, buttonsRow);

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
