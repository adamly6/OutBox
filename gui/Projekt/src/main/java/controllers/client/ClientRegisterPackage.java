package main.java.controllers.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import main.java.controllers.animations.Alerts;
import main.java.controllers.animations.Animations;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientRegisterPackage implements Initializable {

    @FXML
    private Button btnNextRecipient;

    @FXML
    private Button btnNextTime;

    @FXML
    private Button btnBackSize;

    @FXML
    private AnchorPane packageSizePane;

    @FXML
    private ToggleButton smallPackage;

    @FXML
    private ToggleButton mediumPackage;

    @FXML
    private ToggleButton bigPackage;

    @FXML
    private AnchorPane recipientDetailsPane;

    @FXML
    private AnchorPane deliveryTimePane;

    @FXML
    private AnchorPane registerSummaryPane;

    @FXML
    private Circle navCircle;

    @FXML
    private AnchorPane appWindow;


    private ToggleGroup packageGroup = new ToggleGroup();

    @FXML
    private Pane alertPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //alertPane.setTranslateY(-400);

        // Added three buttons to the group
        smallPackage.setToggleGroup(packageGroup);
        smallPackage.setUserData("small");

        mediumPackage.setToggleGroup(packageGroup);
        mediumPackage.setUserData("medium");

        bigPackage.setToggleGroup(packageGroup);
        bigPackage.setUserData("big");

        recipientDetailsPane.setTranslateX(+800);           // After panel is initialized three panes are moved 800 pixels to the right
        deliveryTimePane.setTranslateX(+800);               // for animation purposes
        registerSummaryPane.setTranslateX(+800);
    }



    // Button actions that leads forward
    @FXML
    void fromSizeToRecipient(ActionEvent event) {
        if(packageGroup.getSelectedToggle() != null){
            System.out.println(packageGroup.getSelectedToggle().getUserData().toString());
            Animations.changePane(packageSizePane,recipientDetailsPane,-800,0.7);
            Animations.moveByX(navCircle, +114,0.7);
        }
        else{
            //Animations.moveAndWaitByY(alertPane,400,0.3,3,btnNextRecipient);
            Alerts.createAlert(appWindow,293,86,0,-600,btnNextRecipient,"WARNING","WYBIERZ ROZMIAR PACZKI");
        }

    }

    @FXML
    void fromRecipientToTime(ActionEvent event) {
        Animations.changePane(recipientDetailsPane,deliveryTimePane,-800,0.7);
        Animations.moveByX(navCircle,+114,0.7);
    }

    @FXML
    void fromTimeToSummary(ActionEvent event) {
        Animations.changePane(deliveryTimePane,registerSummaryPane,-800,0.7);
        Animations.moveByX(navCircle,+114,0.7);
    }

    // Button actions that leads backward
    @FXML
    void fromRecipientToSize(ActionEvent event) {
        Animations.changePane(recipientDetailsPane,packageSizePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    @FXML
    void fromTimeToRecipient(ActionEvent event) {
        Animations.changePane(deliveryTimePane,recipientDetailsPane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }

    @FXML
    void fromSummaryToTime(ActionEvent event) {
        Animations.changePane(registerSummaryPane,deliveryTimePane,+800,0.7);
        Animations.moveByX(navCircle,-114,0.7);
    }
}
