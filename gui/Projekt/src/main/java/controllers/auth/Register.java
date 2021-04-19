package main.java.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;
import main.java.dao.UserInfosDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static main.java.dao.UserInfosDAO.getUsers;

public class Register {

    @FXML
    private AnchorPane loginRightPaneAnchorPane;

    @FXML
    private Button registerExitButtonButton;

    @FXML
    private Button registerReturnButtonButton;

    @FXML
    private Button registerRegisterButtonButton;

    @FXML
    private Circle registerFirstNameCircle;

    @FXML
    private TextField registerFirstNameField;

    @FXML
    private Circle registerLastNameCircle;

    @FXML
    private TextField registerLastNameField;

    @FXML
    private Circle registerPhoneNumberCircle;

    @FXML
    private TextField registerPhoneNumberField;

    @FXML
    private Circle registerEmailAddressCircle;

    @FXML
    private TextField registerEmailAddressField;

    @FXML
    private Circle registerStreetCircle;

    @FXML
    private TextField registerStreetField;

    @FXML
    private Circle registerCityCircle;

    @FXML
    private TextField registerCityField;

    @FXML
    private Circle registerVoivodeshipCircle;

    @FXML
    private TextField registerVoivodeshipField;

    @FXML
    private Circle registerPasswordCircle;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private Circle registerRepeatPasswordCircle;

    @FXML
    private PasswordField registerRepeatPasswordField;


    @FXML
    private Label registerFirstNameErrorExample;

    @FXML
    private Label registerLastNameErrorExample;

    @FXML
    private Label registerPhoneNumberErrorExample;

    @FXML
    private Label registerEmailErrorExample;

    @FXML
    private Label registerStreetErrorExample;

    @FXML
    private Label registerCityErrorExample;

    @FXML
    private Label registerVoivodeshipErrorExample;

    @FXML
    private Label registerSixCharsRequirement;

    @FXML
    private Label registerSmallLetterRequirement;

    @FXML
    private Label registerBigLetterRequirement;

    @FXML
    private Label registerNumberRequirement;

    @FXML
    private Label registerSpecialCharRequirement;

    @FXML
    private Label registerSamePasswordsRequirement;

    public void register(){
        registerRegisterButtonButton.setDisable(true);
        if(isValid(registerFirstNameField.getText(), registerLastNameField.getText(), registerPhoneNumberField.getText(), registerEmailAddressField.getText(), registerStreetField.getText(), registerCityField.getText(), registerVoivodeshipField.getText(), registerPasswordField.getText(), registerRepeatPasswordField.getText())){
            boolean emailExists = false;

//            List<Users> listOfUsers = getUsers();
//            for(int i = 0; i < getUsers().size(); i++){
//                if(registerEmailAddressField.getText().equals(listOfUsers.get(i).getEmail())) {
//                    emailExists = true;
//                }
//            }

            if(!emailExists){
                String firstName = registerFirstNameField.getText();
                String lastName = registerLastNameField.getText();
                String email = registerEmailAddressField.getText();
                String phoneNumber = registerPhoneNumberField.getText();
                String street = registerStreetField.getText();
                String city = registerCityField.getText();
                String voivodeship = registerVoivodeshipField.getText();
                String password = registerPasswordField.getText();

                UserInfosDAO.addUserInfo(firstName, lastName, email, phoneNumber, street, city, voivodeship, password);

                //REJESTRACJA POMYSLNA
                System.out.println("Zarejestrowano");
                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"CHECK","Zarejestrowano pomyślnie", 293, 86, "alertSuccess");
                SceneManager.renderScene("login");
            }else{
                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany adres email jest już w użyciu", 350, 86, "alertFailure");
                errorOnEmailAddress();
                registerRegisterButtonButton.setDisable(false);
            }
            registerRegisterButtonButton.setDisable(false);
        }
    }

    private boolean isValid(String firstName, String lastName, String phoneNumber, String email, String street, String city, String voivodeship, String password, String password2){
        boolean firstNameError = false, lastNameError = false, phoneNumberError = false, emailError = false, streetError = false, cityError = false, voivodeshipError = false, passwordError = false, passwordNotTheSameError = false;
        int error = 0;
        Pattern pattern = Pattern.compile("[A-Za-z]{2,60}");

        Pattern patternFirstName = Pattern.compile("[A-Za-z]{2,30}\\s?[A-Za-z]{2,30}");

        Pattern patternLastName = Pattern.compile("[A-Za-z]{2,30}\\s?\\-\\s?[A-Za-z]{2,30}");

        Pattern patternMobilePhoneNumber = Pattern.compile("\\+?[0-9]{0,2}\\s?[0-9]{3}\\s?[0-9]{3}\\s?[0-9]{3}");
        Pattern patternPhoneNumber = Pattern.compile("[0-9]{3}\\s?[0-9]{2}\\s?[0-9]{2}\\s?[0-9]{2}");

        Pattern patternEmail = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");

        Pattern patternStreet = Pattern.compile("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}");

        Pattern patternCity = Pattern.compile("[A-Za-z]{2,40}\\s?\\-?\\s?[A-Za-z]{0,40}\\s?\\-?\\s?[A-Za-z]{0,40}");

        Pattern patternVoivodeship = Pattern.compile("[A-Za-z]{7,40}\\s?\\-?\\s?[A-Za-z]{0,40}");

        Pattern patternPassword = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

        Matcher matchSingleFirstName = pattern.matcher(firstName);
        Matcher matchDoubleFirstName = patternFirstName.matcher(firstName);

        Matcher matchSingleLastName = pattern.matcher(lastName);
        Matcher matchDoubleLastName = patternLastName.matcher(lastName);

        Matcher matchMobilePhoneNumber = patternMobilePhoneNumber.matcher(phoneNumber);
        Matcher matchPhoneNumber = patternPhoneNumber.matcher(phoneNumber);

        Matcher matchEmail = patternEmail.matcher(email);

        Matcher matchStreet = patternStreet.matcher(street);

        Matcher matchCity = patternCity.matcher(city);

        Matcher matchVoivodeship = patternVoivodeship.matcher(voivodeship);

        Matcher matchPassword = patternPassword.matcher(password);

        if(!matchSingleFirstName.matches() && !matchDoubleFirstName.matches()){
            errorOnFirstName();
            firstNameError = true;
            error++;
        }
        if(!matchSingleLastName.matches() && !matchDoubleLastName.matches()){
            errorOnLastName();
            lastNameError = true;
            error++;
        }
        if(!matchPhoneNumber.matches() && !matchMobilePhoneNumber.matches()){
            errorOnPhoneNumber();
            phoneNumberError = true;
            error++;
        }
        if(!matchEmail.matches()){
            errorOnEmailAddress();
            emailError = true;
            error++;
        }
        if(!matchStreet.matches()){
            errorOnStreet();
            streetError = true;
            error++;
        }
        if(!matchCity.matches()){
            errorOnCity();
            cityError = true;
            error++;
        }
        if(!matchVoivodeship.matches()){
            errorOnVoivodeship();
            voivodeshipError = true;
            error++;
        }
        if(!matchPassword.matches()){
            errorOnPassword();
            passwordError = true;
            errorOnConfirmPassword();
            error++;
        }else{
            if(!password.equals(password2)){
                errorOnConfirmPassword();
                passwordNotTheSameError = true;
                error++;
            }
        }

        if(error <= 0){
            return true;
        }else if(error == 1){
            if(firstNameError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format imienia jest błędny", 350, 86, "alertFailure");
            }
            if(lastNameError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format nazwiska jest błedny", 350, 86, "alertFailure");
            }
            if(phoneNumberError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format numeru telefonu jest błędny", 390, 86, "alertFailure");
            }
            if(emailError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format adresu email jest błędny", 350, 86, "alertFailure");
            }
            if(streetError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format ulicy jest błędny", 350, 86, "alertFailure");
            }
            if(cityError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format miasta jest błędny", 350, 86, "alertFailure");
            }
            if(voivodeshipError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format województwa jest błędny", 365, 86, "alertFailure");
            }
            if(passwordError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Podany format hasła jest błędny", 350, 86, "alertFailure");
            }
            if(passwordNotTheSameError){

                Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Hasła nie są takie same", 350, 86, "alertFailure");
            }
            return false;
        }else{

            Alerts.createCustomAlert(loginRightPaneAnchorPane, registerReturnButtonButton,"WARNING","Popraw błędy na zaznaczonych polach", 350, 86, "alertFailure");
            return false;
        }
    }

    public void handleRegister(MouseEvent mouseEvent) {
        register();
    }

    public void handleRegisterOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            register();
        }
    }

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) registerExitButtonButton.getScene().getWindow();
        stage.close();
    }
    //FIRST NAME
    private void errorOnFirstName(){
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFieldsError");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("fillError");

        registerFirstNameErrorExample.setVisible(true);
    }

    public void clearErrorsOnFirstName(KeyEvent keyEvent) {
        //FirstNameField
        registerFirstNameField.getStyleClass().clear();
        registerFirstNameField.getStyleClass().add("textFields");
        //FirstNameCircle
        registerFirstNameCircle.getStyleClass().clear();
        registerFirstNameCircle.getStyleClass().add("fill");

        registerFirstNameErrorExample.setVisible(false);
    }
    //LAST NAME
    private void errorOnLastName(){
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFieldsError");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("fillError");

        registerLastNameErrorExample.setVisible(true);
    }

    public void clearErrorsOnLastName(KeyEvent keyEvent) {
        //LastNameField
        registerLastNameField.getStyleClass().clear();
        registerLastNameField.getStyleClass().add("textFields");
        //LastNameCircle
        registerLastNameCircle.getStyleClass().clear();
        registerLastNameCircle.getStyleClass().add("fill");

        registerLastNameErrorExample.setVisible(false);
    }
    //PHONE NUMBER
    private void errorOnPhoneNumber(){
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFieldsError");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("fillError");

        registerPhoneNumberErrorExample.setVisible(true);
    }

    public void clearErrorsOnPhoneNumber(KeyEvent keyEvent) {
        //PhoneNumberField
        registerPhoneNumberField.getStyleClass().clear();
        registerPhoneNumberField.getStyleClass().add("textFields");
        //PhoneNumberCircle
        registerPhoneNumberCircle.getStyleClass().clear();
        registerPhoneNumberCircle.getStyleClass().add("fill");

        registerPhoneNumberErrorExample.setVisible(false);
    }
    //EMAIL ADDRESS
    private void errorOnEmailAddress(){
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFieldsError");
        //EmailAddressCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("fillError");

        registerEmailErrorExample.setVisible(true);
    }

    public void clearErrorsOnEmailAddress(KeyEvent keyEvent) {
        //EmailAddressField
        registerEmailAddressField.getStyleClass().clear();
        registerEmailAddressField.getStyleClass().add("textFields");
        //EmailAddressFCircle
        registerEmailAddressCircle.getStyleClass().clear();
        registerEmailAddressCircle.getStyleClass().add("fill");

        registerEmailErrorExample.setVisible(false);
    }
    //STREET
    private void errorOnStreet(){
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFieldsError");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("fillError");

        registerStreetErrorExample.setVisible(true);
    }

    public void clearErrorsOnStreet(KeyEvent keyEvent) {
        //StreetField
        registerStreetField.getStyleClass().clear();
        registerStreetField.getStyleClass().add("textFields");
        //StreetCircle
        registerStreetCircle.getStyleClass().clear();
        registerStreetCircle.getStyleClass().add("fill");

        registerStreetErrorExample.setVisible(false);
    }
    //CITY
    private void errorOnCity(){
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFieldsError");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("fillError");

        registerCityErrorExample.setVisible(true);
    }

    public void clearErrorsOnCity(KeyEvent keyEvent) {
        //CityField
        registerCityField.getStyleClass().clear();
        registerCityField.getStyleClass().add("textFields");
        //CityCircle
        registerCityCircle.getStyleClass().clear();
        registerCityCircle.getStyleClass().add("fill");

        registerCityErrorExample.setVisible(false);
    }
    //VOIVODESHIP
    private void errorOnVoivodeship(){
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("fillError");

        registerVoivodeshipErrorExample.setVisible(true);
    }

    public void clearErrorsOnVoivodeship(KeyEvent keyEvent) {
        //VoivodeshipField
        registerVoivodeshipField.getStyleClass().clear();
        registerVoivodeshipField.getStyleClass().add("textFields");
        //VoivodeshipCircle
        registerVoivodeshipCircle.getStyleClass().clear();
        registerVoivodeshipCircle.getStyleClass().add("fill");

        registerVoivodeshipErrorExample.setVisible(false);
    }
    //PASSWORD
    private void errorOnPassword(){
        //PasswordField
        registerPasswordField.getStyleClass().clear();
        registerPasswordField.getStyleClass().add("textFieldsError");
        //VoivodeshipCircle
        registerPasswordCircle.getStyleClass().clear();
        registerPasswordCircle.getStyleClass().add("fillError");
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //PasswordField
        registerPasswordField.getStyleClass().clear();
        registerPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        registerPasswordCircle.getStyleClass().clear();
        registerPasswordCircle.getStyleClass().add("fill");
    }
    //REPEAT PASSWORD
    private void errorOnConfirmPassword(){
        //RepeatPasswordField
        registerRepeatPasswordField.getStyleClass().clear();
        registerRepeatPasswordField.getStyleClass().add("textFieldsError");
        //RepeatVoivodeshipCircle
        registerRepeatPasswordCircle.getStyleClass().clear();
        registerRepeatPasswordCircle.getStyleClass().add("fillError");
    }

    public void clearErrorsOnRepeatPassword(KeyEvent keyEvent) {
        //RepeatPasswordField
        registerRepeatPasswordField.getStyleClass().clear();
        registerRepeatPasswordField.getStyleClass().add("textFields");
        //RepeatPasswordCircle
        registerRepeatPasswordCircle.getStyleClass().clear();
        registerRepeatPasswordCircle.getStyleClass().add("fill");
    }

    private void passwordRequirements(){
        Pattern sixChars = Pattern.compile(".{6,}");
        Pattern smallLetter = Pattern.compile(".*[a-z]+.*");
        Pattern bigLetter = Pattern.compile(".*[A-Z]+.*");
        Pattern number = Pattern.compile(".*[0-9]+.*");
        Pattern specialChar = Pattern.compile(".*[!@#$%^&*()_\\-+=]+.*");

        Matcher matchSixChars = sixChars.matcher(registerPasswordField.getText());
        Matcher matchSmallLetter = smallLetter.matcher(registerPasswordField.getText());
        Matcher matchBigLetter = bigLetter.matcher(registerPasswordField.getText());
        Matcher matchNumber = number.matcher(registerPasswordField.getText());
        Matcher matchSpecialChar = specialChar.matcher(registerPasswordField.getText());

        if(matchSixChars.matches()){
            registerSixCharsRequirement.getStyleClass().clear();
            registerSixCharsRequirement.getStyleClass().add("successText");
        }else{
            registerSixCharsRequirement.getStyleClass().clear();
            registerSixCharsRequirement.getStyleClass().add("errorText");
        }

        if(matchSmallLetter.matches()){
            registerSmallLetterRequirement.getStyleClass().clear();
            registerSmallLetterRequirement.getStyleClass().add("successText");
        }else{
            registerSmallLetterRequirement.getStyleClass().clear();
            registerSmallLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchBigLetter.matches()){
            registerBigLetterRequirement.getStyleClass().clear();
            registerBigLetterRequirement.getStyleClass().add("successText");
        }else{
            registerBigLetterRequirement.getStyleClass().clear();
            registerBigLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchNumber.matches()){
            registerNumberRequirement.getStyleClass().clear();
            registerNumberRequirement.getStyleClass().add("successText");
        }else{
            registerNumberRequirement.getStyleClass().clear();
            registerNumberRequirement.getStyleClass().add("errorText");
        }

        if(matchSpecialChar.matches()){
            registerSpecialCharRequirement.getStyleClass().clear();
            registerSpecialCharRequirement.getStyleClass().add("successText");
        }else{
            registerSpecialCharRequirement.getStyleClass().clear();
            registerSpecialCharRequirement.getStyleClass().add("errorText");
        }

        if(registerRepeatPasswordField.getText().equals(registerPasswordField.getText())){
            registerSamePasswordsRequirement.getStyleClass().clear();
            registerSamePasswordsRequirement.getStyleClass().add("successText");
        }else{
            registerSamePasswordsRequirement.getStyleClass().clear();
            registerSamePasswordsRequirement.getStyleClass().add("errorText");
        }
    }

    public void checkRequirements(KeyEvent keyEvent) {
        passwordRequirements();
    }


}
