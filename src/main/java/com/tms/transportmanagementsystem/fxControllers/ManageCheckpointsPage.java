package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.CheckpointHib;
import com.tms.transportmanagementsystem.hibernate.DestinationHib;
import com.tms.transportmanagementsystem.hibernate.TruckHib;
import com.tms.transportmanagementsystem.model.Checkpoint;
import com.tms.transportmanagementsystem.model.DeliveryStatus;
import com.tms.transportmanagementsystem.model.Destination;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ManageCheckpointsPage implements Initializable {
    @FXML
    public TextField checkpointLocation;
    @FXML
    public TextField usedAmountOfFuel;
    @FXML
    public ChoiceBox<Destination> destinationChoice;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private CheckpointHib checkpointHib;
    private DestinationHib destinationHib;
    private Checkpoint selectedCheckpoint;

    public void setCheckpointData(EntityManagerFactory entityManagerFactory, Checkpoint selectedCheckpoint) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedCheckpoint = selectedCheckpoint;
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
        fillCheckpointFields();
    }

    public void createCheckpoint() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        CheckpointHib checkpointHib = new CheckpointHib(entityManagerFactory);
        if (!areFieldsEmpty()) {
            Checkpoint checkpoint = new Checkpoint(checkpointLocation.getText(), usedAmountOfFuel.getText(), LocalDateTime.now(), destinationChoice.getValue());
            checkpointHib.createCheckpoint(checkpoint);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public void updateCheckpoint(Checkpoint checkpoint) {
        if (!areFieldsEmpty()) {
            checkpoint.setCheckpointLocation(checkpointLocation.getText());
            checkpoint.setUsedAmountOfFuel(usedAmountOfFuel.getText());
            checkpoint.setCheckpointTime(LocalDateTime.now());
            checkpoint.setDestination(destinationChoice.getValue());
            checkpointHib.updateCheckpoint(checkpoint);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    public boolean areFieldsEmpty() {
        if (checkpointLocation.getText().isBlank() || usedAmountOfFuel.getText().isBlank() || destinationChoice.getValue() == null) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }

    private void fillCheckpointFields() {
        Checkpoint checkpoint = checkpointHib.getCheckpointById(selectedCheckpoint.getId());
        checkpointLocation.setText(checkpoint.getCheckpointLocation());
        usedAmountOfFuel.setText(checkpoint.getUsedAmountOfFuel());
        destinationChoice.setValue(checkpoint.getDestination());
        actionButton.setOnAction(actionEvent -> updateCheckpoint(checkpoint));
        actionButton.setText("Update");
    }

    private void fillChoiceList() {
        List<Destination> allDestinations = destinationHib.getAllDestinations();
        destinationChoice.getItems().addAll(allDestinations.stream()
                .filter(d -> d.getDeliveryStatus().equals(DeliveryStatus.DELIVERING)).toList());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        this.destinationHib = new DestinationHib(entityManagerFactory);
        fillChoiceList();
    }
}
