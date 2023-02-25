package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.DestinationHib;
import com.tms.transportmanagementsystem.model.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;

import javax.persistence.EntityManagerFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ManageFilteredDestinationsPage implements Initializable {
    @FXML
    public ListView<Destination> filteredDestinationList;
    @FXML
    public ChoiceBox<DeliveryStatus> deliveryStatusChoice;
    @FXML
    public DatePicker endDate;
    @FXML
    public DatePicker startDate;
    @FXML
    public Button filterButton;
    private EntityManagerFactory entityManagerFactory;
    private DestinationHib destinationHib;

    public void setFilteredDestinationData(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
        this.destinationHib = new DestinationHib(entityManagerFactory);
    }

    public void filterDestinations() {
        refreshUI();
        List<Destination> allDestination = destinationHib.getAllDestinations();
        Predicate<Destination> predicate1 = d -> startDate.getValue() == null || d.getStartDate().equals(startDate.getValue());
        Predicate<Destination> predicate2 = d -> endDate.getValue() == null || d.getEndDate().equals(endDate.getValue());
        Predicate<Destination> predicate3 = d -> deliveryStatusChoice.getValue() == null || d.getDeliveryStatus().equals(deliveryStatusChoice.getValue());
        List<Destination> filteredList = allDestination.stream()
                .filter(predicate1.and(predicate2).and(predicate3)).toList();
        filteredList.forEach(c -> filteredDestinationList.getItems().add(c));
    }

    private void refreshUI() {
        filteredDestinationList.getItems().clear();
    }

    private void fillChoiceLists() {
        deliveryStatusChoice.getItems().addAll(DeliveryStatus.values());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillChoiceLists();
    }
}
