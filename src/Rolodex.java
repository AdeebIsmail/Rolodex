import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Properties;

public class Rolodex extends Application implements java.io.Serializable {
    Contact prev;

    ListView<Button> contactList = new ListView<>();

    ArrayList<Contact> myContacts = new ArrayList<>();

    ArrayList<Contact> myContactsInfo = new ArrayList<>();

    VBox contactBox = new VBox();

    VBox buttonBox = new VBox();

    VBox textFields = new VBox();

    VBox labelBox = new VBox();

    TextField firstName = new TextField();

    TextField lastName = new TextField();

    TextField phoneNumber = new TextField();

    TextField address = new TextField();

    Label firstLabel = new Label("First Name");

    Label lastLabel = new Label("Last Name");

    Label phoneNumberLabel = new Label("Phone Number");

    Label addressLabel = new Label("Address");

    Button deleteContact = new Button("Delete");

    Button saveContact = new Button("Save");

    Button newButton = new Button("New");
    EventHandler selectContact = event -> {
        Contact c;

        Button source = (Button) event.getSource();

        contactList.getSelectionModel().select(contactList.getItems().indexOf(source));
        contactList.getFocusModel().focus(contactList.getItems().indexOf(source));

        Contact contactButton = (Contact) source.getUserData();

        c = new Contact(contactButton.getFirstName(), contactButton.getLastName(), contactButton.getPhoneNumber(), contactButton.getAddress());

        displayContactInfo(c);

    };
    EventHandler deleteButtonAction = event -> {
        Contact c = new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), address.getText());
        for (int i = 0; i < myContactsInfo.size(); i++) {
            if (myContactsInfo.get(i).compareTo(c) == 1) {
                myContactsInfo.remove(i);

                contactList.getItems().remove(i);

                firstName.clear();
                lastName.clear();
                phoneNumber.clear();
                address.clear();

                displayContact();

                contactList.getSelectionModel().clearSelection();

                break;
            }

        }
        if (myContactsInfo.size() == 0) {
            newButton.setVisible(true);
            newButton.setDisable(false);

            deleteContact.setVisible(false);
            deleteContact.setDisable(true);
        }


    };
    EventHandler saveButtonAction = event -> {
        Contact newC = new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), address.getText());
        if (newC.getFirstName().isEmpty() || newC.getLastName().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Missing Fields").showAndWait();
        } else if (myContactsInfo.size() == 0) {
            addContact(new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), address.getText()));

            firstName.clear();
            lastName.clear();
            phoneNumber.clear();
            address.clear();
        } else if (prev != null && newC.compareTo(prev) == 0) {
            for (int i = 0; i < myContactsInfo.size(); i++) {
                if (myContactsInfo.get(i).compareTo(prev) == 1) {
                    Button b = contactList.getItems().get(i);

                    b.setText(newC.getFirstName() + "," + newC.getLastName());
                    b.setUserData(newC);

                    contactList.getItems().get(i).setUserData(b.getUserData());

                    myContactsInfo.set(i, newC);

                    newButton.setVisible(true);
                    newButton.setDisable(false);

                    deleteContact.setVisible(false);
                    deleteContact.setDisable(true);

                    firstName.clear();
                    lastName.clear();
                    phoneNumber.clear();
                    address.clear();

                    break;
                }

            }
            prev = null;
        } else if (prev != null && newC.compareTo(prev) == 1) {
            newButton.setVisible(true);
            newButton.setDisable(false);

            buttonBox.getChildren().add(newButton);

            deleteContact.setVisible(false);
            deleteContact.setDisable(true);

            firstName.clear();

            lastName.clear();

            phoneNumber.clear();

            address.clear();

            prev = null;
        } else if (!firstName.getText().equals(" ") && !lastName.getText().equals(" ") && !phoneNumber.getText().equals(" ") && !address.getText().equals(" ")) {
            addContact(new Contact(firstName.getText(), lastName.getText(), phoneNumber.getText(), address.getText()));

            firstName.clear();
            lastName.clear();
            phoneNumber.clear();
            address.clear();
        }

        displayContact();

        contactList.getSelectionModel().clearSelection();


    };
    EventHandler clearFields = event -> {
        firstName.clear();
        lastName.clear();
        phoneNumber.clear();
        address.clear();
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();

        saveContact.setOnAction(saveButtonAction);

        deleteContact.setOnAction(deleteButtonAction);

        newButton.setOnAction(clearFields);

        deleteContact.setDisable(true);
        deleteContact.setVisible(false);

        deleteContact.setPrefSize(75, 25);

        saveContact.setPrefSize(75, 25);

        newButton.setPrefSize(75, 25);

        buttonBox.getChildren().addAll(saveContact, newButton);
        buttonBox.setPadding(new Insets(300, 10, 10, 400));

        labelBox.getChildren().add(firstLabel);
        firstLabel.setPadding(new Insets(10, 10, 10, 0));
        labelBox.getChildren().add(lastLabel);
        lastLabel.setPadding(new Insets(10, 10, 10, 0));
        labelBox.getChildren().add(phoneNumberLabel);
        phoneNumberLabel.setPadding(new Insets(10, 10, 10, 0));
        labelBox.getChildren().add(addressLabel);
        addressLabel.setPadding(new Insets(10, 10, 10, 0));
        labelBox.setPadding(new Insets(15, 10, 10, 280));

        textFields.getChildren().add(firstName);
        firstName.setPadding(new Insets(10, 10, 10, 50));
        textFields.getChildren().add(lastName);
        lastName.setPadding(new Insets(10, 10, 10, 50));
        textFields.getChildren().add(phoneNumber);
        phoneNumber.setPadding(new Insets(10, 10, 10, 50));
        textFields.getChildren().add(address);
        address.setPadding(new Insets(10, 10, 10, 50));
        textFields.setPadding(new Insets(15, 10, 10, 400));
        contactBox.getChildren().add(contactList);


        group.getChildren().addAll(labelBox, buttonBox, textFields, contactBox);

        Scene scene = new Scene(group, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setTitle("K1218470");


    }

    public void addContact(Contact c) {
        myContacts.add(c);
    }

    public void displayContact() {
        buttonBox.getChildren().remove(deleteContact);
        if (!buttonBox.getChildren().contains(newButton)) {
            buttonBox.getChildren().add(newButton);
        }
        for (int i = 0; i < myContacts.size(); i++) {
            contactList.getItems().add(new Button(myContacts.get(i).getFirstName() + "," + myContacts.get(i).getLastName()));

            if (contactList.getItems().size() == 1) {
                contactList.getItems().get(0).setUserData(myContacts.get(i));
                contactList.getItems().get(0).setStyle("-fx-background-color: transparent;");
                contactList.getItems().get(0).setPrefSize(235, 2);
            } else {
                contactList.getItems().get(contactList.getItems().size() - 1).setUserData(myContacts.get(i));
                contactList.getItems().get(contactList.getItems().size() - 1).setPrefSize(235, 2);
                contactList.getItems().get(contactList.getItems().size() - 1).setStyle("-fx-background-color: transparent;");
            }

            myContactsInfo.add(myContacts.get(i));
            myContacts.clear();

        }
        for (Node child : contactList.getItems()) {
            child.setOnMouseClicked(selectContact);
        }
    }

    public void displayContactInfo(Contact c) {
        prev = new Contact(c.getFirstName(), c.getLastName(), c.getPhoneNumber(), c.getAddress());

        newButton.setVisible(false);
        newButton.setDisable(true);

        buttonBox.getChildren().remove(newButton);

        deleteContact.setVisible(true);
        deleteContact.setDisable(false);

        if (!buttonBox.getChildren().contains(deleteContact)) {
            buttonBox.getChildren().add(deleteContact);
        }

        firstName.setText(c.getFirstName());

        lastName.setText(c.getLastName());

        phoneNumber.setText(c.getPhoneNumber());

        address.setText(c.getAddress());
    }


}