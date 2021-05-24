package main.java.controllers.courier;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.java.SceneManager;
import main.java.controllers.auth.Login;
import main.java.dao.*;
import main.java.entity.Packages;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Animations;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Courier implements Initializable {

    boolean hamburgerClicked = false;
    @FXML
    private AnchorPane mainWindow;
    @FXML
    private VBox paneRight;
    @FXML
    private FontAwesomeIconView hamburger;
    @FXML
    private Pane welcomeMessage;
    @FXML
    private Pane alertPane;
    @FXML
    private AnchorPane window;

    @FXML
    private Text loggedUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);
        loggedUser.setText(ui.getName() + " " + ui.getSurname());
        List<Packages> packagesList = PackagesDAO.getPackagesWithoutCourierId();
        List<Users> usersList = UsersDAO.getCouriers("Kurier");
        for (int i = 0; i < packagesList.size(); i++) {
            for (int j = 0; j < usersList.size(); j++) {
                if (packagesList.get(i).getUsersByUserId().getUserInfosByUserInfoId().getVoivodeship().equals(usersList.get(j).getAreasByAreaId().getVoivodeship())) {
                    if (packagesList.get(i).getUsersByUserId().getUserInfosByUserInfoId().getCity().equals(usersList.get(j).getAreasByAreaId().getName())) {
                        List<Users> couriersInArea = UsersDAO.getCouriersByAreaId(usersList.get(j).getAreaId());
                        if(couriersInArea.size() > 1){
                            int courierId = couriersInArea.get(0).getId();
                            Long courierPackages = 999999L;
                            for(int k = 0; k < couriersInArea.size(); k++){
                                if(UsersDAO.getPackagesByCourier(couriersInArea.get(k).getId()) < courierPackages){
                                    courierPackages = UsersDAO.getPackagesByCourier(couriersInArea.get(k).getId());
                                    courierId = couriersInArea.get(k).getId();
                                }
                            }
                            PackagesDAO.updateCourierId(packagesList.get(i).getId(), courierId);
                        }else {
                            PackagesDAO.updateCourierId(packagesList.get(i).getId(), usersList.get(j).getId());
                        }
                        break;
                    }
                }
            }
        }

        paneRight.setTranslateX(-200);
        alertPane.setTranslateY(-500);

        hamburger.setOnMouseClicked(event -> {      // If hamburger button is clicked then menu slides in and transition last for 0.5s
            if (hamburgerClicked == false) {

                hamburger.setDisable(true);
                hamburgerClicked = true;
                paneRight.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();

                Animations.moveByX(paneRight, +200, 0.5);
                Animations.moveByX(welcomeMessage, +170, 0.5);
                Animations.moveByX(mainWindow, +70, 0.5);

                fadeTransition.setOnFinished(event1 -> {
                    hamburger.setDisable(false);
                });
            } else {
                hamburger.setDisable(true);
                hamburgerClicked = false;

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), paneRight);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.play();

                Animations.moveByX(paneRight, -200, 0.5);
                Animations.moveByX(welcomeMessage, -170, 0.5);
                Animations.moveByX(mainWindow, -70, 0.5);

                fadeTransition.setOnFinished(event1 -> {
                    paneRight.setVisible(false);
                    hamburger.setDisable(false);
                });
            }
        });
        try {
            SceneManager.loadScene("../../../resources/view/courier/courierHome.fxml", mainWindow);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method that opens home panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openHome(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierHome.fxml", mainWindow);
    }
    /**
     * method that opens main panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSecond(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSecond.fxml", mainWindow);
    }

    /**
     * method that opens settings panel after clicking button
     * @param actionEvent
     * @throws IOException
     */
    public void openSettings(ActionEvent actionEvent) throws IOException {
        SceneManager.loadScene("../../../resources/view/courier/courierSettings.fxml", mainWindow);
    }

    /**
     * method that blurs application and creating logout confirmation, that slides from top
     * @param event
     */
    @FXML
    void logout(ActionEvent event) {
        Animations.moveByY(alertPane, +500, 0.3);
        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(8);
        window.setDisable(true);
        window.setEffect(gaussianBlur);
    }

    /**
     * if button "no" is clicked, then logout confirmation slides to top and disappearing
     * @param event
     */
    @FXML
    void logoutNo(ActionEvent event) {
        Animations.moveByY(alertPane, -500, 0.3);
        window.setEffect(null);
        window.setDisable(false);
    }

    /**
     *  if button "yes" is clicked, then returning to login panel
     * @param event
     */
    @FXML
    void logoutYes(ActionEvent event) {
        SceneManager.renderScene("login");
    }
}
