package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.CargoHib;
import com.tms.transportmanagementsystem.hibernate.CheckpointHib;
import com.tms.transportmanagementsystem.hibernate.DestinationHib;
import com.tms.transportmanagementsystem.hibernate.TruckHib;
import com.tms.transportmanagementsystem.model.Cargo;
import com.tms.transportmanagementsystem.model.Checkpoint;
import com.tms.transportmanagementsystem.model.Destination;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class ViewDestinationPage {
    @FXML
    public TextField startLocationView;
    @FXML
    public TextField endLocationView;
    @FXML
    public TextField startDateView;
    @FXML
    public TextField endDateView;
    @FXML
    public TextField truckView;
    @FXML
    public TextField cargoView;
    @FXML
    public TextField driverView;
    @FXML
    public TextField statusView;
    @FXML
    public TextField managerView;
    @FXML
    public ListView<Checkpoint> destinationCheckpointsList;
    @FXML
    public LineChart<String, String> fuelConsumptionChart;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private CheckpointHib checkpointHib;
    private DestinationHib destinationHib;
    private Destination selectedDestination;

    public void setDestinationViewData(EntityManagerFactory entityManagerFactory, Destination selectedDestination) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedDestination = selectedDestination;
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
        this.destinationHib = new DestinationHib(entityManagerFactory);
        fillViewFields();
        fillCheckpointList();
        fillChartWithData();
    }

    private void fillChartWithData() {
        List<Checkpoint> destinationCheckpoints = selectedDestination.getCheckpoints();
        XYChart.Series<String, String> series = new XYChart.Series<>();
        for (Checkpoint point : destinationCheckpoints) {
            XYChart.Data<String, String> checkpoints = new XYChart.Data<>(point.getCheckpointLocation(), point.getUsedAmountOfFuel());
            series.getData().add(checkpoints);
        }
        fuelConsumptionChart.getData().add(series);
    }

    private void fillViewFields() {
        Destination destination = destinationHib.getDestinationById(selectedDestination.getId());
        startLocationView.setText(destination.getStartLocation());
        endLocationView.setText(destination.getEndLocation());
        startDateView.setText(String.valueOf(destination.getStartDate()));
        endDateView.setText(String.valueOf(destination.getEndDate()));
        truckView.setText(String.valueOf(destination.getTruck()));
        cargoView.setText(String.valueOf(destination.getCargo()));
        driverView.setText(String.valueOf(destination.getDriver()));
        statusView.setText(String.valueOf(destination.getDeliveryStatus()));
        managerView.setText(String.valueOf(destination.getManager()));
    }

    private void fillCheckpointList() {
        Destination destination = destinationHib.getDestinationById(selectedDestination.getId());
        destinationCheckpointsList.getItems().addAll(destination.getCheckpoints());
    }
}
