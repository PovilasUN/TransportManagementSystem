package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.StartProgram;
import com.tms.transportmanagementsystem.hibernate.UserHib;
import com.tms.transportmanagementsystem.model.User;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;

public class LoginPage {
    @FXML
    public TextField loginField;
    @FXML
    public PasswordField pswField;
    @FXML
    public CheckBox managerCheck;

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
    UserHib userHib = new UserHib(entityManagerFactory);

    public void validateUser() {
        if (!areFieldsEmpty()) {
            User user = userHib.getUserByLoginData(loginField.getText(), pswField.getText(), managerCheck.isSelected());
            if (user != null)
                goToMainPage(user);
            else
                FXUtils.generateAlert(Alert.AlertType.ERROR, "User login error!",
                        "Wrong username or password, try again!");
        }
    }

    public boolean areFieldsEmpty() {
        if (loginField.getText().isBlank() || pswField.getText().isBlank()) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "User login error!",
                    "Some login fields might be empty, try again!");
            return true;
        }
        return false;
    }

    public void goToMainPage(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("main-page.fxml"));
            Parent parent = fxmlLoader.load();
            MainPage mainPage = fxmlLoader.getController();
            mainPage.setData(entityManagerFactory, user);
            Scene scene = new Scene(parent);
            Stage stage = (Stage) loginField.getScene().getWindow();
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "Error loading next scene!");
        }
    }

    public void goToRegistrationPage() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("registration-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "Error loading next scene!");
        }
    }
}
