package com.tms.transportmanagementsystem.fxControllers;

import com.tms.transportmanagementsystem.hibernate.CommentHib;
import com.tms.transportmanagementsystem.hibernate.ForumHib;
import com.tms.transportmanagementsystem.model.Comment;
import com.tms.transportmanagementsystem.model.Forum;
import com.tms.transportmanagementsystem.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ManageCommentsPage {
    @FXML
    public TextArea commentTextField;
    @FXML
    public Button createButton;
    private EntityManagerFactory entityManagerFactory;
    private CommentHib commentHib;
    private Comment selectedComment;
    private Forum selectedForum;

    public void setCommentData(EntityManagerFactory entityManagerFactory, TreeItem<Comment> selectedComment, Forum selectedForum) {
        this.entityManagerFactory = entityManagerFactory;
        if (selectedComment != null)
            this.selectedComment = selectedComment.getValue();
        this.selectedForum = selectedForum;
        this.commentHib = new CommentHib(entityManagerFactory);
    }

    public void createComment() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TMS");
        CommentHib commentHib = new CommentHib(entityManagerFactory);
        if (!areFieldsEmpty() && selectedComment != null) {
            Comment comment = new Comment(commentTextField.getText(), selectedComment);
            commentHib.createComment(comment);
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        } else if (!areFieldsEmpty()  && selectedComment == null) {
            Comment comment = new Comment(commentTextField.getText(), selectedForum);
            commentHib.createComment(comment);
            Stage stage = (Stage) createButton.getScene().getWindow();
            stage.close();
        }
    }

//    private void fillForumFields() {
//        Comment comment = commentHib.getCommentById(selectedComment.getId());
//        commentTextField.setText(comment.getCommentText());
//        createButton.setOnAction(actionEvent -> updateForum(forum));
//        actionButton.setText("Update");
//    }

    private boolean areFieldsEmpty() {
        if (commentTextField.getText().isBlank()) {
            FXUtils.generateAlert(Alert.AlertType.ERROR, "Error!",
                    "Enter comment text, please try again!");
            return true;
        }
        return false;
    }


}
