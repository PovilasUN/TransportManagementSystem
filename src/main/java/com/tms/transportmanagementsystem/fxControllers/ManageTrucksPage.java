package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.DestinationHib;
import com.tms.transportmanagementsystem.hibernate.TruckHib;
import com.tms.transportmanagementsystem.hibernate.UserHib;
import com.tms.transportmanagementsystem.model.*;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageTrucksPage implements Initializable {
    @FXML
    public ChoiceBox<TruckModel> truckModelChoice;
    @FXML
    public DatePicker makeDateField;
    @FXML
    public DatePicker technicalExpirationDateField;
    @FXML
    public TextField weightField;
    @FXML
    public TextField hpField;
    @FXML
    public TextField fuelTankCapacityField;
    @FXML
    public ChoiceBox<Driver> driverChoice;
    @FXML
    public ChoiceBox<Manager> managerChoice;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private TruckHib truckHib;
    private UserHib userHib;
    private User user;
    private Truck selectedTruck;

    public void setTruckCreationData(EntityManagerFactory entityManagerFactory, User user, boolean hideFields) {
        this.user = user;
        this.entityManagerFactory = entityManagerFactory;
        if (hideFields) {
            managerChoice.setDisable(true);
        }
    }

    public void setTruckData(Truck selectedTruck) {
        this.selectedTruck = selectedTruck;
        this.truckHib = new TruckHib(entityManagerFactory);
        fillTruckFields();
    }

    public void createTruck() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        TruckHib truckHib = new TruckHib(entityManagerFactory);
        if (!areFieldsEmpty()) {
            Truck truck = new Truck(truckModelChoice.getValue(), makeDateField.getValue(), technicalExpirationDateField.getValue(),
                    Double.parseDouble(weightField.getText()), Integer.parseInt(hpField.getText()), Double.parseDouble(fuelTankCapacityField.getText()), driverChoice.getValue());
            truck.setManager(userHib.getManagerById(user.getId()));
            truckHib.createTruck(truck);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private void updateTruck(Truck truck) {
        if (!areFieldsEmpty()) {
            truck.setTruckModel(truckModelChoice.getValue());
            truck.setMakeDate(makeDateField.getValue());
            truck.setTechnicalCheckExpirationDate(technicalExpirationDateField.getValue());
            truck.setTruckWeight(Double.parseDouble(weightField.getText()));
            truck.setHorsePower(Integer.parseInt(hpField.getText()));
            truck.setFuelTankCapacity(Double.parseDouble(fuelTankCapacityField.getText()));
            truck.setManager(managerChoice.getValue());
            truckHib.updateTruck(truck);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean areFieldsEmpty() {
        if (truckModelChoice.getValue() == null || makeDateField.getValue() == null || technicalExpirationDateField.getValue() == null ||
                weightField.getText().isBlank() || hpField.getText().isBlank() || fuelTankCapacityField.getText().isBlank() || driverChoice.getValue() == null) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }

    private void fillChoiceLists() {
        List<Driver> allDrivers = userHib.getAllDrivers();
        List<Manager> allManagers = userHib.getAllManagers();
        truckModelChoice.getItems().addAll(TruckModel.values());
        driverChoice.getItems().addAll(allDrivers);
        managerChoice.getItems().addAll(allManagers);
    }

    private void fillTruckFields() {
        Truck truck = truckHib.getTruckById(selectedTruck.getId());
        truckModelChoice.setValue(truck.getTruckModel());
        makeDateField.setValue(truck.getMakeDate());
        technicalExpirationDateField.setValue(truck.getTechnicalCheckExpirationDate());
        weightField.setText(String.valueOf(truck.getTruckWeight()));
        hpField.setText(String.valueOf(truck.getHorsePower()));
        fuelTankCapacityField.setText(String.valueOf(truck.getFuelTankCapacity()));
        driverChoice.setValue(truck.getDriver());
        managerChoice.setValue(truck.getManager());
        actionButton.setOnAction(actionEvent -> updateTruck(truck));
        actionButton.setText("Update");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        this.userHib = new UserHib(entityManagerFactory);
        fillChoiceLists();
    }
}
