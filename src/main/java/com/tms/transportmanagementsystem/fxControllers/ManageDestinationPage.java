package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.CargoHib;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageDestinationPage implements Initializable {
    @FXML
    public TextField endLocation;
    @FXML
    public TextField startLocation;
    @FXML
    public DatePicker startDate;
    @FXML
    public DatePicker endDate;
    @FXML
    public ChoiceBox<Truck> truckChoice;
    @FXML
    public ChoiceBox<Cargo> cargoChoice;
    @FXML
    public ChoiceBox<Driver> driverChoice;
    @FXML
    public ChoiceBox<DeliveryStatus> deliveryStatusChoice;
    @FXML
    public ChoiceBox<Manager> managerChoice;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private DestinationHib destinationHib;
    private TruckHib truckHib;
    private CargoHib cargoHib;
    private UserHib userHib;
    private User user;
    private Destination selectedDestination;

    public void setDestinationCreationData(EntityManagerFactory entityManagerFactory, User user, boolean hideBoxes) {
        this.user = user;
        this.entityManagerFactory = entityManagerFactory;
        if (hideBoxes) {
            deliveryStatusChoice.setDisable(true);
            managerChoice.setDisable(true);
        }
    }

    public void setDestinationData(Destination selectedDestination) {
        this.selectedDestination = selectedDestination;
        this.destinationHib = new DestinationHib(entityManagerFactory);
        fillDestinationFields();
    }

    public void createDestination() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        DestinationHib destinationHib = new DestinationHib(entityManagerFactory);
        if (!areFieldsEmpty()) {
            Destination destination = new Destination(startLocation.getText(), endLocation.getText(), startDate.getValue(),
                    endDate.getValue(), truckChoice.getValue(), cargoChoice.getValue(), driverChoice.getValue());
            destination.setDeliveryStatus(DeliveryStatus.DELIVERING);
            destination.setManager(userHib.getManagerById(user.getId()));
            destinationHib.createDestination(destination);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private void updateDestination(Destination destination) {
        if (!areFieldsEmpty()) {
            destination.setStartLocation(startLocation.getText());
            destination.setEndLocation(endLocation.getText());
            destination.setStartDate(startDate.getValue());
            destination.setEndDate(endDate.getValue());
            destination.setTruck(truckChoice.getValue());
            destination.setCargo(cargoChoice.getValue());
            destination.setDriver(driverChoice.getValue());
            destination.setDeliveryStatus(deliveryStatusChoice.getValue());
            destination.setManager(managerChoice.getValue());
            destinationHib.updateDestination(destination);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean areFieldsEmpty() {
        if (startLocation.getText().isBlank() || endLocation.getText().isBlank() || startDate.getValue() == null ||
                endDate.getValue() == null || truckChoice.getValue() == null || cargoChoice.getValue() == null ||
        driverChoice.getValue() == null) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }

    private void fillChoiceLists() {
        List<Truck> allTrucks = truckHib.getAllTrucks();
        List<Cargo> allCargo = cargoHib.getAllCargo();
        List<Driver> allDrivers = userHib.getAllDrivers();
        List<Manager> allManagers = userHib.getAllManagers();
        deliveryStatusChoice.getItems().addAll(DeliveryStatus.values());
        truckChoice.getItems().addAll(allTrucks);
        cargoChoice.getItems().addAll(allCargo);
        driverChoice.getItems().addAll(allDrivers);
        managerChoice.getItems().addAll(allManagers);
    }

    private void fillDestinationFields() {
        Destination destination = destinationHib.getDestinationById(selectedDestination.getId());
        startLocation.setText(destination.getStartLocation());
        endLocation.setText(destination.getEndLocation());
        startDate.setValue(destination.getStartDate());
        endDate.setValue(destination.getEndDate());
        truckChoice.setValue(destination.getTruck());
        cargoChoice.setValue(destination.getCargo());
        driverChoice.setValue(destination.getDriver());
        deliveryStatusChoice.setValue(destination.getDeliveryStatus());
        managerChoice.setValue(destination.getManager());
        actionButton.setOnAction(actionEvent -> updateDestination(destination));
        actionButton.setText("Update");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        this.truckHib = new TruckHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.userHib = new UserHib(entityManagerFactory);
        fillChoiceLists();
    }
}
