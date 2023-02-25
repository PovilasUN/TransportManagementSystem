package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.ForumHib;
import com.tms.transportmanagementsystem.hibernate.TruckHib;
import com.tms.transportmanagementsystem.model.Forum;
import com.tms.transportmanagementsystem.model.Truck;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManageForumsPage {
    @FXML
    public TextField forumTitleField;
    @FXML
    public Button actionButton;
    private EntityManagerFactory entityManagerFactory;
    private ForumHib forumHib;
    private Forum selectedForum;

    public void setForumData(EntityManagerFactory entityManagerFactory, Forum selectedForum) {
        this.entityManagerFactory = entityManagerFactory;
        this.selectedForum = selectedForum;
        this.forumHib = new ForumHib(entityManagerFactory);
        fillForumFields();
    }

    public void createForum() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        ForumHib forumHib = new ForumHib(entityManagerFactory);
        if (!areFieldsEmpty()) {
            Forum forum = new Forum(forumTitleField.getText());
            forumHib.createForum(forum);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private void updateForum(Forum forum) {
        if (!areFieldsEmpty()) {
            forum.setForumTitle(forumTitleField.getText());
            forumHib.updateForum(forum);
            Stage stage = (Stage) actionButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean areFieldsEmpty() {
        if (forumTitleField.getText().isBlank()) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Some fields are empty, try again!");
            return true;
        }
        return false;
    }

    private void fillForumFields() {
        Forum forum = forumHib.getForumById(selectedForum.getId());
        forumTitleField.setText(forum.getForumTitle());
        actionButton.setOnAction(actionEvent -> updateForum(forum));
        actionButton.setText("Update");
    }
}
