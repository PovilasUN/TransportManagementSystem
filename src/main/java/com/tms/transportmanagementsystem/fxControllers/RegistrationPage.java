package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.UserHib;
import com.tms.transportmanagementsystem.model.Driver;
import com.tms.transportmanagementsystem.model.Manager;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegistrationPage implements Initializable {
    @FXML
    public CheckBox managerCheck;
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField phoneNumField;
    @FXML
    public DatePicker dateOfBirthField;
    @FXML
    public TextField companyNameField;
    @FXML
    public TextField licenceField;
    @FXML
    public Button actionButton;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private Manager selectedManager;
    private Driver selectedDriver;


    private void updateManager(Manager manager) {
        if (!areManagerOrDriverFieldsEmpty()) {
            manager.setLoginName(loginField.getText());
            manager.setPassword(pswField.getText());
            manager.setName(nameField.getText());
            manager.setSurname(surnameField.getText());
            manager.setEmail(emailField.getText());
            manager.setPhoneNumber(phoneNumField.getText());
            manager.setDateOfBirth(dateOfBirthField.getValue());
            manager.setCompanyName(companyNameField.getText());
            userHib.updateUser(manager);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private void updateDriver(Driver driver) {
        if (!areManagerOrDriverFieldsEmpty()) {
            driver.setLoginName(loginField.getText());
            driver.setPassword(pswField.getText());
            driver.setName(nameField.getText());
            driver.setSurname(surnameField.getText());
            driver.setEmail(emailField.getText());
            driver.setPhoneNumber(phoneNumField.getText());
            driver.setDateOfBirth(dateOfBirthField.getValue());
            driver.setDriverLicenceNumber(licenceField.getText());
            userHib.updateUser(driver);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public void setManagerData(EntityManagerFactory entityManagerFactory, Manager selectedManager) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedManager = selectedManager;
        this.userHib = new UserHib(entityManagerFactory);
        fillManagerFields();
    }

    public void setDriverData(EntityManagerFactory entityManagerFactory, Driver selectedDriver) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedDriver = selectedDriver;
        this.userHib = new UserHib(entityManagerFactory);
        fillDriverFields();
    }

    private void fillDriverFields() {
        Driver driver = userHib.getDriverById(selectedDriver.getId());
        managerCheck.setSelected(false);
        disableFields();
        loginField.setText(driver.getLoginName());
        pswField.setText(driver.getPassword());
        nameField.setText(driver.getName());
        surnameField.setText(driver.getSurname());
        emailField.setText(driver.getEmail());
        phoneNumField.setText(driver.getPhoneNumber());
        dateOfBirthField.setValue(driver.getDateOfBirth());
        licenceField.setText(driver.getDriverLicenceNumber());
        actionButton.setOnAction(actionEvent -> updateDriver(driver));
        actionButton.setText("Update");
    }

    private void fillManagerFields() {
        Manager manager = userHib.getManagerById(selectedManager.getId());
        managerCheck.setSelected(true);
        disableFields();
        loginField.setText(manager.getLoginName());
        pswField.setText(manager.getPassword());
        nameField.setText(manager.getName());
        surnameField.setText(manager.getSurname());
        emailField.setText(manager.getEmail());
        phoneNumField.setText(manager.getPhoneNumber());
        dateOfBirthField.setValue(manager.getDateOfBirth());
        companyNameField.setText(manager.getCompanyName());
        actionButton.setOnAction(actionEvent -> updateManager(manager));
        actionButton.setText("Update");
    }

    public void createNewUser() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        UserHib userHib = new UserHib(entityManagerFactory);
        if (!areManagerOrDriverFieldsEmpty()) {
            if (managerCheck.isSelected()) {
                Manager manager = new Manager(loginField.getText(), pswField.getText(), nameField.getText(),
                        surnameField.getText(), emailField.getText(), phoneNumField.getText(),
                        dateOfBirthField.getValue(), companyNameField.getText());
                manager.setRegistrationDate(LocalDate.now());
                userHib.createUser(manager);
            } else {
                Driver driver = new Driver(loginField.getText(), pswField.getText(), nameField.getText(),
                        surnameField.getText(), emailField.getText(), phoneNumField.getText(),
                        dateOfBirthField.getValue(), licenceField.getText());
                driver.setRegistrationDate(LocalDate.now());
                userHib.createUser(driver);
            }
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public void disableFields() {
        if (managerCheck.isSelected()) {
            companyNameField.setDisable(false);
            licenceField.setDisable(true);
        } else {
            companyNameField.setDisable(true);
            licenceField.setDisable(false);
        }
    }

    public boolean areManagerOrDriverFieldsEmpty() {
        if (managerCheck.isSelected()) {
            return areFieldsEmpty(companyNameField);
        } else {
            return areFieldsEmpty(licenceField);
        }
    }

    public boolean areFieldsEmpty(TextField licenceOrCompanyName) {
        if (loginField.getText().isBlank() || pswField.getText().isBlank() || nameField.getText().isBlank() ||
                surnameField.getText().isBlank() || emailField.getText().isBlank() || phoneNumField.getText().isBlank() ||
                dateOfBirthField.getValue() == null || licenceOrCompanyName.getText().isBlank()) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerCheck.setSelected(true);
        disableFields();
    }
}
