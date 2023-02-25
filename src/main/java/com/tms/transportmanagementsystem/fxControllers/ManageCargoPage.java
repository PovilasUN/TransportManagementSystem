package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.CargoHib;
import com.tms.transportmanagementsystem.hibernate.CheckpointHib;
import com.tms.transportmanagementsystem.model.*;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class ManageCargoPage implements Initializable {
    @FXML
    public ChoiceBox<CargoType> cargoChoice;
    @FXML
    public Spinner<Integer> quantitySpinner;
    @FXML
    public Spinner<Integer> weightSpinner;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private CargoHib cargoHib;
    private Cargo selectedCargo;

    public void setCargoData(EntityManagerFactory entityManagerFactory, Cargo selectedCargo) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedCargo = selectedCargo;
        this.cargoHib = new CargoHib(entityManagerFactory);
        fillCargoFields();
    }

    public void createCargo() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        CargoHib cargoHib = new CargoHib(entityManagerFactory);
        if (!areFieldsEmpty()) {
            Cargo cargo = new Cargo(cargoChoice.getValue(), String.valueOf(quantitySpinner.getValue()),
                    String.valueOf(weightSpinner.getValue()));
            cargoHib.createCargo(cargo);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public void updateCargo(Cargo cargo) {
        if (!areFieldsEmpty()) {
            cargo.setCargoType(cargoChoice.getValue());
            cargo.setNumberOfProducts(String.valueOf(quantitySpinner.getValue()));
            cargo.setCargoWeight(String.valueOf(weightSpinner.getValue()));
            cargoHib.updateCargo(cargo);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public boolean areFieldsEmpty() {
        if (cargoChoice.getValue() == null) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }

    private void fillChoiceLists() {
        cargoChoice.getItems().addAll(CargoType.values());
    }

    private void fillAllSpinners() {
        SpinnerValueFactory<Integer> quantityValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2000);
        SpinnerValueFactory<Integer> weightValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 2000);
        quantityValueFactory.setValue(1);
        weightValueFactory.setValue(1);
        quantitySpinner.setValueFactory(quantityValueFactory);
        weightSpinner.setValueFactory(weightValueFactory);
    }

    private void fillCargoFields() {
        Cargo cargo = cargoHib.getCargoById(selectedCargo.getId());
        cargoChoice.setValue(cargo.getCargoType());
        quantitySpinner.getValueFactory().setValue(Integer.valueOf(cargo.getNumberOfProducts()));
        weightSpinner.getValueFactory().setValue(Integer.valueOf(cargo.getCargoWeight()));
        actionButton.setOnAction(actionEvent -> updateCargo(cargo));
        actionButton.setText("Update");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChoiceLists();
        fillAllSpinners();
    }
}
