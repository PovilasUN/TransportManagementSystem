package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.StartProgram;
import com.tms.transportmanagementsystem.hibernate.*;
import com.tms.transportmanagementsystem.model.*;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainPage {
    @FXML
    public TabPane allTabs;
    @FXML
    public Tab orderTab;
    @FXML
    public Tab userTab;
    @FXML
    public Tab truckTab;
    @FXML
    public ListView<Manager> managerListView;
    @FXML
    public ListView<Driver> driverListView;
    @FXML
    public ListView<Destination> destinationListView;
    @FXML
    public ListView<Checkpoint> checkpointListView;
    @FXML
    public ListView<Truck> truckListView;
    @FXML
    public ListView<Cargo> cargoListView;
    @FXML
    public ListView<Forum> forumListView;
    @FXML
    public TreeView<Comment> commentTreeList;
    @FXML
    public Button driverUpdate;
    @FXML
    public Button driverDelete;
    @FXML
    public Button managerUpdate;
    @FXML
    public Button managerDelete;
    @FXML
    public Button destinationView;
    @FXML
    public Button destinationUpdate;
    @FXML
    public Button checkpointCreation;
    @FXML
    public Button checkpointUpdate;
    @FXML
    public Button destinationCreation;
    @FXML
    public Button destinationDelete;
    @FXML
    public Button truckCreate;
    @FXML
    public Button cargoCreate;
    @FXML
    public Button checkpointDelete;
    @FXML
    public Button truckUpdate;
    @FXML
    public Button truckDelete;
    @FXML
    public Button cargoUpdate;
    @FXML
    public Button cargoDelete;
    @FXML
    public Button destinationFilter;
    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;
    private DestinationHib destinationHib;
    private CheckpointHib checkpointHib;
    private CargoHib cargoHib;
    private TruckHib truckHib;
    private CommentHib commentHib;
    private ForumHib forumHib;
    private User user;

    public void setData(EntityManagerFactory entityManagerFactory, User user) {
        this.entityManagerFactory = entityManagerFactory;
        this.userHib = new UserHib(entityManagerFactory);
        this.destinationHib = new DestinationHib(entityManagerFactory);
        this.checkpointHib = new CheckpointHib(entityManagerFactory);
        this.cargoHib = new CargoHib(entityManagerFactory);
        this.truckHib = new TruckHib(entityManagerFactory);
        this.commentHib = new CommentHib(entityManagerFactory);
        this.forumHib = new ForumHib(entityManagerFactory);
        this.user = user;
        disableTabsAndFieldsForUsers();
        fillAllLists(user);
    }

    public void deleteManager() {
        var getSelected = managerListView.getSelectionModel().getSelectedItem();
        if (Objects.equals(getSelected.getLoginName(), user.getLoginName()) && Objects.equals(getSelected.getPassword(), user.getPassword()) || user.isAdmin()) {
            userHib.deleteUser(getSelected);
            refreshUI();
        } else {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "You can only change Your own info!", "Please choose Your user!");
        }
    }

    public void deleteDriver() {
        var getSelected = driverListView.getSelectionModel().getSelectedItem();
        if (Objects.equals(getSelected.getLoginName(), user.getLoginName()) && Objects.equals(getSelected.getPassword(), user.getPassword()) || user.isAdmin()) {
            userHib.deleteUser(getSelected);
            refreshUI();
        } else {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "You can only change Your own info!", "Please choose Your user!");
        }
    }

    public void deleteDestination() {
        var getSelected = destinationListView.getSelectionModel().getSelectedItem();
        if (getSelected.getDeliveryStatus() == DeliveryStatus.CANCELED || getSelected.getDeliveryStatus() == DeliveryStatus.DELIVERED) {
            destinationHib.deleteDestination(getSelected);
            refreshUI();
        } else {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Order in progress!", "This order is still in progress You can't delete it!");
        }
    }

    public void deleteCheckpoint() {
        var getSelected = checkpointListView.getSelectionModel().getSelectedItem();
        checkpointHib.deleteCheckpoint(getSelected);
        refreshUI();
    }

    public void deleteCargo() {
        var getSelected = cargoListView.getSelectionModel().getSelectedItem();
        cargoHib.deleteCargo(getSelected);
        refreshUI();
    }

    public void deleteTruck() {
        var getSelected = truckListView.getSelectionModel().getSelectedItem();
        truckHib.deleteTruck(getSelected);
        refreshUI();
    }

    public void updateManager() {
        if (Objects.equals(managerListView.getSelectionModel().getSelectedItem().getLoginName(), user.getLoginName()) &&
                Objects.equals(managerListView.getSelectionModel().getSelectedItem().getPassword(), user.getPassword()) ||
        user.isAdmin()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("registration-page.fxml"));
                Parent parent = fxmlLoader.load();
                RegistrationPage registrationPage = fxmlLoader.getController();
                registrationPage.setManagerData(entityManagerFactory, managerListView.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initOwner(managerListView.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle("Transport Management System");
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                        "It appears there is no next screen to be opened!");
            }
            refreshUI();
        } else {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "You can only change Your own info!", "Please choose Your user!");
        }
    }

    public void updateDriver() {
        if (Objects.equals(driverListView.getSelectionModel().getSelectedItem().getLoginName(), user.getLoginName()) &&
                Objects.equals(driverListView.getSelectionModel().getSelectedItem().getPassword(), user.getPassword()) ||
                user.isAdmin()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("registration-page.fxml"));
                Parent parent = fxmlLoader.load();
                RegistrationPage registrationPage = fxmlLoader.getController();
                registrationPage.setDriverData(entityManagerFactory, driverListView.getSelectionModel().getSelectedItem());
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.initOwner(managerListView.getScene().getWindow());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.setTitle("Transport Management System");
                stage.setScene(scene);
                stage.showAndWait();
            } catch (IOException e) {
                FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                        "It appears there is no next screen to be opened!");
            }
            refreshUI();
        } else {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "You can only change Your own info!", "Please choose Your user!");
        }
    }

    public void updateDestination() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-destinations-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageDestinationPage manageDestinationPage = fxmlLoader.getController();
            manageDestinationPage.setDestinationData(destinationListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(managerListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void updateCheckpoint() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-checkpoints-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageCheckpointsPage manageCheckpointsPage = fxmlLoader.getController();
            manageCheckpointsPage.setCheckpointData(entityManagerFactory, checkpointListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(checkpointListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void updateCargo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-cargo-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageCargoPage manageCargoPage = fxmlLoader.getController();
            manageCargoPage.setCargoData(entityManagerFactory, cargoListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(cargoListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void updateTruck() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-trucks-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageTrucksPage manageTrucksPage = fxmlLoader.getController();
            manageTrucksPage.setTruckData(truckListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(truckListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createDestination() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-destinations-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageDestinationPage manageDestinationPage = fxmlLoader.getController();
            manageDestinationPage.setDestinationCreationData(entityManagerFactory, user, true);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(destinationListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createCheckpoint() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-checkpoints-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(checkpointListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createCargo() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-cargo-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(cargoListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createTruck() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-trucks-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageTrucksPage manageTrucksPage = fxmlLoader.getController();
            manageTrucksPage.setTruckCreationData(entityManagerFactory, user, true);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(cargoListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createForum() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-forums-page.fxml"));
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(forumListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void createComment() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-comments-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageCommentsPage manageCommentsPage = fxmlLoader.getController();
            manageCommentsPage.setCommentData(entityManagerFactory, commentTreeList.getSelectionModel().getSelectedItem(), forumListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(forumListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void loadComments() {
        List<Comment> comments = forumHib.getForumById(forumListView.getSelectionModel().getSelectedItem().getId()).getComments();
        commentTreeList.setRoot(new TreeItem<>(new Comment()));
        commentTreeList.setShowRoot(false);
        commentTreeList.getRoot().setExpanded(true);
        comments.forEach(comment -> addTreeItem(comment, commentTreeList.getRoot()));
    }

    public void addTreeItem(Comment comment, TreeItem parent) {
        TreeItem<Comment> treeItem = new TreeItem<>(comment);
        parent.getChildren().add(treeItem);
        comment.getReplies().forEach(r -> addTreeItem(r, treeItem));
    }

    public void viewDestination() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("view-destination-page.fxml"));
            Parent parent = fxmlLoader.load();
            ViewDestinationPage viewDestinationPage = fxmlLoader.getController();
            viewDestinationPage.setDestinationViewData(entityManagerFactory, destinationListView.getSelectionModel().getSelectedItem());
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(cargoListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void filterDestination() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("manage-filtered-destinations-page.fxml"));
            Parent parent = fxmlLoader.load();
            ManageFilteredDestinationsPage manageFilteredDestinationsPage = fxmlLoader.getController();
            manageFilteredDestinationsPage.setFilteredDestinationData(entityManagerFactory);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.initOwner(destinationListView.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle("Transport Management System");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Critical Error!",
                    "It appears there is no next screen to be opened!");
        }
        refreshUI();
    }

    public void disableTabsAndFieldsForUsers() {
        if (user.getClass() == Driver.class) {
            managerUpdate.setDisable(true);
            managerDelete.setDisable(true);
            destinationUpdate.setDisable(true);
            destinationCreation.setDisable(true);
            destinationDelete.setDisable(true);
            truckCreate.setDisable(true);
            truckUpdate.setDisable(true);
            truckDelete.setDisable(true);
            cargoCreate.setDisable(true);
            cargoUpdate.setDisable(true);
            cargoDelete.setDisable(true);
        } else if (user.getClass() == Manager.class && !user.isAdmin()) {
            driverUpdate.setDisable(true);
            driverDelete.setDisable(true);
            checkpointCreation.setDisable(true);
            checkpointDelete.setDisable(true);
            checkpointUpdate.setDisable(true);
        }
    }

    private void fillAllLists(User user) {
        List<Manager> allManagers = userHib.getAllManagers();
        allManagers.forEach(c -> managerListView.getItems().add(c));

        List<Driver> allDrivers = userHib.getAllDrivers();
        allDrivers.forEach(c -> driverListView.getItems().add(c));

        List<Destination> allDestinations = destinationHib.getAllDestinations();
        if (user.isAdmin()) {
            allDestinations.forEach(c -> destinationListView.getItems().add(c));
        } else if (user.getClass() == Driver.class) {
            var filteredDestinationsByDriver = allDestinations.stream().filter(d -> Objects.equals(d.getDriver().getId(), user.getId()));
            filteredDestinationsByDriver.forEach(c -> destinationListView.getItems().add(c));
        } else {
            var filteredDestinationsByManager = allDestinations.stream().filter(d -> Objects.equals(d.getManager().getId(), user.getId()));
            filteredDestinationsByManager.forEach(c -> destinationListView.getItems().add(c));
        }

        List<Checkpoint> allCheckpoints = checkpointHib.getAllCheckpoints();
        allCheckpoints.forEach(c -> checkpointListView.getItems().add(c));

        List<Cargo> allCargo = cargoHib.getAllCargo();
        allCargo.forEach(c -> cargoListView.getItems().add(c));

        List<Truck> allTrucks = truckHib.getAllTrucks();
        if (user.isAdmin()) {
            allTrucks.forEach(c -> truckListView.getItems().add(c));
        } else if (user.getClass() == Driver.class) {
            var filteredTrucksByDriver = allTrucks.stream().filter(d -> Objects.equals(d.getDriver().getId(), user.getId()));
            filteredTrucksByDriver.forEach(c -> truckListView.getItems().add(c));
        } else {
            var filteredTrucksByManager = allTrucks.stream().filter(d -> Objects.equals(d.getManager().getId(), user.getId()));
            filteredTrucksByManager.forEach(c -> truckListView.getItems().add(c));
        }
//        allTrucks.forEach(c -> truckListView.getItems().add(c));

        List<Forum> allForums = forumHib.getAllForums();
        allForums.forEach(c -> forumListView.getItems().add(c));
    }

    private void refreshUI() {
        driverListView.getItems().clear();
        managerListView.getItems().clear();
        destinationListView.getItems().clear();
        checkpointListView.getItems().clear();
        cargoListView.getItems().clear();
        truckListView.getItems().clear();
        forumListView.getItems().clear();
        commentTreeList.refresh();
        fillAllLists(user);
    }
}
