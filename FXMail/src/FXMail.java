import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FXMail extends Application {
    private TextField fromInput;
    private TextField subjectInput;
    private TextField toInput;
    private Button addressBook;
    private TextArea emailContent;
    private String fileName = "src/addressBook.csv";
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private ArrayList<String> readAddressBook() throws IOException {
        // Read the address book csv files in a new ArrayList of String
        return Files.lines(Paths.get(fileName)).collect(Collectors.toCollection(ArrayList::new));
    }

    private boolean checkValidFormat(String email) {
        return emailPattern.matcher(email).matches();
    }

    private void showAddressBook() throws IOException {
        // Read the address book
        ArrayList<String> addressBook = readAddressBook();
        // Create a new stage for the dialog
        Stage addressBookStage = new Stage();
        // set the width ,height and other parameters.
        addressBookStage.setWidth(300);
        addressBookStage.setHeight(250);
        addressBookStage.setTitle("Address Book");
        // Make sure the main window is not clickable until the dialog is closed
        addressBookStage.initModality(Modality.APPLICATION_MODAL);
        // The dialog is not resizable
        addressBookStage.setResizable(false);
        // the dialog should have focus.
        addressBookStage.setAlwaysOnTop(true);
        // Create a VBox layout
        VBox vBox = new VBox();
        // Create the contacts ListView
        ListView<String> listView = new ListView<>();
        // Loop on every contact and add it to the address book
        for (String contact : addressBook) {
            listView.getItems().add(contact);
        }
        // Add contacts list to vBox
        vBox.getChildren().add(listView);
        Text err = new Text("Email format is not valid");
        err.setFill(Color.RED);
        err.setVisible(false);
        vBox.getChildren().add(err);
        // Create a horizontal box layout (HBox)
        HBox hBox = new HBox(10);
        // Add padding to center it
        hBox.setPadding(new Insets(10));
        // Set right alignment
        hBox.setAlignment(Pos.CENTER_RIGHT);
        // Create ok Button
        Button ok = new Button("Ok");
        // Add action on clicking ok Button
        ok.setOnAction(e -> {
            if (listView.getSelectionModel().getSelectedItems().get(0) != null) {
                // Check if the selected contact has a valid email
                if (checkValidFormat(((String) listView.getSelectionModel().getSelectedItems().get(0)).split(",")[1].substring(1))) {
                    // Add the selected contact email to the to TextField
                    this.toInput.setText(((String) listView.getSelectionModel().getSelectedItems().get(0)).split(",")[1]);
                    // Close the dialog
                    addressBookStage.close();
                } else {
                    err.setVisible(true);
                }
            } else {
                // Close the dialog
                addressBookStage.close();
            }
        });
        // Add ok button to HBox
        hBox.getChildren().add(ok);
        // Create cancel Button
        Button cancel = new Button("Cancel");
        // Add action on clicking cancel Button
        cancel.setOnAction(e -> {
            // Close the dialog
            addressBookStage.close();
        });
        // Add cancel button to HBox
        hBox.getChildren().add(cancel);
        // Add HBox to vBox
        vBox.getChildren().add(hBox);
        // create a scene .
        Scene s = new Scene(vBox);
        // Set the scene.
        addressBookStage.setScene(s);
        // Show the stage
        addressBookStage.show();
    }

    private void preparePrimaryStage(Stage primaryStage) {
        // Set the title.
        primaryStage.setTitle("FXMail V1.0");
        // Set the width.
        primaryStage.setWidth(500);
        // set the height
        primaryStage.setHeight(500);
        // Create a VBox layout
        VBox vBox = new VBox(30);
        vBox.setPadding(new Insets(30, 15, 30, 15));
        // Create the upper fields GridPane
        GridPane gridPane = new GridPane();
        // Set horizontal and vertical gaps between components in the GridPane
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // Create from text
        Text from = new Text("From: ");
        // Create from TextField
        fromInput = new TextField();
        fromInput.setPrefWidth(225);
        // Create subject text
        Text subject = new Text("Subject: ");
        // Create subject TextField
        subjectInput = new TextField();
        subjectInput.setPrefWidth(225);
        // Create from text
        Text to = new Text("To: ");
        // Create from TextField
        toInput = new TextField();
        toInput.setPrefWidth(225);
        // Create addressBook Button
        addressBook = new Button("...");
        // Add action to addressBook to open the address book dialog
        addressBook.setOnAction(e -> {
            try {
                showAddressBook();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        // Add the components to the GridPane in the order of the screenshot
        gridPane.add(from, 0, 0);
        gridPane.add(fromInput, 1, 0);
        gridPane.add(subject, 0, 1);
        gridPane.add(subjectInput, 1, 1);
        gridPane.add(to, 0, 2);
        gridPane.add(toInput, 1, 2);
        gridPane.add(addressBook, 2, 2);
        // Add the GridPane to the VBox
        vBox.getChildren().add(gridPane);

        // Instantiate a new email TextArea
        emailContent = new TextArea();
        // Set TextArea height to be like screenshot
        emailContent.setPrefHeight(250);
        // Add email TextArea to the VBox
        vBox.getChildren().add(emailContent);

        // Create a HBox layout
        HBox hBox = new HBox(15);
        // Set the HBox to have right alignment for the buttons to be on the right
        hBox.setAlignment(Pos.CENTER_RIGHT);
        // Create quit Button
        Button quit = new Button("Quit");
        // Set quit Button to have 75 width to be like screenshot
        quit.setPrefWidth(75);
        // Create send Button
        Button send = new Button("Send");
        // Set send Button to have 75 width to be like screenshot
        send.setPrefWidth(75);
        // Add buttons to HBox
        hBox.getChildren().add(quit);
        hBox.getChildren().add(send);
        // Add HBox to VBox
        vBox.getChildren().add(hBox);

        // Create Ready text
        Text ready = new Text("Ready");
        // Add ready Text to VBox
        vBox.getChildren().add(ready);

        // Create a scene.
        Scene s = new Scene(vBox);
        // Set the scene.
        primaryStage.setScene(s);
        // Show application window
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        preparePrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        // Launch the application.
        launch();
    }
}
